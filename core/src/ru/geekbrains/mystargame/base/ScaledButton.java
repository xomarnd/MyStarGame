package ru.geekbrains.mystargame.base;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.mystargame.StarGame;

public abstract class ScaledButton extends Sprite {

    private static final float PRESS_SCALE = 0.99f;
    protected StarGame game;
    protected BaseScreen screen;

    private boolean pressed = false;
    private int pointer;
    private boolean isShowing;


    public ScaledButton(TextureRegion region, int rows, int cols, int frames, BaseScreen screen) {
        super(region, rows, cols, frames);
        this.game = screen.getGame();
        this.screen = screen;
    }

    public boolean touchDown(Vector2 touch, int pointer, int button) {
        if (pressed || !isMe(touch)) {
            this.scale = 1f;
            this.frame = 0;
            this.pressed = false;
            return false;
        }else{
            this.frame = 1;
            this.pointer = pointer;
            this.scale = PRESS_SCALE;
            this.pressed = true;
            return false;
        }
    }
    @Override
    public void update(float delta) {
        super.update(delta);
        if (!pressed) {
            this.frame = 0;
        } else {
            this.frame = 1;
        }
    }

    public boolean touchUp(Vector2 touch, int pointer, int button) {

        if (this.pointer != pointer || !pressed) {
            pressed = false;
            return false;
        }
        if (isMe(touch)) {
            action();
        }
        pressed = false;
        scale = 0f;
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
