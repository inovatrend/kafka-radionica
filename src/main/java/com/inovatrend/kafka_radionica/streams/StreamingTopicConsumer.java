package com.inovatrend.kafka_radionica.streams;

import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.KStream;

import com.inovatrend.kafka_radionica.model.UserMessage;

import java.util.Properties;

public class StreamingTopicConsumer {

    public StreamingTopicConsumer() {

        Properties config = new Properties();
        config.put(StreamsConfig.APPLICATION_ID_CONFIG, "streaming-consumer-app");
        config.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "192.168.128.105:9092");
        config.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.StringSerde.class);
        config.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, UserMessageSerde.class);

        StreamsConfig streamConfig = new StreamsConfig(config);

        StreamsBuilder builder = new StreamsBuilder();
        KStream<String, UserMessage> inputStream = builder.stream("user-messages");

        inputStream.foreach (
                (key, value) -> System.out.println(value)
        );

        KafkaStreams streams = new KafkaStreams(builder.build(), streamConfig);
        streams.start();

    }

    public static void main(String[] args) {
        new StreamingTopicConsumer();
    }
}
