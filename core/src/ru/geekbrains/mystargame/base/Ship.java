package ru.geekbrains.mystargame.base;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import ru.geekbrains.mystargame.math.Rect;
import ru.geekbrains.mystargame.pool.BulletPool;
import ru.geekbrains.mystargame.sprite.Bullet;

public class Ship extends Sprite {

    protected Vector2 v0 = new Vector2();
    protected Vector2 v = new Vector2();

    protected BulletPool bulletPool;
    //принимаем границы игрового мира
    protected Rect worldBounds;
    //объявляем переменную региона
    protected TextureRegion bulletRegion;

    //Объявляем переменные атаки корабля игрока
    protected Sound shootSound;
    //инициируем константу времени между выстрелами
    protected float reloadInterval = 0.2f;
    //инициируем переменную таймера времени между выстрелами
    protected float reloadTimer = 0f;

    protected float bulletHeight;
    protected int damage;

    protected Vector2 bulletV = new Vector2();

    protected int hp;

    public Ship() {
        super();
    }

    public Ship(TextureRegion region, int rows, int cols, int frames) {
        super(region, rows, cols, frames);
    }

    public void damage(int damage) {
        //уменьшаем значение здоровья корабля на размер нанесенного ущерба
        hp -= damage;
        System.out.println("Ship hp" + hp);
        if (hp <= 0) {
            //вызываем метод уничтожения корабля
            destroy();
        }
        frame = 1;
    }

    @Override
    public void update(float delta) {
        pos.mulAdd(v, delta);
    }
    @Override
    public void destroy() {
        super.destroy();
    }


    protected void shoot() {
        Bullet bullet = bulletPool.obtain();
        bullet.set(this, bulletRegion, pos, bulletV, bulletHeight, worldBounds, damage);
        shootSound.play();
    }
}
