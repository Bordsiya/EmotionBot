package org.example.tgbot.model;

import lombok.Data;


import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Entity(name = "emotion_statistic_ref")
public class EmotionStatisticEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "emotion_statistic_seq")
    private Long id;

    @Column(name = "chat_id")
    private String chatId;

    @Column(name = "angry")
    private Integer angry;

    @Column(name = "disgust")
    private Integer disgust;

    @Column(name = "fear")
    private Integer fear;

    @Column(name = "happy")
    private Integer happy;

    @Column(name = "sad")
    private Integer sad;

    @Column(name = "surprise")
    private Integer surprise;

    @Column(name = "neutral")
    private Integer neutral;

    @Column(name = "time_creation")
    private LocalDate dateCreation;
}
