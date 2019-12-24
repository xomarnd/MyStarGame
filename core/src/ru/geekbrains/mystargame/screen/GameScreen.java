package ru.geekbrains.mystargame.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
//import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
//import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.mystargame.base.BaseScreen;
import ru.geekbrains.mystargame.math.Rect;
import ru.geekbrains.mystargame.sprite.Background;
import ru.geekbrains.mystargame.sprite.Star;

public class GameScreen extends BaseScreen {

//    private Texture bg;
    private TextureAtlas atlas;
    private TextureAtlas atlasStar;
    private TextureAtlas atlasMenu;


    private Background background;
    private Star[] stars;

    @Override
    public void show() {
        super.show();
//        bg = new Texture("textures/bg.png");
//        background = new Background(new TextureRegion(bg));
        atlasStar = new TextureAtlas(Gdx.files.internal("textures/stars.tpack"));
        atlasMenu = new TextureAtlas(Gdx.files.internal("textures/atlasmenu.tpack"));

        stars = new Star[64];
        for (int i = 0; i < stars.length; i++) {
            stars[i] = new Star(atlasStar);
        }
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        update(delta);
        draw();
    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
//        background.resize(worldBounds);
        for (Star star : stars) {
            star.resize(worldBounds);
        }
    }

    @Override
    public void dispose() {
        atlas.dispose();
//        bg.dispose();
        super.dispose();
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer, int button) {
        return super.touchDown(touch, pointer, button);
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer, int button) {
        return super.touchUp(touch, pointer, button);
    }

    private void update(float delta) {
        for (Star star : stars) {
            star.update(delta);
        }
    }

    private void draw() {
        Gdx.gl.glClearColor(0.15f, 0.15f, 0.15f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
//        background.draw(batch);
        for (Star star : stars) {
            star.draw(batch);
        }
        batch.end();
    }
}
