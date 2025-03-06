#!/bin/sh

./kafka-console-consumer.sh --bootstrap-server $MY_CLUSTER_KAFKA_BOOTSTRAP_PORT_9092_TCP --topic dbz.public.item --from-beginning

./kafka-topics.sh --bootstrap-server $MY_CLUSTER_KAFKA_BOOTSTRAP_PORT_9092_TCP --list
