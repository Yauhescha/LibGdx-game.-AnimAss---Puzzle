package com.hescha.game.puzzle.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.hescha.game.puzzle.AnimAssPuzzle;
import com.hescha.game.puzzle.util.FontUtil;

public class MainMenuScreen extends ScreenAdapter {
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

        prepareActionButton();

        backgroundColor = Color.WHITE;
    }

    private void prepareActionButton() {
        table.reset();
        Table innerTable = new Table();

            String buttonName = "Play";
            TextButton button = new TextButton(buttonName, skin);
            innerTable.add(button).pad(100).row();
            button.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    AnimAssPuzzle.launcher.setScreen(new GameScreen());
                }
            });

        TextButton exit = new TextButton("Exit", skin);
        innerTable.add(exit).pad(100).row();
        exit.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Gdx.app.exit();
            }
        });

        ScrollPane scrollPane = new ScrollPane(innerTable);
        table.add(scrollPane);
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
