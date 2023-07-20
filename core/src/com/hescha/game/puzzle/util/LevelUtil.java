package com.hescha.game.puzzle.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net;
import com.badlogic.gdx.utils.Json;
import com.hescha.game.puzzle.model.Level;
import com.hescha.game.puzzle.screen.LevelType;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.PixmapIO;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.net.HttpRequestBuilder;

public class LevelUtil {

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
        level.setTexturePath("levels/3x3/1.jpg");

        Level levelInternet = new Level();
        levelInternet.setName("levelInternet");
        levelInternet.setCategory("internet");
        levelInternet.setType(LevelType.LEVEL_3X3);
        levelInternet.setNew(true);
        levelInternet.setTexturePath("https://naru-naru.ucoz.ru/animass/puzzle/witch-12-.jpg");

        Level level2 = new Level();
        level2.setName("Second level");
        level2.setCategory("Succubus");
        level2.setType(LevelType.LEVEL_3X3);
        level2.setNew(false);
        level2.setTexturePath("levels/3x3/2.jpg");

        Level level3 = new Level();
        level3.setName("Third level");
        level3.setCategory("Succubus");
        level3.setType(LevelType.LEVEL_3X5);
        level3.setNew(false);
        level3.setTexturePath("levels/3x5/1.jpg");

        Level level4 = new Level();
        level4.setName("Fourth level");
        level4.setCategory("Succubus");
        level4.setType(LevelType.LEVEL_4X6);
        level4.setNew(false);
        level4.setTexturePath("levels/4x6/1.jpg");


        List<Level> levels = new ArrayList<>();
        levels.add(level);
        levels.add(level2);
        levels.add(level3);
        levels.add(level4);
        levels.add(levelInternet);
        downloadImage(levelInternet.getTexturePath(), "levels/img.png");
        levelInternet.setTexturePath("levels/img.png");
        Json json = new Json();
        String levelsJson = json.toJson(levels);
        Gdx.files.local("levels.json").writeString(levelsJson, false);
    }

    public static ArrayList<Level> loadLevels() {
        String jsonData = Gdx.files.local("levels.json").readString();
        Json json = new Json();
        return json.fromJson(ArrayList.class, Level.class, jsonData);
    }


    public static void downloadImage(String imageUrl, final String savePath) {
        HttpRequestBuilder requestBuilder = new HttpRequestBuilder();
        Net.HttpRequest httpRequest = requestBuilder.newRequest().method(Net.HttpMethods.GET).url(imageUrl).build();
        Gdx.net.sendHttpRequest(httpRequest, new Net.HttpResponseListener() {
            @Override
            public void handleHttpResponse(Net.HttpResponse httpResponse) {
                System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
                byte[] imageBytes = httpResponse.getResult();
                Pixmap pixmap = new Pixmap(imageBytes, 0, imageBytes.length);
                savePixmap(pixmap, savePath);
                pixmap.dispose();
            }

            @Override
            public void failed(Throwable t) {
                System.out.println("!!!!!!!!!!!!2222222222222222222!!!!!!!!!!!!!!!!");
                System.out.println();
                t.printStackTrace();
                // Handle failure
            }

            @Override
            public void cancelled() {
                System.out.println("!!!!!!!!!!e333333333333333333333!!!!!!!!!!!!!!!!!!");
                // Handle cancellation
            }
        });
    }


    public static void savePixmap(Pixmap pixmap, String savePath) {
        PixmapIO.writePNG(Gdx.files.local(savePath), pixmap);
        System.out.println("LEVEL SAVED!!!!!!!!!!!");
    }


}
