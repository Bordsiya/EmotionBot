package org.example.tgbot.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class EmotionalResponse {
    private Long chatId;
    private EmotionalResult result;
}
