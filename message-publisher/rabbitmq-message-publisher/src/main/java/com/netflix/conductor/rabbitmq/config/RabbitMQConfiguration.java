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
package com.netflix.conductor.rabbitmq.config;

import com.netflix.conductor.core.listener.TaskStatusListener;
import com.netflix.conductor.core.listener.WorkflowStatusListener;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.netflix.conductor.contribs.queue.amqp.AMQPConnection;
import com.netflix.conductor.contribs.queue.amqp.config.AMQPRetryPattern;
import com.netflix.conductor.rabbitmq.listener.TaskStatusPublisherRabbitMQ;
import com.netflix.conductor.rabbitmq.listener.WorkflowStatusPublisherRabbitMQ;
import com.netflix.conductor.rabbitmq.services.RabbitMQService;
import com.netflix.conductor.rabbitmq.services.RabbitMQServiceImpl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.Address;
import com.rabbitmq.client.ConnectionFactory;

@Configuration
@EnableConfigurationProperties(RabbitMQProperties.class)
@ConditionalOnProperty(name = "conductor.message-publisher.type", havingValue = "rabbitmq")
public class RabbitMQConfiguration {

    @Bean
    public AMQPConnection amqpConnection(RabbitMQProperties rabbitMQProperties) {
        ConnectionFactory connectionFactory = new ConnectionFactory();

        connectionFactory.setHost(rabbitMQProperties.getHosts());
        connectionFactory.setPort(rabbitMQProperties.getPort());
        connectionFactory.setUsername(rabbitMQProperties.getUsername());
        connectionFactory.setPassword(rabbitMQProperties.getPassword());

        Address[] addresses =
                new Address[] {
                    new Address(rabbitMQProperties.getHosts(), rabbitMQProperties.getPort())
                };

        AMQPRetryPattern retryPattern = new AMQPRetryPattern();

        return AMQPConnection.getInstance(connectionFactory, addresses, retryPattern);
    }

    @Bean
    public RabbitMQService rabbitMQService(
            AMQPConnection amqpConnection, ObjectMapper objectMapper) {
        return new RabbitMQServiceImpl(amqpConnection, objectMapper);
    }

    @ConditionalOnProperty(
            name = "conductor.workflow-status-listener.type",
            havingValue = "rabbitmq",
            matchIfMissing = false)
    @Bean
    public WorkflowStatusListener workflowStatusListenerRabbitMQ(
            RabbitMQService rabbitMQService, RabbitMQProperties rabbitMQProperties) {
        return new WorkflowStatusPublisherRabbitMQ(rabbitMQService, rabbitMQProperties);
    }

    @ConditionalOnProperty(
            name = "conductor.task-status-listener.type",
            havingValue = "rabbitmq",
            matchIfMissing = false)
    @Bean
    public TaskStatusListener taskStatusPublisherRabbitMQ(
            RabbitMQService rabbitMQService, RabbitMQProperties rabbitMQProperties) {
        return new TaskStatusPublisherRabbitMQ(rabbitMQService, rabbitMQProperties);
    }
}
