package com.hescha.game.puzzle.model;

import com.hescha.game.puzzle.screen.LevelType;

import java.io.Serializable;

import lombok.Data;

@Data
public class Level implements Serializable {
    private LevelType type;
    private String category;
    private String name;
    private String texturePath;
    private boolean isNew;
}
