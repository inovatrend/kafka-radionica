package com.inovatrend.kafka_radionica.simple;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;

import com.inovatrend.kafka_radionica.model.UserMessage;
import com.inovatrend.kafka_radionica.serdes.UserMessageDeserializer;

import java.util.Arrays;
import java.util.Properties;

public class SimpleConsumer implements Runnable {


    private KafkaConsumer<String, UserMessage> consumer;

    private String[] sourceTopics = {"user-messages"};

    public SimpleConsumer() {

        Properties config = new Properties();
        config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        config.put(ConsumerConfig.GROUP_ID_CONFIG, "user_message_reader");
        config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, UserMessageDeserializer.class);

        consumer = new KafkaConsumer<>(config);
        consumer.subscribe(Arrays.asList(sourceTopics));

        new Thread(this).start();
    }

    @Override
    public void run() {

        while(true) {
            ConsumerRecords<String, UserMessage> records = consumer.poll(1000);
            for (ConsumerRecord<String, UserMessage> record : records) {
                UserMessage value = record.value();
                System.out.println("Message received: " + value);
            }
        }
    }


    public static void main(String[] args) {
        new SimpleConsumer();
    }

}
