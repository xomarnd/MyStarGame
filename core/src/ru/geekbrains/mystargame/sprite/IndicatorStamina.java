package ru.geekbrains.mystargame.sprite;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import ru.geekbrains.mystargame.base.Indicator;
import ru.geekbrains.mystargame.math.Rect;
import ru.geekbrains.mystargame.base.BaseScreen;

public class IndicatorStamina  extends Indicator {

    public IndicatorStamina(TextureAtlas atlas, BaseScreen screen) {
        super(atlas.findRegion("ammo"), 4, 1, 4, screen);
    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        setHeightProportion(0.049f);
        setBottom(worldBounds.getBottom() + 0.059f);
    }
}
