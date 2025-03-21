package com.example.test.model;

import com.example.test.model.enums.Diffeculty;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Random;

@Getter
@Setter
@Entity
@Table(name = "challenge")
public class Challenge {
    @Id
    @Column(nullable = false)
    private Long id;


    @Enumerated(EnumType.STRING)
    @Column(name = "diffeculty")
    private Diffeculty diffeculty;

    private String level;

    @OneToOne()
    @JoinColumn(name = "goal_id")
    private Goal goal;

    private String description;

    public void formChallenge(String level, Diffeculty diffeculty) {
        double modifier = 0;
        modifier = 0.1 + (Math.random() * 0.9); // random number between 0.1 and 0.9
        switch (diffeculty) {
            case EASY: // 0.1 to 0.9
                description = goal.goalFormat(description, goal.getBaseLevel() + (goal.getBaseLevel() * modifier));
                break;
            case MEDIUM: // 0.7 to 1.4
                 description = goal.goalFormat(description, goal.getBaseLevel() + goal.getBaseLevel() * (modifier+ 0.6));
                break;
            case HARD: //2.2 to 3.0
                 description = goal.goalFormat(description, goal.getBaseLevel() + goal.getBaseLevel() * (modifier+ 2.1));
                break;
        }

    }


}