package com.inovatrend.kafka_radionica.serdes;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.serialization.Serializer;

import com.inovatrend.kafka_radionica.model.UserMessage;

import java.util.Map;

public class UserMessageSerializer implements Serializer<UserMessage> {

    private ObjectMapper mapper = new ObjectMapper();

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {

    }

    @Override
    public byte[] serialize(String topic, UserMessage data) {

        try {
            String json = mapper.writeValueAsString(data);
            return json.getBytes();
        } catch (Exception e) {
            e.printStackTrace();
            return new byte[0];
        }
    }

    @Override
    public void close() {

    }
}
