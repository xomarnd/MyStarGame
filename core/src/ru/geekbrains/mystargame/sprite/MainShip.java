package ru.geekbrains.mystargame.sprite;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.mystargame.base.Ship;
import ru.geekbrains.mystargame.math.Rect;

/**
 * Класс спрайта корабля игрока(главного корабля).
 */
public class MainShip extends Ship {
    private boolean pressedLeft;
    private boolean pressedRight;
    private static final int INVALID_POINTER = -1;
    private int leftPointer = INVALID_POINTER;
    private int rightPointer = INVALID_POINTER;

    public MainShip(TextureAtlas atlas) {
        super(atlas.findRegion("main_ship"));
        bulletRegion = atlas.findRegion("bulletMainShip");
        v0.set(0.5f, 0);


    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        setHeightProportion(0.029f);
        setBottom(worldBounds.getBottom() + 0.25f);
    }

    @Override
    public void update(float delta) {
        super.update(delta);
        pos.mulAdd(v, delta);
        //если главный корабль вышел на половину за правый край игрового поля
        if (pos.x >= worldBounds.getRight()) {
            setRight(worldBounds.getRight() + getHalfWidth() - delta);
            stop();
        } else if (pos.x <= worldBounds.getLeft()) {
            setLeft(worldBounds.getLeft() - getHalfWidth() + delta);
            stop();
        }
    }

    public void keyDown(int keycode) {
        switch (keycode) {
            case Input.Keys.D:
            case Input.Keys.RIGHT:
                moveRight();
                pressedRight = true;
                break;
            case Input.Keys.A:
            case Input.Keys.LEFT:
                moveLeft();
                pressedLeft = true;
                break;
            case Input.Keys.UP:
                break;
        }
    }

    public void keyUp(int keycode) {
        switch (keycode) {
            case Input.Keys.D:
            case Input.Keys.RIGHT:
                pressedRight = false;
                if (pressedLeft) {
                    moveLeft();
                } else {
                    stop();
                }
                break;
            case Input.Keys.A:
            case Input.Keys.LEFT:
                pressedLeft = false;
                if (pressedRight) {
                    moveRight();
                } else {
                    stop();
                }
                break;
        }
    }


    private void moveRight() {
        v.set(v0);
    }

    private void moveLeft() {
        v.set(v0).rotate(180);
    }

    private void stop() {
        v.setZero();
    }

}
