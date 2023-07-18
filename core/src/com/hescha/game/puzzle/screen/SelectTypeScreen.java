package com.hescha.game.puzzle.screen;

import static com.hescha.game.puzzle.AnimAssPuzzle.WORLD_HEIGHT;
import static com.hescha.game.puzzle.AnimAssPuzzle.WORLD_WIDTH;
import static com.hescha.game.puzzle.AnimAssPuzzle.BACKGROUND_COLOR;
import static com.hescha.game.puzzle.screen.LevelType.*;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.hescha.game.puzzle.AnimAssPuzzle;
import com.hescha.game.puzzle.MyFunctionalInterface;
import com.hescha.game.puzzle.util.FontUtil;

public class SelectTypeScreen extends ScreenAdapter {
    public static SelectTypeScreen screen;
    private Stage stage;
    private BitmapFont font;
    private Table innerTable;

    private Viewport viewport;

    @Override
    public void show() {
        screen = this;
        OrthographicCamera camera = new OrthographicCamera(WORLD_WIDTH, WORLD_HEIGHT);
        camera.position.set(WORLD_WIDTH / 2, WORLD_HEIGHT / 2, 0);
        camera.update();
        viewport = new FitViewport(WORLD_WIDTH, WORLD_HEIGHT, camera);
        viewport.apply(true);
        Texture buttonTexture = new Texture(Gdx.files.internal("ui/button.png"));
        Texture headerTexture = new Texture(Gdx.files.internal("ui/header.png"));

        Table table = new Table();
        table.setFillParent(true);
        font = FontUtil.generateFont(Color.BLACK);
        innerTable = new Table();
        innerTable.setFillParent(true);


        createButton(headerTexture, "CATEGORIES", 50, null);
        createButton(buttonTexture, "BACK", 100, addAction(() -> AnimAssPuzzle.launcher.setScreen(MainMenuScreen.screen)));
        createButton(buttonTexture, "3x3", 10, addAction(() -> AnimAssPuzzle.launcher.setScreen(new SelectCategoryScreen(LEVEL_3X3))));
        createButton(buttonTexture, "4x4", 10, addAction(() -> AnimAssPuzzle.launcher.setScreen(new SelectCategoryScreen(LEVEL_4X4))));
        createButton(buttonTexture, "5x5", 10, addAction(() -> AnimAssPuzzle.launcher.setScreen(new SelectCategoryScreen(LEVEL_5X5))));
        createButton(buttonTexture, "3x5", 50, addAction(() -> AnimAssPuzzle.launcher.setScreen(new SelectCategoryScreen(LEVEL_3X5))));
        createButton(buttonTexture, "4x6", 10, addAction(() -> AnimAssPuzzle.launcher.setScreen(new SelectCategoryScreen(LEVEL_4X6))));


        ScrollPane scrollPane = new ScrollPane(innerTable);
        table.add(scrollPane);
        stage = new Stage(viewport);
        stage.addActor(table);
        Gdx.input.setInputProcessor(stage);

    }

    private void createButton(Texture headerTexture, String CATEGORIES, int padBottom, EventListener listener) {
        TextureRegion headerCover = new TextureRegion(headerTexture);
        TextureRegionDrawable buttonDrawable0 = new TextureRegionDrawable(headerCover);
        ImageTextButton imageTextButton0 = new ImageTextButton(CATEGORIES, new ImageTextButton.ImageTextButtonStyle(buttonDrawable0, null, null, font));
        innerTable.add(imageTextButton0).center().padTop(10).padBottom(padBottom).row();
        if (listener != null) {
            imageTextButton0.addListener(listener);
        }
    }


    @Override
    public void render(float delta) {
        ScreenUtils.clear(BACKGROUND_COLOR);
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
    }

    @Override
    public void dispose() {
        font.dispose();
        stage.dispose();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
    }

    private ClickListener addAction(MyFunctionalInterface lambda) {
        return new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                lambda.perform();
            }
        };
    }
}

