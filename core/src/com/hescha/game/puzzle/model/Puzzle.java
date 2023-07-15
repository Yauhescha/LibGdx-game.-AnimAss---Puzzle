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

    public Tile[][] transpose() {
        Tile[][] transposed = new Tile[level][level];
        for (int i = 0; i < level; i++) {
            for (int j = 0; j < level; j++) {
                transposed[i][j] = tiles[j][i];
            }
        }
        return transposed;
    }

}

