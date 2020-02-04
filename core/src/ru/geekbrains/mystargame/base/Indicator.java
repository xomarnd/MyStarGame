package ru.geekbrains.mystargame.base;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

import ru.geekbrains.mystargame.sprite.MainShip;


public class Indicator extends Sprite {
    private static int countStamina;
    protected BaseScreen screen;

    public Indicator(TextureRegion region, int rows, int cols, int frames, BaseScreen screen) {
        super(region, rows, cols, frames);
    }

    @Override
    public void update(float delta) {
        if (countStamina == 3){
           MainShip.pointStaminaFull = true;
           frame = 0;
       }else  if (countStamina == 2){
           frame = 1;
       }else  if (countStamina == 1){
           frame = 2;
       }else  if (countStamina == 0){
           frame = 3;
       }
    }
    public static void setAddPointCount(int point) {
        if (countStamina < 3){
            countStamina += point;
        }
    }
    public static void setPointCount() {
        countStamina = 0;
    }

}
