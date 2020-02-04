package ru.geekbrains.mystargame.base;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.util.ArrayList;
import java.util.List;


//Родительский класс пулов/объектов/спрайтов снарядов/кораблей/взрывов
public abstract class SpritesPool<T extends Sprite> {

    protected final List<T> activeObjects = new ArrayList<>();

    private final List<T> freeObjects = new ArrayList<>();

    public abstract T newObject();

    public T obtain() {
        T object;
        if (freeObjects.isEmpty()) {
            object = newObject();
        } else {
            object = freeObjects.remove(freeObjects.size() - 1);
        }
        activeObjects.add(object);
        return object;
    }

    public void updateActiveSprites(float delta) {
        for (T item : activeObjects) {
            if (!item.isDestroyed()) {
                item.update(delta);
            }
        }
    }

    public void drawActiveSprites(SpriteBatch batch) {
        for (T item : activeObjects) {
            if (!item.isDestroyed()) {
                item.draw(batch);
            }
        }
    }

    public void freeAllDestroyedActiveObjects() {
        for (int i = 0; i < activeObjects.size(); i++) {
            T item = activeObjects.get(i);
            if (item.isDestroyed()) {
                activeObjects.remove(item);
                freeObjects.add(item);
                i--;
                item.flushDestroy();
            }
        }
    }
    public void freeAllActiveObjects() {
        freeObjects.addAll(activeObjects);
        activeObjects.clear();
    }

    public List<T> getActiveObjects() {
        return activeObjects;
    }

    public void dispose() {
        activeObjects.clear();
        freeObjects.clear();
    }

    private void free(T object) {
        if (activeObjects.remove(object)) {
            freeObjects.add(object);
        }
        System.out.println(this.getClass().getName() + " active/free:" + activeObjects.size() + "/" + freeObjects.size());
    }
}


