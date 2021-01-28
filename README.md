# Coupler


## run

### bootstrap

```bash
make bootstrap
```

### deploy kafka

```bash
make deploy-kafka
```


### run test

run-as script is simple wrapper.

```bash
$ ./bin/run-as kafka-consumer bin/kafka-console-consumer.sh --bootstrap-server coupler-cluster-kafka-bootstrap:9092 --topic my-topic --from-beginning
$ ./bin/run-as kafka-producer bin/kafka-console-producer.sh --broker-list coupler-cluster-kafka-bootstrap:9092 --topic my-topic
```

- [ ] interface definition
- [ ] static check interface
- [ ] data source and destination selectable
- [ ] any language
