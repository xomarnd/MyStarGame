package ru.geekbrains.mystargame.sprite;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import ru.geekbrains.mystargame.base.ScaledButton;
import ru.geekbrains.mystargame.math.Rect;

public class ButtonDodge  extends ScaledButton {
    private static final float INITIAL_HEIGHT = 0.12f;

    public ButtonDodge(TextureAtlas atlas) {
        super(atlas.findRegion("dougbutton"));

    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        setHeightProportion(INITIAL_HEIGHT);
        setLeft(worldBounds.getLeft() + 0.05f);
        setBottom(worldBounds.getBottom() + 0.05f);
    }

    @Override
    public void action() {
    }
}
