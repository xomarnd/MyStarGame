package ru.geekbrains.mystargame.sprite;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import ru.geekbrains.mystargame.base.ScaledButton;
import ru.geekbrains.mystargame.math.Rect;
import ru.geekbrains.mystargame.base.BaseScreen;

public class IndicatorStamina  extends ScaledButton {

    public IndicatorStamina(TextureAtlas atlas, BaseScreen screen) {
        super(atlas.findRegion("amo3"), screen);
    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        setHeightProportion(0.049f);
        setBottom(worldBounds.getBottom() + 0.19f);
    }

    @Override
    public void action() {
    }
}
