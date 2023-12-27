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

import com.netflix.conductor.core.listener.TaskStatusListener;
import com.netflix.conductor.model.TaskModel;
import com.netflix.conductor.rabbitmq.config.RabbitMQProperties;
import com.netflix.conductor.rabbitmq.services.RabbitMQService;

public class TaskStatusPublisherRabbitMQ implements TaskStatusListener {

    private final Logger LOGGER = LoggerFactory.getLogger(TaskStatusPublisherRabbitMQ.class);

    private final RabbitMQService rabbitMQService;
    private final RabbitMQProperties rabbitMQProperties;

    public TaskStatusPublisherRabbitMQ(
            RabbitMQService rabbitMQService, RabbitMQProperties rabbitMQProperties) {
        this.rabbitMQService = rabbitMQService;
        this.rabbitMQProperties = rabbitMQProperties;
    }

    @Override
    public void onTaskScheduled(TaskModel task) {
        publishMessage(task);
    }

    @Override
    public void onTaskInProgress(TaskModel task) {
        publishMessage(task);
    }

    @Override
    public void onTaskCanceled(TaskModel task) {
        publishMessage(task);
    }

    @Override
    public void onTaskFailed(TaskModel task) {
        publishMessage(task);
    }

    @Override
    public void onTaskFailedWithTerminalError(TaskModel task) {
        publishMessage(task);
    }

    @Override
    public void onTaskCompleted(TaskModel task) {
        publishMessage(task);
    }

    @Override
    public void onTaskCompletedWithErrors(TaskModel task) {
        publishMessage(task);
    }

    @Override
    public void onTaskTimedOut(TaskModel task) {
        publishMessage(task);
    }

    @Override
    public void onTaskSkipped(TaskModel task) {
        publishMessage(task);
    }

    private boolean IsStatusEnabled(TaskModel task) {
        return rabbitMQProperties.getAllowedTaskStatuses().contains(task.getStatus().name());
    }

    private void publishMessage(TaskModel task) {
        try {
            if (IsStatusEnabled(task))
                rabbitMQService.publishMessage(rabbitMQProperties.getTaskStatusExchange(), task);
        } catch (Exception e) {
            LOGGER.error(
                    "Failed to publish message to exchange: {}. Exception: {}",
                    rabbitMQProperties.getTaskStatusExchange(),
                    e);
            throw new RuntimeException(e);
        }
    }
}
