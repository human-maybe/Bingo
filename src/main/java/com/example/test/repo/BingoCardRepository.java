package com.example.test.repo;

import com.example.test.model.BingoCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;

public interface BingoCardRepository extends JpaRepository<BingoCard, Long> {
    @Query("select b from BingoCard b where b.day = ?1")
    BingoCard findByDay(Date day);
}