package com.example.test.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "bingo_card")
public class BingoCard {
    @Id
    @Column(nullable = false)
    private Long id;

    @Temporal(TemporalType.DATE)
    @Column(name = "day", nullable = false)
    private Date day = new Date();

    @OneToMany(mappedBy = "bingoCard", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BingoTile> bingoTiles = new ArrayList<>();

    public void addBingoTile(BingoTile tile){
        if (bingoTiles == null){
            bingoTiles =  new ArrayList<>();
        }
        tile.setBingoCard(this);
        bingoTiles.add(tile);
    }

    public void deleteBingoTile(BingoTile tile){
        tile.setBingoCard(null);
        bingoTiles.remove(tile);
    }



}