package com.hescha.game.puzzle.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.ScreenUtils;
import com.hescha.game.puzzle.util.FontUtil;

public class GameScreen extends ScreenAdapter {
    Stage stage;
    BitmapFont font;
    TextButton.TextButtonStyle skin;
    Table table;
    private Color backgroundColor;

    @Override
    public void show() {
        table = new Table();
        table.setFillParent(true);

        stage = new Stage();
        stage.addActor(table);
        Gdx.input.setInputProcessor(stage);

        font = FontUtil.generateFont(Color.BLACK);

        skin = new TextButton.TextButtonStyle();
        skin.font = font;
        skin.fontColor = Color.BLACK;

        backgroundColor = Color.WHITE;
    }



    @Override
    public void render(float delta) {
        ScreenUtils.clear(backgroundColor);
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
    }

    @Override
    public void dispose() {
        font.dispose();
        stage.dispose();
    }
}
