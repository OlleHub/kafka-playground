#!/bin/sh

./bin/kafka-topics.sh --bootstrap-server $MY_CLUSTER_KAFKA_BOOTSTRAP_PORT_9092_TCP --list

./bin/kafka-console-consumer.sh --bootstrap-server $MY_CLUSTER_KAFKA_BOOTSTRAP_PORT_9092_TCP --topic dbz.public.item --from-beginning

./bin/kafka-console-consumer.sh --bootstrap-server $MY_CLUSTER_KAFKA_BOOTSTRAP_PORT_9092_TCP --topic output-items --from-beginning
