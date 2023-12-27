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
package com.netflix.conductor.rabbitmq.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.netflix.conductor.core.listener.WorkflowStatusListener;
import com.netflix.conductor.model.WorkflowModel;
import com.netflix.conductor.rabbitmq.config.RabbitMQProperties;
import com.netflix.conductor.rabbitmq.services.RabbitMQService;

public class WorkflowStatusPublisherRabbitMQ implements WorkflowStatusListener {

    private final Logger LOGGER = LoggerFactory.getLogger(WorkflowStatusPublisherRabbitMQ.class);
    private final RabbitMQService rabbitMQService;
    private final String EXCHANGE_NAME;

    public WorkflowStatusPublisherRabbitMQ(
            RabbitMQService rabbitMQService, RabbitMQProperties rabbitMQProperties) {
        this.rabbitMQService = rabbitMQService;
        this.EXCHANGE_NAME = rabbitMQProperties.getWorkflowStatusExchange();
    }

    @Override
    public void onWorkflowCompleted(WorkflowModel workflow) {
        publishMessage(workflow);
    }

    @Override
    public void onWorkflowTerminated(WorkflowModel workflow) {
        publishMessage(workflow);
    }

    @Override
    public void onWorkflowFinalized(WorkflowModel workflow) {
        publishMessage(workflow);
    }

    private void publishMessage(WorkflowModel workflow) {
        try {
            rabbitMQService.publishMessage(EXCHANGE_NAME, workflow);
        } catch (Exception e) {
            LOGGER.error(
                    "Failed to publish message to exchange: {}. Exception: {}", EXCHANGE_NAME, e);
            throw new RuntimeException(e);
        }
    }
}
