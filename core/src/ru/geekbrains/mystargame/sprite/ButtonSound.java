package ru.geekbrains.mystargame.sprite;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import ru.geekbrains.mystargame.base.PushButton;
import ru.geekbrains.mystargame.math.Rect;


public class ButtonSound extends PushButton {

    public ButtonSound(TextureAtlas atlas) {
        super(atlas.findRegion("soundoff"), 2, 1, 2, false);
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
