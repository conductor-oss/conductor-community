# Conductor OSS community modules

This repository hosts all the community contributed modules and extensions for 
[Conductor OSS](https://github.com/conductor-oss/conductor)

## ⭐ Conductor OSS
Show support for the Conductor OSS.  Please help spread the awareness by starring this repo.

[![GitHub stars](https://img.shields.io/github/stars/conductor-oss/conductor.svg?style=social&label=Star&maxAge=2592000)](https://GitHub.com/conductor-oss/conductor/stargazers/)


### What is _this_ repository?
Conductor OSS is an extensible platform that allows users to bring in their own persistence, queues, integrations eventing systems such as SQS, NATS, AMQP etc.

For the list of artifacts published please see the table below:

| Parent Folder | Description |
| ----------- | ----- |
|[event-queue](event-queue/README.md)| Support for external eventing systems like AMQP and NATS |
| [external-payload-storage](external-payload-storage/README.md) | Storage for large workflow payloads |
| [index](index/README.md)| Indexing for searching workflows |
|[metrics](metrics/README.md)| Support for various metrics integrations including Datadog and Prometheus |
|[persistence](persistence/README.md)| Persistence for metadata, execution and queue implementation |
| [task](task/README.md)| Various system tasks - Kafka Publish
| [lock](lock/README.md)| Workflow execution lock implementation |
|  [workflow-event-listener](workflow-event-listener/README.md)| Workflow Status Listener and Binary compatibility with previously published conductor-contribs |


## FAQ
#### Why separate repository?
The number of contributions, especially newer implementations of the core contracts in Conductor has increased over the past few years. 
There is interest in the community to contribute more implementations. 
To streamline the support and release of the existing community-contributed implementations and future ones, we are creating a new repository dedicated to hosting just contributions. 
Conductor users who wish to use a contributed module will have a dedicated place to ask questions directly to fellow members of the community. 

Having a separate repository will allow us to scale the contributions and also ensure we are able to review and merge PRs in a timely fashion.

#### How often builds are published?
Similar to core Conductor the builds are published often with each major release.
Release numbers are kept in sync with main Conductor releases, which removes the need for a version compatibility matrix.

#### How do I get help?
Please join the [Slack community](https://join.slack.com/t/orkes-conductor/shared_invite/zt-xyxqyseb-YZ3hwwAgHJH97bsrYRnSZg) for help.

#### How do I add new modules here?
1. Start with a proposal by posting on the discussion
2. Send a PR

