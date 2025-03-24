package com.example.test.model;

import com.example.test.model.enums.Status;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "bingo_card")
public class BingoCard {
    @Id
    @Column(nullable = false)
    private Long id;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "day", nullable = false)
    private LocalDateTime day; // this is the date of the card

    @OneToMany(mappedBy = "bingoCard", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BingoTile> bingoTiles = new ArrayList<>();

    @Enumerated
    @Column(name = "status")
    private Status status;

    @Column(name = "goals_finished", nullable = false)
    private int goalsFinished = 0;

    public void addBingoTile(BingoTile tile) {
        if (bingoTiles == null) {
            bingoTiles = new ArrayList<>();
        }
        tile.setBingoCard(this);
        bingoTiles.add(tile);
    }

    public void addBingoTiles(List<BingoTile> tiles) {
        if (bingoTiles == null) {
            bingoTiles = new ArrayList<>();
        }
        tiles.forEach(tile -> {
            tile.setBingoCard(this);
            bingoTiles.add(tile);
        });
    }

    public void deleteBingoTile(BingoTile tile) {
        tile.setBingoCard(null);
        bingoTiles.remove(tile);
    }


}