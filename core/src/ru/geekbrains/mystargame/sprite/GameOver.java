package ru.geekbrains.mystargame.sprite;


import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.mystargame.base.Sprite;


public class GameOver extends Sprite {
    private static final float INITIAL_HEIGHT = 0.05f;
    private static final float INITIAL_ANGLE = 0f;
    private static final Vector2 pos0 = new Vector2(0, 0);

    public GameOver(TextureAtlas atlas) {
        super(atlas.findRegion("gameover"));
        pos.set(pos0);
        setHeightProportion(INITIAL_HEIGHT);
        setAngle(INITIAL_ANGLE);
    }

    public void startNewGame(){
        destroyed = false;
        setAngle(INITIAL_ANGLE);
        setHeightProportion(INITIAL_HEIGHT);
    }
}
