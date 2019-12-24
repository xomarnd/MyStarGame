package ru.geekbrains.mystargame.sprite;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import ru.geekbrains.mystargame.base.ScaledButton;
import ru.geekbrains.mystargame.math.Rect;

public class ButtonExit extends ScaledButton {

    public ButtonExit(TextureAtlas atlas) {
        super(atlas.findRegion("exitbutton"));
    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        setHeightProportion(0.1f);
        setLeft(worldBounds.getLeft() + 0.05f);
        setTop(worldBounds.getTop() - 0.05f);
    }

    @Override
    public void action() {
        Gdx.app.exit();
    }
}
