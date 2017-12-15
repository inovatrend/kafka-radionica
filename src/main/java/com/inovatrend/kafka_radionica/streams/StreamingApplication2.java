package com.inovatrend.kafka_radionica.streams;

import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.KTable;
import org.apache.kafka.streams.kstream.Produced;

import com.inovatrend.kafka_radionica.model.UserMessage;

import java.util.Properties;

public class StreamingApplication2 {

    public StreamingApplication2() {

        Properties config = new Properties();
        config.put(StreamsConfig.APPLICATION_ID_CONFIG, "streaming-app-2");
        config.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        config.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.StringSerde.class);
        config.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, UserMessageSerde.class);
        config.put(StreamsConfig.COMMIT_INTERVAL_MS_CONFIG, 1000);

        StreamsConfig streamConfig = new StreamsConfig(config);

        StreamsBuilder builder = new StreamsBuilder();

        KStream<String, UserMessage> inputStream = builder.stream("user-messages");

        KStream<String, UserMessage> filtered = inputStream.filter(
                (key, value) -> {
                    System.out.println("Processing message: " + value);
                    return value.getPriority() > 3;
                }
        );

        KTable<String, Long> countsTable = filtered.groupByKey().count();
        countsTable.toStream().to("user-msg-counts",
                Produced.with(Serdes.String(), Serdes.Long()));

        KafkaStreams streams = new KafkaStreams(builder.build(), streamConfig);
        streams.start();

    }

    public static void main(String[] args) {
        new StreamingApplication2();
    }
}
