package com.hescha.game.puzzle.screen;

import static com.hescha.game.puzzle.AnimAssPuzzle.WORLD_HEIGHT;
import static com.hescha.game.puzzle.AnimAssPuzzle.WORLD_WIDTH;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.hescha.game.puzzle.model.Puzzle;
import com.hescha.game.puzzle.model.Tile;
import com.hescha.game.puzzle.service.PuzzleService;
import com.hescha.game.puzzle.util.FontUtil;

public class GameScreen extends ScreenAdapter {
    public static final int IMAGE_WIDTH = 512;
    public static final int DIFFICULTY = 3;


    private Viewport viewport;
    private OrthographicCamera camera;

    private Stage stage;
    private BitmapFont font;
    private TextButton.TextButtonStyle skin;
    private Color backgroundColor;
    private SpriteBatch batch;
    TextureRegion[][] textureRegions;
    PuzzleService puzzleService;
    public static Puzzle puzzle;

    @Override
    public void show() {
        camera = new OrthographicCamera(WORLD_WIDTH, WORLD_HEIGHT);
        camera.position.set(WORLD_WIDTH / 2, WORLD_HEIGHT / 2, 0);
        camera.update();
        viewport = new FitViewport(WORLD_WIDTH, WORLD_HEIGHT, camera);
        viewport.apply(true);



        batch = new SpriteBatch();


        font = FontUtil.generateFont(Color.BLACK);

        skin = new TextButton.TextButtonStyle();
        skin.font = font;
        skin.fontColor = Color.BLACK;

        backgroundColor = Color.WHITE;

        loadImage();
        puzzleService = new PuzzleService();
        puzzle = puzzleService.newPuzzle(3, textureRegions);
        stage = new Stage(viewport, batch);
        Gdx.input.setInputProcessor(stage);

        Tile[][] tiles = puzzle.getTiles();
        for (Tile[] tile : tiles) {
            for (Tile tile1 : tile) {
                stage.addActor(tile1);
            }
        }
    }


    private void loadImage() {
        int size = IMAGE_WIDTH / DIFFICULTY;
        Texture fullTexture = new Texture(Gdx.files.internal("img/1.jpg"));

        int imageWidth = size;
        int imageHeight = size;

        textureRegions = TextureRegion.split(fullTexture, imageWidth, imageHeight);
    }


    @Override
    public void render(float delta) {
        batch.setProjectionMatrix(camera.projection);
        batch.setTransformMatrix(camera.view);
        ScreenUtils.clear(backgroundColor);
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
}
