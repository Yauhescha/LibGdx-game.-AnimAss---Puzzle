package com.hescha.game.puzzle.model;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.hescha.game.puzzle.screen.GameScreen;
import com.hescha.game.puzzle.screen.LevelType;
import com.hescha.game.puzzle.service.PuzzleService;

import lombok.Data;

@Data
public class Tile extends Actor {
    private TextureRegion textureRegion;
    private int number;

    private GlyphLayout glyphLayout;
    private BitmapFont bitmapFont;

    public Tile(LevelType level, TextureRegion textureRegion) {

        glyphLayout = new GlyphLayout();
        bitmapFont = new BitmapFont();
        bitmapFont.getData().setScale(7);


        this.textureRegion = textureRegion;
        setSize(level.imageWidth, level.imageHeight);

        addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("Touched: x - " + getX()+", y - " + getY());
                System.out.println("Touched number: " + number);
                PuzzleService.makeMove(GameScreen.puzzle,
                        (int) getY()/level.imageHeight,
                        (int) getX()/ level.imageWidth);
            }
        });
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        if (textureRegion != null) {
            batch.draw(textureRegion, getX(), getY());
        }
        glyphLayout.setText(bitmapFont, number+"");
        bitmapFont.draw(batch, glyphLayout, getX(), getY());
    }
}

