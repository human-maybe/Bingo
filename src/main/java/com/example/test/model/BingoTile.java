package com.example.test.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "bingo_tile")
public class BingoTile {
    @Id
    @Column(nullable = false)
    private Long id;


    @Column(name = "is_done")
    private Boolean isDone;

    @Column(name = "datetime")
    private LocalDateTime whenFinished;

    @ManyToOne
    @JoinColumn(name = "bingo_card_")
    private BingoCard bingoCard;

    @Column(name = "position")
    private Integer position;  // position in the bingo card

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "challenge_id")
    private Challenge challenge;

}