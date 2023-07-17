package com.hescha.game.puzzle.service;


import static com.hescha.game.puzzle.AnimAssPuzzle.WORLD_WIDTH;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.hescha.game.puzzle.model.Puzzle;
import com.hescha.game.puzzle.model.Tile;
import com.hescha.game.puzzle.screen.LevelType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class PuzzleService {
    public static final int[][] DIRECTIONS = new int[][]{{0, 1}, {1, 0}, {0, -1}, {-1, 0}};
    public static final Random RANDOM = new Random();

    public static Puzzle newPuzzle(LevelType levelType, TextureRegion[][] textureRegions) {
        Puzzle puzzle = new Puzzle();
        puzzle.setLevel(levelType);
        puzzle.setTiles(initTiles(levelType, textureRegions));
        shuffle(puzzle);
        return puzzle;
    }

    private static void shuffle(Puzzle puzzle) {
        int counter = 0;
        while (counter < 10_000) {
            int x = RANDOM.nextInt(puzzle.getLevel().x);
            int y = RANDOM.nextInt(puzzle.getLevel().y);
            makeMove(puzzle, x, y);
            counter++;
        }
    }

    private static Tile[][] initTiles(LevelType level, TextureRegion[][] textureRegions) {
        float xPadding = (WORLD_WIDTH - level.x * level.imageWidth) / 2;
        List<List<TextureRegion>> transpose = transpose(rotateMatrixClockwise(textureRegions));
        Tile[][] tiles = new Tile[level.x][level.y];
        int counter = 0;
        for (int i = 0; i < level.x; i++) {
            for (int j = 0; j < level.y; j++) {
                if (counter == level.y * (level.x - 1)) {
                    tiles[i][j] = new Tile(level, null);
                } else {
                    tiles[i][j] = new Tile(level, transpose.get(i).get(j));
                }
                tiles[i][j].setX(xPadding + i * level.imageWidth);
                tiles[i][j].setY(j * level.imageHeight);

                tiles[i][j].setNumber(counter);
                counter++;
            }
        }
        return tiles;
    }

    public static TextureRegion[][] rotateMatrixClockwise(TextureRegion[][] matrix) {
        int rows = matrix.length;
        int cols = matrix[0].length;

        TextureRegion[][] rotatedMatrix = new TextureRegion[cols][rows];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                rotatedMatrix[j][rows - i - 1] = matrix[i][j];
            }
        }

        return rotatedMatrix;
    }


    private static List<List<TextureRegion>> transpose(TextureRegion[][] array) {
        List<List<TextureRegion>> list = new ArrayList<>();
        int rows = array.length;
        for (int i = 0; i < rows; i++) {
            List<TextureRegion> sublist = new ArrayList<>();
            sublist.addAll(Arrays.asList(array[i]));
            list.add(sublist);
        }
        return list;
    }

    public static void makeMove(Puzzle puzzle, int y, int x) {
        Tile[][] tiles = puzzle.getTiles();
        for (int[] direction : DIRECTIONS) {
            int newX = x + direction[0];
            int newY = y + direction[1];

            if (newX >= 0 && newX < puzzle.getLevel().x
                    && newY >= 0 && newY < puzzle.getLevel().y) {
                if (tiles[newX][newY].getTextureRegion() == null) {
                    Tile tile1 = tiles[newX][newY];
                    float currentX1 = tile1.getX();
                    float currentY1 = tile1.getY();

                    Tile tile2 = tiles[x][y];
                    float currentX2 = tile2.getX();
                    float currentY2 = tile2.getY();

                    tiles[newX][newY] = tile2;
                    tile2.setX(currentX1);
                    tile2.setY(currentY1);

                    tiles[x][y] = tile1;
                    tile1.setX(currentX2);
                    tile1.setY(currentY2);

                    puzzle.incrementMove();
                    break;
                }
            }
        }
    }

    public static boolean isSolved(Puzzle puzzle) {
        Tile[][] tiles = puzzle.getTiles();
        int counter = 0;
        for (Tile[] tile : tiles) {
            for (int j = 0; j < tiles[0].length; j++) {
                if (counter != tile[j].getNumber()) {
                    return false;
                }
                counter++;
            }
        }
        return true;
    }
}
