package com.inovatrend.kafka_radionica.serdes;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.serialization.Deserializer;

import com.inovatrend.kafka_radionica.model.UserMessage;

import java.io.IOException;
import java.util.Map;

public class UserMessageDeserializer implements Deserializer<UserMessage> {

    private ObjectMapper mapper = new ObjectMapper();

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {

    }

    @Override
    public UserMessage deserialize(String topic, byte[] data) {
        try {
            return mapper.readValue(data, UserMessage.class) ;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void close() {

    }
}
