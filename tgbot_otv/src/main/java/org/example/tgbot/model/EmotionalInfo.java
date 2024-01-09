package org.example.tgbot.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
public class EmotionalInfo {
    private String chatId;
    private byte[] photoBytes;
}
