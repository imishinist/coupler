package com.imishinist.kafka_practice;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;

public class NonClusterConsumer {
    public static void main(String[] args) {
        String bootstrapServers = System.getenv("BOOTSTRAP_SERVERS");
        Properties properties = new Properties();
        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        properties.put(ConsumerConfig.GROUP_ID_CONFIG, "java-consumer-group");
        properties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        properties.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "false");

        KafkaConsumer<String, String> consumer =
            new KafkaConsumer<>(properties, new StringDeserializer(), new StringDeserializer());
        consumer.subscribe(Collections.singletonList("my-topic"));
        try {
            while (true) {
                ConsumerRecords<String, String> records = consumer.poll(Duration.ofSeconds(60L));
                for (ConsumerRecord<String, String> record :records) {
                    System.out.printf("%s:%s%n", record.offset(), record.value());
                }
                consumer.commitSync();
            }
        } finally {
            consumer.close();
        }
    }
}
