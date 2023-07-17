package com.hescha.game.puzzle.screen;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum LevelType {
    LEVEL_3X3(3, 3, 170, 170),
    LEVEL_4X4(4, 4, 128, 128),
    LEVEL_5X5(5, 5, 102, 102),
    LEVEL_3X5(3, 5, 170, 153),
    LEVEL_4X6(4, 6, 128, 128);

    public final int x;
    public final int y;
    public final int imageWidth;
    public final int imageHeight;
}
