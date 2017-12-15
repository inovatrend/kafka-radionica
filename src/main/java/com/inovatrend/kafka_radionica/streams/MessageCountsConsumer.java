package com.inovatrend.kafka_radionica.streams;

import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.KStream;

import com.inovatrend.kafka_radionica.model.UserMessage;

import java.util.Properties;

public class MessageCountsConsumer {

    public MessageCountsConsumer() {

        Properties config = new Properties();
        config.put(StreamsConfig.APPLICATION_ID_CONFIG, "message-counts-consumer-app");
        config.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        config.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.StringSerde.class);
        config.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.LongSerde.class);

        StreamsConfig streamConfig = new StreamsConfig(config);

        StreamsBuilder builder = new StreamsBuilder();
        KStream<String, Long> inputStream = builder.stream("high-priority-msg-counts");

        inputStream.foreach (
                (key, value) -> System.out.println(key + "sent " + value + " messages")
        );

        KafkaStreams streams = new KafkaStreams(builder.build(), streamConfig);
        streams.start();

    }

    public static void main(String[] args) {
        new MessageCountsConsumer();
    }
}
