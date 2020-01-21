package ru.geekbrains.mystargame.base;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.mystargame.StarGame;

public abstract class ScaledButton extends Sprite {

    private static final float PRESS_SCALE = 0.9f;
    protected StarGame game;
    protected BaseScreen screen;

    private boolean pressed = false;
    private int pointer;
    private boolean isShowing;


    public ScaledButton(TextureRegion region, BaseScreen screen) {
        super(region);
        this.game = screen.getGame();
        this.screen = screen;
    }

    public boolean touchDown(Vector2 touch, int pointer, int button) {
        if (pressed || !isMe(touch)) {
            scale = 1f;
            this.pressed = false;
            return false;

        }else {
            this.pointer = pointer;
            this.scale = PRESS_SCALE;
            this.pressed = true;
        }
        return false;
    }

    public boolean touchUp(Vector2 touch, int pointer, int button) {
        if (this.pointer != pointer || !pressed) {
            return false;
        }
        if (isMe(touch)) {
            action();
        }
        pressed = false;
        scale = 1f;
        return false;
    }
    public boolean isShowing() {
        return isShowing;
    }
    public void setShowing(boolean showing) {
        isShowing = showing;
    }
    public abstract void action();
}
