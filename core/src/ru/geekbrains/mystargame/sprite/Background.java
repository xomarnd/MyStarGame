package ru.geekbrains.mystargame.sprite;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

import ru.geekbrains.mystargame.base.Sprite;
import ru.geekbrains.mystargame.math.Rect;

public class Background extends Sprite {

    public Background(TextureRegion region) {
        super(region);
    }

    @Override
    public void resize(Rect worldBounds) {
        setHeightProportion(worldBounds.getHeight());
        pos.set(worldBounds.pos);
    }
}
