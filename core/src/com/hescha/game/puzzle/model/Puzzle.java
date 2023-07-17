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

    public int getNumberX() {
        return this.getTiles().length;
    }

    public int getNumberY() {
        return this.getTiles()[0].length;
    }

    public Tile[][] transpose() {
        Tile[][] transposed = new Tile[level.x][level.y];
        for (int i = 0; i < level.x; i++) {
            for (int j = 0; j < level.y; j++) {
                transposed[i][j] = tiles[j][i];
            }
        }
        return transposed;
    }

}

