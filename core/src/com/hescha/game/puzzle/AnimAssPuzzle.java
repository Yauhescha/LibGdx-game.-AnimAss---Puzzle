package com.hescha.game.puzzle;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Color;
import com.hescha.game.puzzle.screen.MainMenuScreen;

public class AnimAssPuzzle extends Game {
    public static final float WORLD_WIDTH = 720;
    public static final float WORLD_HEIGHT = 1280;
    public static AnimAssPuzzle launcher;
    public static Color BACKGROUND_COLOR =  new Color(245f/255,232f/255,194f/255,1);

    @Override
    public void create() {
        launcher = this;
        setScreen(new MainMenuScreen());
    }
}
