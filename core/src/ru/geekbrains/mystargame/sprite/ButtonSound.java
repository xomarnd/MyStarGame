package ru.geekbrains.mystargame.sprite;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import ru.geekbrains.mystargame.base.ScaledButton;
import ru.geekbrains.mystargame.math.Rect;
import ru.geekbrains.mystargame.base.BaseScreen;


public class ButtonSound extends ScaledButton {
    public ButtonSound(TextureAtlas atlas, BaseScreen screen) {
        super(atlas.findRegion("soundoff"), 2, 1, 2, screen);
//        super(new TextureRegion(new TextureAtlas(Gdx.files.internal("textures/atlasmenu.tpack")).findRegion("soundon")));
    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        setHeightProportion(0.08f);
        setRight(worldBounds.getRight() - 0.05f);
        setTop(worldBounds.getTop() - 0.05f);
    }

    @Override
    public void action() {

    }
}
