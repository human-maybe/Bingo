package com.example.test.service;


import com.example.test.model.BingoCard;
import com.example.test.repo.BingoTileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BingoTIleService {
    private final BingoTileRepository bingoTileRepository;

    public BingoCard formCard(){

    }

}
