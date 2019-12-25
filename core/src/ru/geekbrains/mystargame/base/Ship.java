package ru.geekbrains.mystargame.base;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import ru.geekbrains.mystargame.math.Rect;


public abstract class Ship extends Sprite {

    protected Vector2 v0 = new Vector2();
    protected final Vector2 v = new Vector2();

    protected Rect worldBounds;
    protected TextureRegion bulletRegion;



    public Ship(TextureRegion region) {
        super(region);
    }

    @Override
    public void update(float delta) {

    }
}
