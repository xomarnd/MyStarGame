package ru.geekbrains.mystargame.pool;

import com.badlogic.gdx.audio.Sound;

import ru.geekbrains.mystargame.base.SpritesPool;
import ru.geekbrains.mystargame.math.Rect;
import ru.geekbrains.mystargame.sprite.Enemy;

public class EnemyPool extends SpritesPool<Enemy> {

    private BulletPool bulletPool;
    private ExplosionPool explosionPool;
    private Sound sound;
    private Rect worldBounds;

    public EnemyPool(BulletPool bulletPool, ExplosionPool explosionPool, Sound sound, Rect worldBounds) {
        this.bulletPool = bulletPool;
        this.explosionPool = explosionPool;
        this.sound = sound;
        this.worldBounds = worldBounds;
    }

    @Override
    public Enemy newObject() {
        return new Enemy(bulletPool, explosionPool, sound, worldBounds);    }
}
