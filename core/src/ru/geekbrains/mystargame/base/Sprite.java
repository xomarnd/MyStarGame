package ru.geekbrains.mystargame.base;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.mystargame.math.Rect;
import ru.geekbrains.mystargame.utils.Regions;

public abstract class Sprite extends Rect {
    protected boolean destroyed;
    protected float angle;
    protected float scale = 1f;
    protected boolean pressed;
    protected TextureRegion[] regions;
    protected int frame;

    public Sprite() {
    }

    public Sprite(TextureRegion region) {
        if (region == null) {
            throw new NullPointerException("Region is null");
        }
        regions = new TextureRegion[1];
        regions[0] = region;
    }

    public Sprite(TextureRegion region, int rows, int cols, int frames) {
        if (region == null) {
            throw new NullPointerException("region is null");
        }
        this.regions = Regions.split(region, rows, cols, frames);
    }
    public Sprite(TextureRegion region, int rows, int cols, int frames, boolean pressed) {
        if (region == null) {
            throw new NullPointerException("region is null");
        }
        this.regions = Regions.split(region, rows, cols, frames);
        if(pressed == true){
            this.frame = 1;
        }else {
            this.frame = 0;
        }
    }


    public void setHeightProportion(float height) {
        setHeight(height);
        float aspect = regions[frame].getRegionWidth() / (float) regions[frame].getRegionHeight();
        setWidth(height * aspect);
    }

    public void draw(SpriteBatch batch) {
        batch.draw(
                regions[frame],
                getLeft(), getBottom(),
                halfWidth, halfHeight,
                getWidth(), getHeight(),
                scale, scale,
                angle
        );
    }

    public void resize(Rect worldBounds) {
    }

    public void update(float delta) {
    }

    public void destroy() {
        this.destroyed = true;
    }

    public boolean isDestroyed() {
        return destroyed;
    }

    public void flushDestroy() {
        this.destroyed = false;
    }

    public boolean touchDown(Vector2 touch, int pointer, int button) {
        return false;
    }

    public boolean touchUp(Vector2 touch, int pointer, int button) {
        return false;
    }

    public float getAngle() {
        return angle;
    }


    public void setAngle(float angle) {
        this.angle = angle;
    }

    public boolean getPress() {
        return pressed;
    }

    public void setScale(float scale) {
        this.scale = scale;
    }

    public void setPress(boolean press) {
        this.pressed = press;
    }

    public void setFrame(int frame) {
        this.frame = frame;
    }

    public int getFrame() {
        return frame;
    }

}
