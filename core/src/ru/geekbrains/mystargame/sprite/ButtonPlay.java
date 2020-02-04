package ru.geekbrains.mystargame.sprite;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import ru.geekbrains.mystargame.base.BaseScreen;
import ru.geekbrains.mystargame.base.ScaledButton;
import ru.geekbrains.mystargame.math.Rect;
import ru.geekbrains.mystargame.screen.GameScreen;

public class ButtonPlay extends ScaledButton {

    public ButtonPlay(TextureAtlas atlas, BaseScreen screen) {
        super(atlas.findRegion("startgame"), 1, 2, 2, screen);
        this.screen = screen;
    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        setHeightProportion(0.11f);
        setBottom(worldBounds.getBottom() + 0.05f);
    }

    @Override
    public void action() {
        game.setScreen(new GameScreen(game));
    }
}