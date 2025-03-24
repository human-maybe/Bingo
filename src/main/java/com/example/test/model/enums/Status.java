package com.example.test.model.enums;

public enum Status {
    Current, // current BINGO card
    Completed, // when he gets 1 bingo
    FAILED, // when he gets 0 bingo
    FINISHED // this is only happens when all goals are  Completed
}
