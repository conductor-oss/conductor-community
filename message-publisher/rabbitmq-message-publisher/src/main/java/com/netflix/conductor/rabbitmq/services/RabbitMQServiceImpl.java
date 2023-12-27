/*
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 */
package com.netflix.conductor.rabbitmq.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.netflix.conductor.contribs.queue.amqp.AMQPConnection;
import com.netflix.conductor.contribs.queue.amqp.util.ConnectionType;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;

public class RabbitMQServiceImpl implements RabbitMQService {

    private static final Logger LOGGER = LoggerFactory.getLogger(RabbitMQServiceImpl.class);

    private final AMQPConnection amqpConnection;

    @Autowired private final ObjectMapper objectMapper;

    public RabbitMQServiceImpl(AMQPConnection amqpConnection, ObjectMapper objectMapper) {
        this.amqpConnection = amqpConnection;
        this.objectMapper = objectMapper;
    }

    @Override
    public <T> void publishMessage(String exchangeName, T content) throws Exception {
        Channel channel = amqpConnection.getOrCreateChannel(ConnectionType.PUBLISHER, exchangeName);

        channel.exchangeDeclare(exchangeName, BuiltinExchangeType.FANOUT, false);

        String jsonMessage = serializeContent(content);

        channel.basicPublish(exchangeName, "", null, jsonMessage.getBytes());

        amqpConnection.returnChannel(ConnectionType.PUBLISHER, channel);
    }

    @Override
    public <T> void publishMessage(
            String exchangeName, BuiltinExchangeType exchangeType, String routingKey, T content)
            throws Exception {
        Channel channel = amqpConnection.getOrCreateChannel(ConnectionType.PUBLISHER, exchangeName);

        channel.exchangeDeclare(exchangeName, exchangeType, false);

        String jsonMessage = serializeContent(content);

        channel.basicPublish(exchangeName, routingKey, null, jsonMessage.getBytes());

        amqpConnection.returnChannel(ConnectionType.PUBLISHER, channel);
    }

    @Override
    public void close() {
        amqpConnection.close();
    }

    private <T> String serializeContent(T content) {
        try {
            return objectMapper.writeValueAsString(content);
        } catch (JsonProcessingException e) {
            LOGGER.error(
                    "Failed to serialize message of type: {} to String. Exception: {}",
                    content.getClass(),
                    e);
            throw new RuntimeException(e);
        }
    }
}
