package ru.geekbrains.mystargame.sprite;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import ru.geekbrains.mystargame.base.BaseScreen;
import ru.geekbrains.mystargame.base.ScaledButton;
import ru.geekbrains.mystargame.math.Rect;

public class ButtonNewGame extends ScaledButton {

    //передаем объект скрина(экрана)
    private BaseScreen screen;

    public ButtonNewGame(TextureAtlas atlas, BaseScreen screen) {
        //передаем в родительский класс текструру-регион картинки кнопки "NewGame"
        super(atlas.findRegion("newgame"), 2, 1, 2, screen);
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
        setShowing(false);
        screen.startNewGame();
    }

}
