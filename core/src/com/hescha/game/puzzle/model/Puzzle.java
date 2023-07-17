package com.hescha.game.puzzle.model;

import com.hescha.game.puzzle.screen.LevelType;

import lombok.Data;

@Data
public class Puzzle {
    private int movesNumber;
    private LevelType level;
    private Tile[][] tiles;

    public void incrementMove() {
        movesNumber++;
    }
    public void clearMove() {
        movesNumber=0;
    }

}

