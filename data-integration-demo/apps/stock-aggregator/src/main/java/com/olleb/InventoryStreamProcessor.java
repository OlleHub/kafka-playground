package com.olleb;

import java.io.IOException;
import java.util.Properties;
import java.util.Random;

import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Produced;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.olleb.model.Event;
import com.olleb.model.Item;

import io.quarkus.runtime.ShutdownEvent;
import io.quarkus.runtime.StartupEvent;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;

@ApplicationScoped
public class InventoryStreamProcessor {

    private final ObjectMapper objectMapper = new ObjectMapper();
    private Random random = new Random();
    private KafkaStreams streams;

    @ConfigProperty(name = "bootstrap.servers.config", defaultValue = "localhost:9092")
    String bootstrapServersConfig;

    @ConfigProperty(name = "topic.input", defaultValue = "dbz.public.item")
    String inputTopic;

    @ConfigProperty(name = "topic.output", defaultValue = "output-items")
    String outputTopic;

    public void onStart(@Observes final StartupEvent startupEvent) {
        StreamsBuilder builder = new StreamsBuilder();

        KStream<String, String> stream = builder.stream(inputTopic,
                Consumed.with(Serdes.String(), Serdes.String()));

        stream.mapValues(value -> {
            try {
                Event event = objectMapper.readValue(value, Event.class);
                Item afterItem = event.getPayload().getAfter();
                if (afterItem != null) {
                    afterItem.setStock(random.nextInt(50) + 1);

                    return objectMapper.writeValueAsString(afterItem);
                }
                return null;
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        })
                .filter((k, v) -> v != null)
                .to(outputTopic, Produced.with(Serdes.String(), Serdes.String()));

        streams = new KafkaStreams(builder.build(), getStreamProperties());
        streams.start();
    }

    public void onStop(@Observes final ShutdownEvent shutdownEvent) {
        streams.close();
    }

    private Properties getStreamProperties() {
        Properties properties = new Properties();
        properties.put(StreamsConfig.APPLICATION_ID_CONFIG, "inventory-stream-processor");
        properties.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServersConfig);
        properties.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass());
        properties.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass());
        return properties;
    }
}