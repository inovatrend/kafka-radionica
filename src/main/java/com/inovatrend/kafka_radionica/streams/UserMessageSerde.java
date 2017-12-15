package com.inovatrend.kafka_radionica.streams;

import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serializer;

import com.inovatrend.kafka_radionica.model.UserMessage;
import com.inovatrend.kafka_radionica.serdes.UserMessageDeserializer;
import com.inovatrend.kafka_radionica.serdes.UserMessageSerializer;

import java.util.Map;

public class UserMessageSerde implements Serde<UserMessage> {

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {

    }

    @Override
    public void close() {

    }

    @Override
    public Serializer<UserMessage> serializer() {
        return new UserMessageSerializer();
    }

    @Override
    public Deserializer<UserMessage> deserializer() {
        return new UserMessageDeserializer();
    }
}
