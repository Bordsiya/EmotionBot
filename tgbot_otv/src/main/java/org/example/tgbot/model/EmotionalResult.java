package org.example.tgbot.model;

import lombok.*;

@Getter
@Setter
@ToString
public class EmotionalResult {
    private Double angry;
    private Double disgust;
    private Double fear;
    private Double happy;
    private Double sad;
    private Double surprise;
    private Double neutral;
}
