package com.hescha.game.puzzle.model;

import static com.hescha.game.puzzle.service.PuzzleService.SIZE;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.hescha.game.puzzle.screen.GameScreen;
import com.hescha.game.puzzle.service.PuzzleService;

import lombok.Data;

@Data
public class Tile extends Actor {
    private TextureRegion textureRegion;
    private int number;

    public Tile(TextureRegion textureRegion) {
        this.textureRegion = textureRegion;
        int size=512/3;
        setSize(size, size);

        addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("Touched: x - " + getX()+", y - " + getY());
                System.out.println("Touched number: " + number);
                PuzzleService.makeMove(GameScreen.puzzle, (int) getY()/SIZE, (int) getX()/SIZE);
            }
        });
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        if (textureRegion != null)
            batch.draw(textureRegion, getX(), getY());
    }
}

