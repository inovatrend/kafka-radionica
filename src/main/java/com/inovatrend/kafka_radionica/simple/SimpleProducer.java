package com.inovatrend.kafka_radionica.simple;

import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.serialization.StringSerializer;

import com.inovatrend.kafka_radionica.model.UserMessage;
import com.inovatrend.kafka_radionica.serdes.UserMessageSerializer;

import java.util.Properties;
import java.util.Random;

public class SimpleProducer implements Runnable {


    private final String[] names = {"marko", "ana", "slavko", "maja"};

    private final KafkaProducer<String, UserMessage> producer;

    private String targetTopic = "user-messages";

    public SimpleProducer() {

        Properties config = new Properties();
        config.put(ProducerConfig.ACKS_CONFIG, "1");
        config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, UserMessageSerializer.class);

        producer = new KafkaProducer<>(config);

        new Thread(this).start();
    }

    public static void main(String[] args) {
        new SimpleProducer();
    }

    @Override
    public void run() {
        Random random = new Random();
        while (true) {

            try {
                String from = names[random.nextInt(names.length)];
                String to = names[random.nextInt(names.length)];
                String text = generateMessageText(random);
                int priority = 1+ random.nextInt(5);

                UserMessage message = new UserMessage(from, to, text, priority);

                producer.send(
                        new ProducerRecord<>(targetTopic, from, message),
                        (metadata, exception) -> {
                            if(exception == null)
                                System.out.println("Sent message: " + message);
                            else
                                exception.printStackTrace();
                        });

            } catch (Exception e) {
                e.printStackTrace();
            }
            sleep(500);
        }
    }

    private void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private String generateMessageText(Random random) {
        return "Ovo je poruka broj " + random.nextInt();
    }


}
