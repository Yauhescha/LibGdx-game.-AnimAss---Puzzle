package com.hescha.game.puzzle.model;

import com.hescha.game.puzzle.screen.LevelType;

import java.io.Serializable;
import java.util.Objects;

import lombok.Data;

@Data
public class Level implements Serializable {
    private LevelType type;
    private String category;
    private String name;
    private String texturePath;
    private boolean isNew;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Level level = (Level) o;
        return type == level.type
                && Objects.equals(category, level.category)
                && Objects.equals(name, level.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, category, name);
    }
}
