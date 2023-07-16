package com.hescha.game.puzzle;

import com.badlogic.gdx.Game;
import com.hescha.game.puzzle.screen.GameScreen;
import com.hescha.game.puzzle.screen.MainMenuScreen;

public class AnimAssPuzzle extends Game {
    public static final float WORLD_WIDTH = 720;
    public static final float WORLD_HEIGHT = 1280;
    public static AnimAssPuzzle launcher;

    @Override
    public void create() {
        launcher = this;
        setScreen(new MainMenuScreen());
    }
}
