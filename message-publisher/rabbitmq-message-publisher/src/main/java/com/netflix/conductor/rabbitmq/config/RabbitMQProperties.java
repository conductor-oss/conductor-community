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

import java.util.Arrays;
import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;

import com.rabbitmq.client.ConnectionFactory;

@ConfigurationProperties("conductor.message-publisher.rabbitmq")
public class RabbitMQProperties {

    private String hosts = ConnectionFactory.DEFAULT_HOST;
    private String username = ConnectionFactory.DEFAULT_USER;
    private String password = ConnectionFactory.DEFAULT_PASS;

    private int port = ConnectionFactory.DEFAULT_AMQP_PORT;
    private int maxChannelCount = 5000;
    private int limit = 50;
    private int duration = 1000;

    private String virtualHost = ConnectionFactory.DEFAULT_VHOST;

    private String allowedTaskStatuses;

    private String workflowStatusExchange;
    private String taskStatusExchange;

    private boolean alwaysPublishWorkflowStatusEnabled = true;

    public String getHosts() {
        return hosts;
    }

    public void setHosts(String hosts) {
        this.hosts = hosts;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public int getMaxChannelCount() {
        return maxChannelCount;
    }

    public void setMaxChannelCount(int maxChannelCount) {
        this.maxChannelCount = maxChannelCount;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getVirtualHost() {
        return virtualHost;
    }

    public void setVirtualHost(String virtualHost) {
        this.virtualHost = virtualHost;
    }

    public String getWorkflowStatusExchange() {
        return workflowStatusExchange;
    }

    public void setWorkflowStatusExchange(String workflowStatusExchange) {
        this.workflowStatusExchange = workflowStatusExchange;
    }

    public String getTaskStatusExchange() {
        return taskStatusExchange;
    }

    public void setTaskStatusExchange(String taskStatusExchange) {
        this.taskStatusExchange = taskStatusExchange;
    }

    public List<String> getAllowedTaskStatuses() {
        return Arrays.asList(this.allowedTaskStatuses.split(","));
    }

    public void setAllowedTaskStatuses(String allowedTaskStatuses) {
        this.allowedTaskStatuses = allowedTaskStatuses;
    }

    public boolean isAlwaysPublishWorkflowStatusEnabled() {
        return alwaysPublishWorkflowStatusEnabled;
    }

    public void setAlwaysPublishWorkflowStatusEnabled(boolean alwaysPublishWorkflowStatusEnabled) {
        this.alwaysPublishWorkflowStatusEnabled = alwaysPublishWorkflowStatusEnabled;
    }
}
