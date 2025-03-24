package com.example.test.repo;

import com.example.test.model.BingoCard;
import com.example.test.model.enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

public interface BingoCardRepository extends JpaRepository<BingoCard, Long> {
    @Query("select b from BingoCard b where DATE(b.day) = DATE(:date)")
    List<BingoCard> findAllCreatedOnDate(@Param("date") LocalDate date);



    @Query("select b from BingoCard b where b.status = :status")
    List<BingoCard> findByStatus(@Param("status") Status status);

    @Query("select b from BingoCard b where b.day between :dayStart and :dayEnd order by b.day DESC")
    List<BingoCard> findLatestCards(@Param("dayStart") LocalDateTime dayStart, @Param("dayEnd") LocalDateTime dayEnd);
}