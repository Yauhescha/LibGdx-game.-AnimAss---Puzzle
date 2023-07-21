package com.hescha.game.puzzle.util;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net;
import com.badlogic.gdx.utils.Json;
import com.hescha.game.puzzle.model.Level;
import com.hescha.game.puzzle.screen.LevelType;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.PixmapIO;
import com.badlogic.gdx.net.HttpRequestBuilder;

public class LevelUtil {

    public static ArrayList<Level> loadLevels() {
        String jsonData = Gdx.files.internal("levels/levels.json").readString();
        Json json = new Json();
        return json.fromJson(ArrayList.class, Level.class, jsonData);
    }
}
