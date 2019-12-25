package ru.geekbrains.mystargame.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.mystargame.base.BaseScreen;
import ru.geekbrains.mystargame.math.Rect;
import ru.geekbrains.mystargame.sprite.ButtonAttak;
import ru.geekbrains.mystargame.sprite.ButtonDodge;
import ru.geekbrains.mystargame.sprite.ButtonSound;
import ru.geekbrains.mystargame.sprite.IndicatorStamina;
import ru.geekbrains.mystargame.sprite.MainShip;
import ru.geekbrains.mystargame.sprite.Star;

public class GameScreen extends BaseScreen {
    private TextureAtlas atlas;
    private TextureAtlas atlasStar;
    private TextureAtlas atlasMenu;

    private MainShip mainShip;


    private ButtonDodge buttonDodge;
    private ButtonAttak buttonAttak;
    private ButtonSound buttonSound;
    private IndicatorStamina indicatorStamina;
    private Star[] stars;

    @Override
    public void show() {
        super.show();
        atlas = new TextureAtlas("textures/mainAtlas.tpack");
        atlasStar = new TextureAtlas(Gdx.files.internal("textures/stars.tpack"));
        atlasMenu = new TextureAtlas(Gdx.files.internal("textures/atlasmenu.tpack"));
        buttonDodge = new ButtonDodge(atlasMenu);
        buttonAttak = new ButtonAttak(atlasMenu);
        indicatorStamina = new IndicatorStamina(atlasMenu);
        buttonSound = new ButtonSound(atlasMenu);
        //инициируем обьект коробля
        mainShip = new MainShip(atlas);

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
        indicatorStamina.resize(worldBounds);
        buttonSound.resize(worldBounds);
        buttonDodge.resize(worldBounds);
        buttonAttak.resize(worldBounds);

        mainShip.resize(worldBounds);
        for (Star star : stars) {
            star.resize(worldBounds);
        }
    }
    /**
     * Метод освобождения памяти от объектов.
     */
    @Override
    public void dispose() {
        atlas.dispose();
        atlasMenu.dispose();
        atlasStar.dispose();
        super.dispose();
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer, int button) {
        if(!buttonDodge.isMe(touch)){
            mainShip.touchDown(touch, pointer, button);
        }else {
            buttonDodge.touchDown(touch, pointer, button);
        }

        if(!buttonAttak.isMe(touch)){
            mainShip.touchDown(touch, pointer, button);
        }else {
            buttonAttak.touchDown(touch, pointer, button);
        }
//        buttonSound.touchDown(touch, pointer, button);
        return false;
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer, int button) {
        if(!buttonDodge.isMe(touch)){
            mainShip.touchDown(touch, pointer, button);
        }else {
            buttonDodge.touchDown(touch, pointer, button);
        }
        if(!buttonAttak.isMe(touch)){
            mainShip.touchDown(touch, pointer, button);
        }else {
            buttonAttak.touchDown(touch, pointer, button);
        }
//        buttonSound.touchDown(touch, pointer, button);
        return false;
    }
    @Override
    public boolean keyDown(int keycode) {
        mainShip.keyDown(keycode);
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        mainShip.keyUp(keycode);
        return false;
    }
    private void update(float delta) {
//        mainShip.update(delta);
        buttonDodge.update(delta);

        for (Star star : stars) {
            star.update(delta);
        }
    }

    private void draw() {
        Gdx.gl.glClearColor(0.15f, 0.15f, 0.15f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        for (Star star : stars) {
            star.draw(batch);
        }
        indicatorStamina.draw(batch);
        buttonSound.draw(batch);
        buttonDodge.draw(batch);
        buttonAttak.draw(batch);
        mainShip.draw(batch);
        batch.end();
    }

}
