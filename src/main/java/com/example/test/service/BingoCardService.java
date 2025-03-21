package com.example.test.service;

import com.example.test.model.BingoCard;
import com.example.test.repo.BingoCardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@RequiredArgsConstructor    // this is a constructor injection
public class BingoCardService {
    private final BingoCardRepository bingoCardRepository;



    public BingoCard formCard(){
        BingoCard todaysCard = bingoCardRepository.findByDay(new Date());
        if (todaysCard !=  null){
            return todaysCard;
        }
        todaysCard = new BingoCard();
        todaysCard.setDay(new Date());
        bingoCardRepository.save(todaysCard);

         return todaysCard;
    }

}
