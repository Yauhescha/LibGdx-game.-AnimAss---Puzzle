package com.hescha.game.puzzle;

import com.badlogic.gdx.Game;
import com.hescha.game.puzzle.screen.MainMenuScreen;

public class AnimAssPuzzle extends Game {
    public static AnimAssPuzzle launcher;

    @Override
    public void create() {
        launcher = this;
        setScreen(new MainMenuScreen());
    }
}
