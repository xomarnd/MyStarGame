package ru.geekbrains.mystargame.base;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.mystargame.StarGame;

public abstract class PushButton extends Sprite {
    protected BaseScreen screen;

    private boolean pressed;

    public PushButton(TextureRegion region, int rows, int cols, int frames, boolean pressed) {
        super(region, rows, cols, frames, pressed);
    }

    public boolean touchDown(Vector2 touch, int pointer, int button) {
        this.pressed = !pressed;
        return false;
    }

    public boolean touchUp(Vector2 touch, int pointer, int button) {
        if (isMe(touch) && pressed) {
            action();
        }
        return false;
    }

    public abstract void action();{


    }

}
