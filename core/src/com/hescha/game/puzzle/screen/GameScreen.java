package com.hescha.game.puzzle.screen;

import static com.hescha.game.puzzle.AnimAssPuzzle.WORLD_HEIGHT;
import static com.hescha.game.puzzle.AnimAssPuzzle.WORLD_WIDTH;
import static com.hescha.game.puzzle.AnimAssPuzzle.BACKGROUND_COLOR;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.hescha.game.puzzle.AnimAssPuzzle;
import com.hescha.game.puzzle.model.Level;
import com.hescha.game.puzzle.model.Puzzle;
import com.hescha.game.puzzle.model.Tile;
import com.hescha.game.puzzle.service.PuzzleService;
import com.hescha.game.puzzle.util.FontUtil;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class GameScreen extends ScreenAdapter {
    public static Puzzle puzzle;
    private final Level level;
    private Viewport viewport;
    private Stage stageInfo;
    private BitmapFont bitmapFont;
    private ImageTextButton imageTextButton;
    private Integer movesMin;
    private String levelScoreSavingPath;

    @Override
    public void show() {
        float worldWidth = WORLD_WIDTH;
        OrthographicCamera camera = new OrthographicCamera(worldWidth, WORLD_HEIGHT);
        camera.position.set(worldWidth / 2, WORLD_HEIGHT / 2, 0);
        camera.update();
        viewport = new FitViewport(worldWidth, WORLD_HEIGHT, camera);
        viewport.apply(true);
        SpriteBatch batch = new SpriteBatch();

        bitmapFont = FontUtil.generateFont(Color.BLACK);

        LevelType levelType = level.getType();
        Texture levelTexture = new Texture(Gdx.files.internal(level.getTexturePath()));
        TextureRegion[][] textureRegions = TextureRegion.split(levelTexture, levelType.imageWidth, levelType.imageHeight);

        puzzle = PuzzleService.newPuzzle(levelType, textureRegions);
        stageInfo = new Stage(viewport, batch);

        InputMultiplexer multiplexer = new InputMultiplexer();
        multiplexer.addProcessor(stageInfo);
        Gdx.input.setInputProcessor(multiplexer);

        BitmapFont font = FontUtil.generateFont(Color.BLACK);

        Table table = new Table();
        stageInfo.addActor(table);
        Table innerTable = new Table();
        table.setFillParent(true);


        Texture mainImage = new Texture(Gdx.files.internal("ui/EmptyScreen.png"));
        TextureRegion mainBoard = new TextureRegion(mainImage);
        TextureRegionDrawable buttonDrawable = new TextureRegionDrawable(mainBoard);
        imageTextButton = new ImageTextButton("Back", new ImageTextButton.ImageTextButtonStyle(buttonDrawable, null, null, font));
        innerTable.add(imageTextButton).top().row();

        Tile[][] tiles = puzzle.getTiles();
        for (Tile[] tile : tiles) {
            for (Tile tile1 : tile) {
                innerTable.addActor(tile1);
            }
        }
        stageInfo.addActor(innerTable);


        Texture buttonTexture = new Texture(Gdx.files.internal("ui/button.png"));

        TextureRegion btnBack = new TextureRegion(buttonTexture);
        TextureRegionDrawable buttonDrawable1 = new TextureRegionDrawable(btnBack);
        ImageTextButton imageTextButton1 = new ImageTextButton("Back", new ImageTextButton.ImageTextButtonStyle(buttonDrawable1, null, null, font));
        innerTable.add(imageTextButton1).center().padTop(10).padBottom(levelType.y * levelType.imageHeight).row();
        imageTextButton1.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                AnimAssPuzzle.launcher.setScreen(SelectLevelScreen.screen);
            }
        });

        Label.LabelStyle labelStyle = new Label.LabelStyle(font, Color.BLACK);
        Label emptyLabel1 = new Label(" ", labelStyle);
        innerTable.add(emptyLabel1);

        ScrollPane scrollPane = new ScrollPane(innerTable);
        table.add(scrollPane);


        levelScoreSavingPath = levelType.name() + "-" + level.getCategory() + "-" + level.getName();
        Preferences prefs = Gdx.app.getPreferences("AnimAss_Puzzle");
        movesMin = prefs.getInteger(levelScoreSavingPath, 9999);
    }


    @Override
    public void render(float delta) {
        updatePuzzleStatus();

        String status = puzzle.isSolved() ? "Solved" : "Playing";

        String newText = "Level: \n" + level.getName() + "\n"
                + "Status: " + status + "\n"
                + "Moves: " + puzzle.getMovesNumber() + "\n"
                + "Moves min: " + movesMin;
        imageTextButton.getLabel().setText(newText);
        ScreenUtils.clear(BACKGROUND_COLOR);

        stageInfo.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stageInfo.draw();
    }

    private void updatePuzzleStatus() {
        if (!puzzle.isSolved()) {
            puzzle.setSolved(PuzzleService.isSolved(puzzle));
        } else {
            if (!puzzle.isSaved()) {
                saveBestResult();
            }
        }
    }

    private void saveBestResult() {
        puzzle.setSaved(true);
        int movesNumber = puzzle.getMovesNumber();

        if (movesMin > movesNumber) {
            Preferences prefs = Gdx.app.getPreferences("AnimAss_Puzzle");
            prefs.putInteger(levelScoreSavingPath, movesNumber);
            prefs.flush();
        }
    }

    @Override
    public void dispose() {
        bitmapFont.dispose();
        stageInfo.dispose();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
    }
}
