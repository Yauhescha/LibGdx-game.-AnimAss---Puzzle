package com.hescha.game.puzzle.model;

import com.hescha.game.puzzle.screen.LevelType;

import java.io.Serializable;

import lombok.Data;

@Data
public class Level implements Serializable {
    LevelType type;
    String category;
    String name;
    String texturePath;
    boolean isNew;

    @Override
    public String toString() {
        return "Level{" +
                "type=" + type +
                ", category=" + category +
                ", name='" + name + '\'' +
                ", texturePath='" + texturePath + '\'' +
                ", isNew=" + isNew +
                '}';
    }
}
