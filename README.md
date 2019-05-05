```sequence
title: CNP流程时序图
participant Clients as client 
participant Receiver as receivers
participant CheckerManage as checker
participant ProcessorManage as processors 
client->receivers: message
receivers->checker: droitRequest
checker->checker: grant
checker->processors: doritGather

processors->processors: handle
Note Over receivers: droitRequest constructor
Note Over checker: granters
Note Over processors: processors

```
