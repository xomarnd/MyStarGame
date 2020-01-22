package ru.geekbrains.mystargame.sprite;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import ru.geekbrains.mystargame.base.ScaledButton;
import ru.geekbrains.mystargame.math.Rect;
import ru.geekbrains.mystargame.base.BaseScreen;


public class ButtonAttak extends ScaledButton {
    private static final float INITIAL_HEIGHT = 0.12f;

    public ButtonAttak(TextureAtlas atlas, BaseScreen screen) {
        super(atlas.findRegion("attakbutton"), 1, 2, 2, screen);
    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        setHeightProportion(INITIAL_HEIGHT);
        setRight(worldBounds.getRight() - 0.05f);
        setBottom(worldBounds.getBottom() + 0.05f);
    }

    @Override
    public void action() {
        System.out.println("buttonAttack");
        MainShip.buttonAttack();
    }
}
