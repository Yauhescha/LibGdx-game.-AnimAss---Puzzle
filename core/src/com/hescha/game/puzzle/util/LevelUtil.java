package com.hescha.game.puzzle.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Json;
import com.hescha.game.puzzle.model.Level;
import com.hescha.game.puzzle.screen.LevelType;

import java.util.ArrayList;
import java.util.List;

public class LevelUtil {


    public static void prepareDefaultLevels() {
        Level level = new Level();
        level.setName("First level");
        level.setCategory("Amazonies");
        level.setType(LevelType.LEVEL_4X4);
        level.setNew(false);
        level.setTexturePath("levels/3x3/1.jpg");

        Level level2 = new Level();
        level2.setName("Second level");
        level2.setCategory("Succubus");
        level2.setType(LevelType.LEVEL_3X3);
        level2.setNew(false);
        level2.setTexturePath("levels/3x3/2.jpg");


        List<Level> levels = new ArrayList<>();
        levels.add(level);
        levels.add(level2);


        Json json = new Json();
        String levelsJson = json.toJson(levels);
        Gdx.files.local("levels.json").writeString(levelsJson, false);
    }

    public static ArrayList<Level> loadLevels() {
        String jsonData = Gdx.files.local("levels.json").readString();
        Json json = new Json();
        return json.fromJson(ArrayList.class, Level.class, jsonData);
    }

}
