package ru.geekbrains.mystargame.sprite;


import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.mystargame.base.Sprite;


public class GameOver extends Sprite {
    private static final float INITIAL_HEIGHT = 0.05f;
    private static final float INITIAL_ANGLE = 0f;
    private static final Vector2 pos0 = new Vector2(0, 0);
    public static final int LAST_FRAME = 18;

    private float animateStartInterval = 10f;
    private float animateTimer;

    public GameOver(TextureAtlas atlas) {
        super(atlas.findRegion("message_game_over"));
        pos.set(pos0);
        setHeightProportion(INITIAL_HEIGHT);
        setAngle(INITIAL_ANGLE);
    }

    @Override
    public void update(float delta) {
        animateTimer += delta;
        if(animateTimer < animateStartInterval){
            return;
        }
        if (getHeight() > 0){
            setHeight(getHeight() - (animateTimer - animateStartInterval) / 10000f);
            setHeightProportion(getHeight());
            setAngle(angle + (animateTimer - animateStartInterval) * 3);
        } else {
            destroy();
        }
    }

    public void startNewGame(){
        destroyed = false;
        setAngle(INITIAL_ANGLE);
        setHeightProportion(INITIAL_HEIGHT);
    }
}
