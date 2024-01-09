package org.example.tgbot.repository;

import org.example.tgbot.model.EmotionStatisticEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;


public interface EmotionStatisticRepository extends CrudRepository<EmotionStatisticEntity, Long> {

    @Query("SELECT COUNT(esr) FROM emotion_statistic_ref esr")
    Long countColumns();

    @Query("SELECT AVG(esr.angry) FROM emotion_statistic_ref esr GROUP BY esr.chatId")
    List<Integer> angryEmotionAvg();

    @Query("SELECT AVG(esr.disgust) FROM emotion_statistic_ref esr GROUP BY esr.chatId")
    List<Integer> disgustEmotionAvg();

    @Query("SELECT AVG(esr.fear) FROM emotion_statistic_ref esr GROUP BY esr.chatId")
    List<Integer> fearEmotionAvg();

    @Query("SELECT AVG(esr.happy) FROM emotion_statistic_ref esr GROUP BY esr.chatId")
    List<Integer> happyEmotionAvg();

    @Query("SELECT AVG(esr.sad) FROM emotion_statistic_ref esr GROUP BY esr.chatId")
    List<Integer> sadEmotionAvg();

    @Query("SELECT AVG(esr.surprise) FROM emotion_statistic_ref esr GROUP BY esr.chatId")
    List<Integer> surpriseEmotionAvg();

    @Query("SELECT AVG(esr.neutral) FROM emotion_statistic_ref esr GROUP BY esr.chatId")
    List<Integer> neutralEmotionAvg();

    @Query("SELECT COUNT(esr) FROM emotion_statistic_ref esr WHERE esr.chatId = :chatId")
    Long countMyColumns(@Param("chatId") String chatId);

    @Query("SELECT COUNT(esr) FROM emotion_statistic_ref esr WHERE esr.chatId = :chatId AND esr.dateCreation >= :fromDate AND esr.dateCreation <= :toDate")
    Long countMyColumnsByDate(@Param("chatId") String chatId, @Param("fromDate") LocalDate fromDate, @Param("toDate") LocalDate toDate);

    @Query("select AVG(esr.angry) FROM emotion_statistic_ref esr WHERE esr.chatId = :chatId AND esr.dateCreation >= :fromDate AND esr.dateCreation <= :toDate")
    Integer myAngryEmotionAvgByDate(@Param("chatId") String chatId, @Param("fromDate") LocalDate fromDate, @Param("toDate") LocalDate toDate);

    @Query("select AVG(esr.disgust) FROM emotion_statistic_ref esr WHERE esr.chatId = :chatId AND esr.dateCreation >= :fromDate AND esr.dateCreation <= :toDate")
    Integer myDisgustEmotionAvgByDate(@Param("chatId") String chatId, @Param("fromDate") LocalDate fromDate, @Param("toDate") LocalDate toDate);

    @Query("select AVG(esr.fear) FROM emotion_statistic_ref esr WHERE esr.chatId = :chatId AND esr.dateCreation >= :fromDate AND esr.dateCreation <= :toDate")
    Integer myFearEmotionAvgByDate(@Param("chatId") String chatId, @Param("fromDate") LocalDate fromDate, @Param("toDate") LocalDate toDate);

    @Query("select AVG(esr.happy) FROM emotion_statistic_ref esr WHERE esr.chatId = :chatId AND esr.dateCreation >= :fromDate AND esr.dateCreation <= :toDate")
    Integer myHappyEmotionAvgByDate(@Param("chatId") String chatId, @Param("fromDate") LocalDate fromDate, @Param("toDate") LocalDate toDate);

    @Query("select AVG(esr.sad) FROM emotion_statistic_ref esr WHERE esr.chatId = :chatId AND esr.dateCreation >= :fromDate AND esr.dateCreation <= :toDate")
    Integer mySadEmotionAvgByDate(@Param("chatId") String chatId, @Param("fromDate") LocalDate fromDate, @Param("toDate") LocalDate toDate);

    @Query("select AVG(esr.surprise) FROM emotion_statistic_ref esr WHERE esr.chatId = :chatId AND esr.dateCreation >= :fromDate AND esr.dateCreation <= :toDate")
    Integer mySurpriseEmotionAvgByDate(@Param("chatId") String chatId, @Param("fromDate") LocalDate fromDate, @Param("toDate") LocalDate toDate);

    @Query("select AVG(esr.neutral) FROM emotion_statistic_ref esr WHERE esr.chatId = :chatId AND esr.dateCreation >= :fromDate AND esr.dateCreation <= :toDate")
    Integer myNeutralEmotionAvgByDate(@Param("chatId") String chatId, @Param("fromDate") LocalDate fromDate, @Param("toDate") LocalDate toDate);
}
