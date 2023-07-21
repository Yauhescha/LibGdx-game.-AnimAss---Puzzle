package com.hescha.game.puzzle.util;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net;
import com.badlogic.gdx.utils.Json;
import com.hescha.game.puzzle.model.Level;
import com.hescha.game.puzzle.screen.LevelType;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.PixmapIO;
import com.badlogic.gdx.net.HttpRequestBuilder;

public class LevelUtil {
    public static String LEVEL_PATH = "https://naru-naru.ucoz.ru/animass/puzzle/levels.txt";
    public static String REMOVE_PATH = "https://naru-naru.ucoz.ru/animass/puzzle/";
    public static Json json = new Json();
//        FileHandle file = Gdx.files.internal(chapter.name().toLowerCase() + SlASH + ticketNumber + JPG);
//        if (file.exists()) {
//            loadImage(innerTable, file);
//        } else {
//            file = Gdx.files.internal(chapter.name().toLowerCase() + SlASH + ticketNumber + PNG);
//            if (file.exists()) {
//                loadImage(innerTable, file);
//            }
//        }

    public static void prepareDefaultLevels() {
        Level level = new Level();
        level.setName("First level");
        level.setCategory("Amazonies");
        level.setType(LevelType.LEVEL_4X4);
        level.setNew(false);
        level.setTexturePath("https://naru-naru.ucoz.ru/animass/puzzle/3x3/1.jpg");

        Level levelInternet = new Level();
        levelInternet.setName("levelInternet");
        levelInternet.setCategory("internet");
        levelInternet.setType(LevelType.LEVEL_3X3);
        levelInternet.setNew(true);
        levelInternet.setTexturePath("https://naru-naru.ucoz.ru/animass/puzzle/3x3/2.jpg");

        Level level2 = new Level();
        level2.setName("Second level");
        level2.setCategory("Succubus");
        level2.setType(LevelType.LEVEL_3X3);
        level2.setNew(false);
        level2.setTexturePath("https://naru-naru.ucoz.ru/animass/puzzle/3x3/3.jpg");

        Level level3 = new Level();
        level3.setName("Third level");
        level3.setCategory("Succubus");
        level3.setType(LevelType.LEVEL_3X5);
        level3.setNew(false);
        level3.setTexturePath("https://naru-naru.ucoz.ru/animass/puzzle/3x3/4.jpg");

        Level level4 = new Level();
        level4.setName("Fourth level");
        level4.setCategory("Succubus");
        level4.setType(LevelType.LEVEL_4X6);
        level4.setNew(false);
        level4.setTexturePath("https://naru-naru.ucoz.ru/animass/puzzle/3x3/5.jpg");


        List<Level> levels = new ArrayList<>();
        levels.add(level);
        levels.add(level2);
        levels.add(level3);
        levels.add(level4);
//        levels.add(levelInternet);
//        downloadImage(levelInternet.getTexturePath(), "levels/img.png");
//        levelInternet.setTexturePath("levels/img.png");

        String levelsJson = json.toJson(new ArrayList<Level>());
        System.out.println();
        System.out.println();
        System.out.println(levelsJson);
        System.out.println();
        System.out.println();
        System.out.println();
        Gdx.files.local("levels.json").writeString(levelsJson, false);
    }

    public static ArrayList<Level> loadLevels() {
        String jsonData = Gdx.files.local("levels.json").readString();
        Json json = new Json();
        return json.fromJson(ArrayList.class, Level.class, jsonData);
    }


    public static void loadNewLevels() {
        HttpRequestBuilder requestBuilder = new HttpRequestBuilder();
        Net.HttpRequest httpRequest = requestBuilder.newRequest().method(Net.HttpMethods.GET).url(LEVEL_PATH).build();
        Gdx.net.sendHttpRequest(httpRequest, new Net.HttpResponseListener() {
            @Override
            public void handleHttpResponse(Net.HttpResponse httpResponse) {
                System.out.println("Loading Levels started");
                String jsonData = httpResponse.getResultAsString();
                ArrayList<Level> newLevels = json.fromJson(ArrayList.class, Level.class, jsonData);
                ArrayList<Level> oldLevels = loadLevels();
                newLevels.removeAll(oldLevels);

                newLevels.forEach(level -> downloadImage(level, oldLevels));

                System.out.println("Loading Levels ended");
            }

            @Override
            public void failed(Throwable t) {
                System.out.println("Loading Levels( failed");
                t.printStackTrace();
            }

            @Override
            public void cancelled() {
                System.out.println("Loading Levels cancelled");
            }
        });
    }

    public static void downloadImage(Level level, ArrayList<Level> oldLevels) {
        if (!level.getTexturePath().contains(REMOVE_PATH)) {
            return;
        }
        HttpRequestBuilder requestBuilder = new HttpRequestBuilder();
        Net.HttpRequest httpRequest = requestBuilder.newRequest().method(Net.HttpMethods.GET).url(level.getTexturePath()).build();
        Gdx.net.sendHttpRequest(httpRequest, new Net.HttpResponseListener() {
            @Override
            public void handleHttpResponse(Net.HttpResponse httpResponse) {
                System.out.println("Loading image in progress");
                byte[] imageBytes = httpResponse.getResult();
                Pixmap pixmap = new Pixmap(imageBytes, 0, imageBytes.length);

                level.setTexturePath(level.getTexturePath().replace(REMOVE_PATH, "assets/levels/"));
                savePixmap(pixmap, level.getTexturePath());
                pixmap.dispose();


                oldLevels.add(level);
                String levelsJson = json.toJson(oldLevels);
                Gdx.files.local("levels.json").writeString(levelsJson, false);
                System.out.println("Loading image ended");
            }

            @Override
            public void failed(Throwable t) {
                System.out.println("Loading image failed");
                t.printStackTrace();
            }

            @Override
            public void cancelled() {
                System.out.println("Loading image " + level.getTexturePath() + " cancelled");
            }
        });

    }


    public static void savePixmap(Pixmap pixmap, String savePath) {
        System.out.println("Saving image by path " + savePath);
        PixmapIO.writePNG(Gdx.files.local(savePath), pixmap);
        System.out.println("Image saved");
    }


}
