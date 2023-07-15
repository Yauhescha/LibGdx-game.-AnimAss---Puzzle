package com.hescha.game.puzzle.model;

import lombok.Data;

@Data
public class Puzzle {
    private int movesNumber;
    private int level;
    private Tile[][] tiles;

    public void incrementMove() {
        movesNumber++;
    }

    public int getNumberX() {
        return this.getTiles().length;
    }

    public int getNumberY() {
        return this.getTiles()[0].length;
    }

}

