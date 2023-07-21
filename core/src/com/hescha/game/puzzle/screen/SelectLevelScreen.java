package com.hescha.game.puzzle.screen;

import static com.hescha.game.puzzle.AnimAssPuzzle.WORLD_HEIGHT;
import static com.hescha.game.puzzle.AnimAssPuzzle.WORLD_WIDTH;
import static com.hescha.game.puzzle.AnimAssPuzzle.BACKGROUND_COLOR;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
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
import com.hescha.game.puzzle.model.Level;
import com.hescha.game.puzzle.util.FontUtil;

import java.util.List;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class SelectLevelScreen extends ScreenAdapter {
    public static SelectLevelScreen screen;
    private LevelType levelType;
    private String category;
    private List<Level> levels;
    private Stage stage;
    private BitmapFont font;
    private Table innerTable;
    private Viewport viewport;
    private boolean isGalleryMode;

    public SelectLevelScreen(LevelType levelType, String category, List<Level> levels, boolean isGalleryMode) {
        this.levelType = levelType;
        this.category = category;
        this.levels = levels.stream().filter(level -> category.equals(level.getCategory())).collect(Collectors.toList());
        this.isGalleryMode = isGalleryMode;
    }

    @Override
    public void show() {
        screen = this;
        OrthographicCamera camera = new OrthographicCamera(WORLD_WIDTH, WORLD_HEIGHT);
        camera.position.set(WORLD_WIDTH / 2, WORLD_HEIGHT / 2, 0);
        camera.update();
        viewport = new FitViewport(WORLD_WIDTH, WORLD_HEIGHT, camera);
        viewport.apply(true);
        Texture buttonTexture = new Texture(Gdx.files.internal("ui/button.png"));
        Texture closedButtonTexture = new Texture(Gdx.files.internal("ui/ClosedButton.png"));
        Texture buttonGreenTexture = new Texture(Gdx.files.internal("ui/buttonGreen.png"));
        Texture headerTexture = new Texture(Gdx.files.internal("ui/header.png"));

        Table table = new Table();
        table.setFillParent(true);
        font = FontUtil.generateFont(Color.BLACK);
        innerTable = new Table();
        innerTable.setFillParent(true);


        createButton(headerTexture, levelType.name().replace("_", " ") + "\n" + category, 50, null);
        createButton(buttonTexture, "BACK", 100, addAction(() -> AnimAssPuzzle.launcher.setScreen(SelectCategoryScreen.screen)));


        Preferences prefs = Gdx.app.getPreferences("AnimAss_Puzzle");
        for (Level level : levels) {
            if (!isGalleryMode) {
                createButton(buttonTexture, level.getName(), 10, addAction(() -> AnimAssPuzzle.launcher.setScreen(new GameScreen(level))));
            } else {

                String levelScoreSavingPath = levelType.name() + "-" + level.getCategory() + "-" + level.getName();
                int moves = prefs.getInteger(levelScoreSavingPath, -1);
                if (moves != -1) {
                    createButton(buttonGreenTexture, level.getName(), 10, addAction(() -> AnimAssPuzzle.launcher.setScreen(new GalleryScreen(level))));
                } else {
                    createButton(closedButtonTexture, level.getName(), 10, null);
                }
            }
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
