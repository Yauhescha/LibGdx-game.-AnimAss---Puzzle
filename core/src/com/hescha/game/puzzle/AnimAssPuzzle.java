package com.hescha.game.puzzle;

import com.badlogic.gdx.Game;
import com.hescha.game.puzzle.screen.MainMenuScreen;

public class AnimAssPuzzle extends Game {

    @Override
    public void create() {
        setScreen(new MainMenuScreen());
    }
}
