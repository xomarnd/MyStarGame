package ru.geekbrains.mystargame.screen;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Logo implements ApplicationListener {

    private Texture texture;
    private SpriteBatch batch;
    private TextureRegion[] regions = new TextureRegion[5];

    @Override
    public void create() {
        texture = new Texture(Gdx.files.internal("DVDlogo/DVD_VIDEO_logo.png"));
        batch = new SpriteBatch();
        regions[0] = new TextureRegion(texture, 0, 0, 500, 220);
        regions[1] = new TextureRegion(texture, 0, 220, 500, 220);
        regions[2] = new TextureRegion(texture, 0, 440, 500, 220);
        regions[3] = new TextureRegion(texture, 500, 0, 500, 220);
        regions[4] = new TextureRegion(texture, 500, 220, 500, 220);
        regions[5] = new TextureRegion(texture, 500, 240, 500, 220);
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void render() {
        batch.begin();
        batch.draw(texture, 0, 0, 64, 64);
        for (int i = 0; i < regions.length; i++) {
            batch.draw(regions[i], 75 * (i + 1), 100);
        }
        batch.end();
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {

    }
}