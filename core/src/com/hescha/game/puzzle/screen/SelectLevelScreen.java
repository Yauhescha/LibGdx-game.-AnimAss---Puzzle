package com.hescha.game.puzzle.screen;

import static com.hescha.game.puzzle.AnimAssPuzzle.WORLD_HEIGHT;
import static com.hescha.game.puzzle.AnimAssPuzzle.WORLD_WIDTH;
import static com.hescha.game.puzzle.AnimAssPuzzle.backgroundColor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
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

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class SelectLevelScreen extends ScreenAdapter {
    final LevelType levelType;
    Stage stage;
    BitmapFont font;
    Table table;
    Table innerTable;


    Texture headerTexture;
    Texture buttonTexture;
    Texture backgroundImage;
    Viewport viewport;
    SpriteBatch batch;
    OrthographicCamera camera;

    @Override
    public void show() {
        camera = new OrthographicCamera(WORLD_WIDTH, WORLD_HEIGHT);
        camera.position.set(WORLD_WIDTH / 2, WORLD_HEIGHT / 2, 0);
        camera.update();
        viewport = new FitViewport(WORLD_WIDTH, WORLD_HEIGHT, camera);
        viewport.apply(true);
        batch = new SpriteBatch();
        buttonTexture = new Texture(Gdx.files.internal("ui/button.png"));
        headerTexture = new Texture(Gdx.files.internal("ui/header.png"));
//        backgroundImage = new Texture(Gdx.files.internal("ui/3 SelectLevel.png"));

        table = new Table();
        table.setFillParent(true);
        font = FontUtil.generateFont(Color.BLACK);
        innerTable = new Table();
        innerTable.setFillParent(true);


        createButton(headerTexture, levelType.name().replace("_", " "), 50, null);
        createButton(buttonTexture, "BACK", 100, addAction(() -> AnimAssPuzzle.launcher.setScreen(new SelectCategoryScreen())));

        for (int i = 0; i < 1; i++) {
            Texture levelTexture = null;
            if (LevelType.LEVEL_3X3 == levelType) {
                levelTexture = new Texture(Gdx.files.internal("levels/3x3/1.jpg"));
            }

            if (LevelType.LEVEL_4X4 == levelType) {
                levelTexture = new Texture(Gdx.files.internal("levels/4x4/2.jpg"));
            }

            if (LevelType.LEVEL_5X5 == levelType) {
                levelTexture = new Texture(Gdx.files.internal("levels/5x5/3.jpg"));
            }

            if (LevelType.LEVEL_6X6 == levelType) {
                levelTexture = new Texture(Gdx.files.internal("levels/6x6/4.jpg"));
            }

            if (LevelType.LEVEL_4X6 == levelType) {
                levelTexture = new Texture(Gdx.files.internal("levels/4x6/5.jpg"));
            }

            if (LevelType.LEVEL_6X9 == levelType) {
                levelTexture = new Texture(Gdx.files.internal("levels/6x9/6.jpg"));
            }

            Texture finalLevelTexture = levelTexture;
            createButton(buttonTexture, "Level " + i, 10, addAction(() -> AnimAssPuzzle.launcher.setScreen(new GameScreen(levelType, finalLevelTexture))));
        }


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
        ScreenUtils.clear(backgroundColor);
        batch.setProjectionMatrix(camera.projection);
        batch.setTransformMatrix(camera.view);
        batch.begin();
//        batch.draw(backgroundImage, 0, 0);
        batch.end();

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
