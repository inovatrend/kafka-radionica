package com.inovatrend.kafka_radionica.model;

import lombok.*;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor @ToString
public class UserMessage {

    private String from;
    private String to;
    private String text;
    private int priority;

}
