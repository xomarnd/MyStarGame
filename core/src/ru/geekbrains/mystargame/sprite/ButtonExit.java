package ru.geekbrains.mystargame.sprite;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import ru.geekbrains.mystargame.base.ScaledButton;
import ru.geekbrains.mystargame.math.Rect;
import ru.geekbrains.mystargame.base.BaseScreen;
public class ButtonExit extends ScaledButton {

    public ButtonExit(TextureAtlas atlas, BaseScreen screen) {
        super(atlas.findRegion("exitbutton"), 1, 2, 2, screen);
    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        setHeightProportion(0.09f);
        setLeft(worldBounds.getLeft() + 0.05f);
        setTop(worldBounds.getTop() - 0.05f);
    }

    @Override
    public void action() {
        Gdx.app.exit();
    }
}
