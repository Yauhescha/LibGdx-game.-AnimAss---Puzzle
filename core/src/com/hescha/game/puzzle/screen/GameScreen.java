package com.hescha.game.puzzle.screen;

import static com.hescha.game.puzzle.AnimAssPuzzle.WORLD_HEIGHT;
import static com.hescha.game.puzzle.AnimAssPuzzle.WORLD_WIDTH;
import static com.hescha.game.puzzle.AnimAssPuzzle.backgroundColor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.hescha.game.puzzle.AnimAssPuzzle;
import com.hescha.game.puzzle.model.Puzzle;
import com.hescha.game.puzzle.model.Tile;
import com.hescha.game.puzzle.service.PuzzleService;
import com.hescha.game.puzzle.util.FontUtil;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class GameScreen extends ScreenAdapter {
    final LevelType levelType;
    final Texture levelTexture;

    private Viewport viewport;
    private OrthographicCamera camera;

    private Stage stageBoard;
    private Stage stageInfo;
    private GlyphLayout glyphLayout;
    private BitmapFont bitmapFont;
    private SpriteBatch batch;
    TextureRegion[][] textureRegions;
    public static Puzzle puzzle;

    @Override
    public void show() {
        float worldWidth = 720;
        camera = new OrthographicCamera(worldWidth, WORLD_HEIGHT);
        camera.position.set(worldWidth / 2, WORLD_HEIGHT / 2, 0);
        camera.update();
        viewport = new FitViewport(worldWidth, WORLD_HEIGHT, camera);
        viewport.apply(true);
        batch = new SpriteBatch();

        glyphLayout = new GlyphLayout();
        bitmapFont = FontUtil.generateFont(Color.BLACK);

        textureRegions = TextureRegion.split(levelTexture, levelType.imageWidth, levelType.imageHeight);

        puzzle = PuzzleService.newPuzzle(levelType, textureRegions);
        stageInfo = new Stage(viewport, batch);
        stageBoard = new Stage(viewport, batch);

        InputMultiplexer multiplexer = new InputMultiplexer();
        multiplexer.addProcessor(stageInfo);
        multiplexer.addProcessor(stageBoard);
        Gdx.input.setInputProcessor(multiplexer);

        Table table = new Table();
        Table innerTable = new Table();
        table.setFillParent(true);

        Texture mainImage = new Texture(Gdx.files.internal("ui/EmptyScreen.png"));
        Image mainimage = new Image(mainImage);
        table.add(mainimage).top().row();
        stageInfo.addActor(table);

        Tile[][] tiles = puzzle.getTiles();
        for (Tile[] tile : tiles) {
            for (Tile tile1 : tile) {
                innerTable.addActor(tile1);
            }
        }
        innerTable.setSize(WORLD_WIDTH, levelType.y * levelType.imageHeight);
        stageInfo.addActor(innerTable);


        BitmapFont font = FontUtil.generateFont(Color.BLACK);

        Texture buttonTexture = new Texture(Gdx.files.internal("ui/button.png"));

        TextureRegion btnPlay = new TextureRegion(buttonTexture);
        TextureRegionDrawable buttonDrawable1 = new TextureRegionDrawable(btnPlay);
        ImageTextButton imageTextButton1 = new ImageTextButton("Back", new ImageTextButton.ImageTextButtonStyle(buttonDrawable1, null, null, font));
        table.add(imageTextButton1).center().padTop(10).padBottom(10).row();
        imageTextButton1.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                AnimAssPuzzle.launcher.setScreen(new SelectCategoryScreen());
            }
        });


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

        stageBoard.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stageBoard.draw();

        stageInfo.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stageInfo.draw();
    }

    @Override
    public void dispose() {
        bitmapFont.dispose();
        stageBoard.dispose();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
    }
}
