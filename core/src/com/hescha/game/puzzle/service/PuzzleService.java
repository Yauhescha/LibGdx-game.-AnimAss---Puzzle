package com.hescha.game.puzzle.service;


import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.hescha.game.puzzle.model.Puzzle;
import com.hescha.game.puzzle.model.Tile;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class PuzzleService {

    public static int SIZE = 512 / 3;
    public static final int[][] DIRECTIONS = new int[][]{{0, 1}, {1, 0}, {0, -1}, {-1, 0}};
    public static final Random RANDOM = new Random();

    public static Puzzle newPuzzle(int difficulty, TextureRegion[][] textureRegions) {
        Puzzle puzzle = new Puzzle();
        puzzle.setLevel(difficulty);
        puzzle.setTiles(initTiles(difficulty, textureRegions));
        shuffle(puzzle);
        return puzzle;
    }

    private static void shuffle(Puzzle puzzle) {
        int counter = 0;
        while (counter < 10_000) {
            int x = RANDOM.nextInt(puzzle.getLevel());
            int y = RANDOM.nextInt(puzzle.getLevel());
            makeMove(puzzle, x, y);
            counter++;
        }
    }

    private static Tile[][] initTiles(int level, TextureRegion[][] textureRegions) {
        Tile[][] tiles = new Tile[level][level];
        for (int i = 0; i < level; i++) {
            for (int j = 0; j < level; j++) {
                int value = i * level + j + 1;
                if (value == level * level) {
                    tiles[i][j] = new Tile(null);
                } else {
                    tiles[i][j] = new Tile(textureRegions[i][j]);
                }
                tiles[i][j].setX(i * SIZE);
                tiles[i][j].setY(j * SIZE);
                tiles[i][j].setNumber(value);
            }
        }
        return tiles;
    }

    public static void makeMove(Puzzle puzzle, int x, int y) {
        Tile[][] tiles = puzzle.getTiles();
        for (int[] direction : DIRECTIONS) {
            int newX = x + direction[0];
            int newY = y + direction[1];

            if (newX >= 0 && newX < puzzle.getNumberX()
                    && newY >= 0 && newY < puzzle.getNumberY()) {
                if (tiles[newY][newX].getTextureRegion() == null) {
                    Tile tile1 = tiles[newY][newX];
                    float currentX1 = tile1.getX();
                    float currentY1 = tile1.getY();

                    Tile tile2 = tiles[y][x];
                    float currentX2 = tile2.getX();
                    float currentY2 = tile2.getY();

                    tiles[newY][newX] = tile2;
                    tile2.setX(currentX1);
                    tile2.setY(currentY1);

                    tiles[y][x] = tile1;
                    tile1.setX(currentX2);
                    tile1.setY(currentY2);

                    puzzle.incrementMove();
                    break;
                }
            }
        }
    }

    public static boolean isSolved(Puzzle puzzle) {
        Tile[][] tiles = puzzle.transpose();
        List<Tile> collect1 = Arrays.stream(tiles)
                .flatMap(tiles1 -> {
                    List<Tile> collect = Arrays.stream(tiles1).collect(Collectors.toList());
                    Collections.reverse(collect);
                    return collect.stream();
                })
                .collect(Collectors.toList());
        Collections.reverse(collect1);
        int counter = 0;
        for (int i = 0; i < puzzle.getNumberX(); i++) {
            for (int j = 0; j < puzzle.getNumberY(); j++) {
                if (i * puzzle.getNumberX() + j + 1 != collect1.get(counter).getNumber()) {
                    return false;
                }
                counter++;
            }
        }
        return true;
    }
}
