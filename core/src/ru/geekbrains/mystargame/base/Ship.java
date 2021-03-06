package ru.geekbrains.mystargame.base;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import ru.geekbrains.mystargame.math.Rect;
import ru.geekbrains.mystargame.pool.BulletPool;
import ru.geekbrains.mystargame.sprite.Bullet;
import ru.geekbrains.mystargame.sprite.Explosion;
import ru.geekbrains.mystargame.pool.ExplosionPool;
import ru.geekbrains.mystargame.utils.EnemyGenerator;


public class Ship extends Sprite {

    protected Vector2 v0 = new Vector2();
    protected Vector2 v = new Vector2();

    protected BulletPool bulletPool;

    protected BaseScreen screen;

    //принимаем границы игрового мира
    protected Rect worldBounds;
    //объявляем переменную региона
    protected TextureRegion[] bulletRegion;
    protected ExplosionPool explosionPool;
    protected TextureRegion[] bulletBigRegion;
    //Объявляем переменные атаки корабля игрока
    protected Sound shootSound;
    protected Sound sound;
    //инициируем константу времени между выстрелами
    protected float reloadInterval = 0.2f;
    //инициируем переменную таймера времени между выстрелами
    protected float reloadTimer = 0f;
    protected int bulletFrame;
    //объявляем переменную для значения константы жизни корабля
    protected int constHp;

    protected float damageAnimateInterval = 0.1f;
    protected float damageAnimateTimer = damageAnimateInterval;


    protected float bulletHeight;
    protected float superBulletHeight;
    protected int damage;
    protected int superdamage;

    protected Vector2 bulletV = new Vector2();

    protected int hp;
    protected int CONSTHP;
    protected int CONSTDAMAGE;
    protected boolean destroyed;


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
            hp = 0;
        }
        frame = 0;
        damageAnimateTimer = 0f;
    }
    public void setHp(int hp) {
        this.hp = hp;
    }
    public void setDamage(int damage) {
        this.damage = damage;
    }

    public void medHp(int hp) {
        this.hp += hp;
    }

    public int getDamage() {
        return damage;
    }

    @Override
    public void update(float delta) {
        pos.mulAdd(v, delta);
        damageAnimateTimer += delta;
        if (damageAnimateTimer >= damageAnimateInterval) {
            frame = rndFrame(1, 3);
        }
    }
    @Override
    public void destroy() {
        super.destroy();
        boom();
    }
    public int rndFrame(int min, int max)
    {
        max -= min;
        return (int) (Math.random() * ++max) + min;
    }

    protected void shoot() {
        Bullet bullet = bulletPool.obtain();
        bullet.set(this, bulletRegion, pos, bulletV, bulletHeight, worldBounds, damage);
        shootSound.play();
    }

    protected void bigShoot(){
        Bullet bullet = bulletPool.obtain();
        bullet.set(this, bulletBigRegion, pos, bulletV, superBulletHeight, worldBounds, superdamage);
        shootSound.play();
    }

    protected void boom() {
        Explosion explosion = explosionPool.obtain();
        explosion.set(getHeight(), this.pos);
    }

    public int getConstHp() {
        return constHp;
    }
}
