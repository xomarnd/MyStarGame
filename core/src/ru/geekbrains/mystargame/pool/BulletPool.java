package ru.geekbrains.mystargame.pool;

import ru.geekbrains.mystargame.base.SpritesPool;
import ru.geekbrains.mystargame.sprite.Bullet;

public class BulletPool extends SpritesPool<Bullet> {
    @Override
    public Bullet newObject() {
        return new Bullet();
    }
}
