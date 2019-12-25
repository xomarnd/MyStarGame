package ru.geekbrains.mystargame.sprite;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import ru.geekbrains.mystargame.base.ScaledButton;
import ru.geekbrains.mystargame.math.Rect;

public class ButtonDodge  extends ScaledButton {

    public ButtonDodge(TextureAtlas atlas) {
        super(atlas.findRegion("dougbutton"));
    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        setHeightProportion(0.12f);
        setLeft(worldBounds.getLeft() + 0.05f);
        setBottom(worldBounds.getBottom() + 0.05f);
    }

    @Override
    public void action() {
    }
}
