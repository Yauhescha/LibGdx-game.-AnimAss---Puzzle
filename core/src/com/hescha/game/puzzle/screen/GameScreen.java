package com.hescha.game.puzzle.screen;

import static com.hescha.game.puzzle.AnimAssPuzzle.WORLD_HEIGHT;
import static com.hescha.game.puzzle.AnimAssPuzzle.WORLD_WIDTH;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
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
    private GlyphLayout glyphLayout;
    private BitmapFont bitmapFont;
    //    private TextButton.TextButtonStyle skin;
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


        glyphLayout = new GlyphLayout();
//        bitmapFont = new BitmapFont();

        bitmapFont = FontUtil.generateFont(Color.BLACK);
//        bitmapFont.getData().setScale(7);


        batch = new SpriteBatch();


//        skin = new TextButton.TextButtonStyle();
//        skin.font = font;
//        skin.fontColor = Color.BLACK;

        backgroundColor = new Color(245,232,194,1);

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
        Texture fullTexture = new Texture(Gdx.files.internal("img/12.jpg"));

        int imageWidth = size;
        int imageHeight = size;

        textureRegions = TextureRegion.split(fullTexture, imageWidth, imageHeight);
    }


    @Override
    public void render(float delta) {
        batch.setProjectionMatrix(camera.projection);
        batch.setTransformMatrix(camera.view);
        ScreenUtils.clear(backgroundColor);

        batch.begin();
        boolean solved = PuzzleService.isSolved(puzzle);
        glyphLayout.setText(bitmapFont, "Game ended? - " + solved);
        bitmapFont.draw(batch, glyphLayout, (viewport.getWorldWidth() - glyphLayout.width) / 2, viewport.getWorldHeight() / 2);
        batch.end();

        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();

    }

    @Override
    public void dispose() {
        bitmapFont.dispose();
        stage.dispose();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
    }
}
