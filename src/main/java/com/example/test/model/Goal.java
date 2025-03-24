package com.example.test.model;

import com.example.test.model.enums.Proirity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "goal")
public class Goal {
    @Id
    @Column(nullable = false)
    private Long id;

    @Column(name = "description")
    private String description; // read {X} pages, push ups {X} times goals will have one constrain for now

    @Column(name = "baselevel")
    private double baseLevel; // determines the base value of the goal eg 10 pages for reading , 20 push ups

    @Enumerated(EnumType.STRING)
    @Column(name = "proirity")
    private Proirity proirity;  // used to make bingo card focus on it more

    @Column(name = "is_active")
    private Boolean isActive;

    public  String setGoalLevel(String text, double level) {
        int startIndex = text.indexOf("{");
        int endIndex = text.indexOf("}");

        // If both brackets are found and in the correct order
        if (startIndex != -1 && endIndex != -1 && startIndex < endIndex) {
            // Replace the placeholder with the actual value
            String result = text.substring(0, startIndex) +
                    level +
                    text.substring(endIndex + 1);
            return result;
        } else {
            // Return original text if no valid placeholder found
            return text;
        }
    }


}