package ru.geekbrains.mystargame.screen;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;


import java.util.Random;

import ru.geekbrains.mystargame.base.BaseScreen;

public class MenuScreen extends BaseScreen{

    private Texture background;
    private Vector2 pos;
    private Vector2 speedV;
    private Vector2 touch;
    private Vector2 buffer;
    private Texture img;
    private int WIDTH;
    private int HEIGHT;
    private int logoColor = 0;
    private static final float V_LEN = 1.5f;


    private int[][] arrayColorRegion = {
            {0, 0, 500, 220},
            {0, 220, 500, 220},
            {0, 440, 500, 220},
            {500, 0, 500, 220},
            {500, 220, 500, 220},
            {500, 440, 500, 220}
    };

    @Override
    public void show() {
        setColor();
        super.show();
        WIDTH = Gdx.graphics.getWidth();
        HEIGHT = Gdx.graphics.getHeight();
        background = new Texture("textures/bgtexture.jpg");
        img = new Texture("DVDlogo/DVD_VIDEO_logo.png");
        pos = new Vector2();
        speedV = new Vector2(0.9f, 0.9f);
        touch = new Vector2();
        buffer = new Vector2();
    }

    private int setColor(){
        Random rnd = new Random(System.currentTimeMillis());
        int number = 0;
        while(logoColor == number){
            number = 0 + rnd.nextInt(5 - 0 + 1);
        }
        return number;
    }
    private void keyDownMove() {
        if (Gdx.input.isKeyPressed(Input.Keys.DPAD_RIGHT)) {
            speedV.set(+speedV.x, speedV.y);
        } else if (Gdx.input.isKeyPressed(Input.Keys.DPAD_LEFT)) {
            speedV.set(-speedV.x, speedV.y);
        } else if (Gdx.input.isKeyPressed(Input.Keys.DPAD_UP)) {
            speedV.set(speedV.x, +speedV.y);
        } else if (Gdx.input.isKeyPressed(Input.Keys.DPAD_DOWN)) {
            speedV.set(speedV.x, -speedV.y);
        }else if (Gdx.input.isKeyPressed(Input.Keys.PLUS)) {
            if (speedV.x > 0 && speedV.y > 0){
                speedV.add(0.1f, 0.1f);
            }else if(speedV.x < 0 && speedV.y < 0){
                speedV.add(-0.1f, 0.1f);
            }else if(speedV.x > 0 && speedV.y < 0){
                speedV.add(0.1f, -0.1f);
            }else if(speedV.x < 0 && speedV.y > 0){
                speedV.add(-0.1f, 0.1f);
            }
        }else if (Gdx.input.isKeyPressed(Input.Keys.MINUS)) {
            if (speedV.x > 0 && speedV.y > 0){
                speedV.add(-0.1f, -0.1f);
            }else if(speedV.x < 0 && speedV.y < 0){
                speedV.add(0.1f, 0.1f);
            }else if(speedV.x > 0 && speedV.y < 0){
                speedV.add(-0.1f, 0.1f);
            }else if(speedV.x < 0 && speedV.y > 0){
                speedV.add(0.1f, -0.1f);
            }
        }
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        keyDownMove();
        Gdx.gl.glClearColor(0.2f, 	0.6f, 0.5f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        batch.draw(background, 0, 0);
        batch.draw(img, pos.x, pos.y, arrayColorRegion[logoColor][0], arrayColorRegion[logoColor][1], arrayColorRegion[logoColor][2], arrayColorRegion[logoColor][3]);
        batch.end();
        pos.add(speedV);
        buffer.set(touch);

        if ((buffer.sub(pos)).len() > V_LEN){
            pos.add(speedV);
        }else{
            logoColor = setColor();
            try {
                Thread.sleep(1500);
                speedV.set(0.9f, 0.9f);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            pos.set(touch);
            pos.add(speedV);
        }

        if (Math.round(pos.y) >= WIDTH - 220){
            speedV.set(speedV.x, -speedV.y);
            logoColor = setColor();
        }else if (Math.round(pos.x) >= HEIGHT - 500){
            speedV.set(-speedV.x, speedV.y);
            logoColor = setColor();
        }else if (Math.round(pos.y) <= 0){
            speedV.set(speedV.x, -speedV.y);
            logoColor = setColor();
        }else if (Math.round(pos.x) <=0){
            speedV.set(-speedV.x, speedV.y);
            logoColor = setColor();
        }

    }

    @Override
    public void dispose() {
        img.dispose();
        background.dispose();
        super.dispose();
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        touch.set(screenX-250, Gdx.graphics.getHeight() - screenY-110);
        speedV.set(touch.cpy().sub(pos)).setLength(V_LEN);
        pos.add(speedV);
        return super.touchDown(screenX, screenY, pointer, button);
    }

}
