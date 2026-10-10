package com.posixprotogen.j2dgameengine.core;

import com.posixprotogen.j2dgameengine.core.abstractions.AbstractEntity;
import com.posixprotogen.j2dgameengine.core.listeners.GestureListener;
import com.posixprotogen.j2dgameengine.core.utils.GLVec2f;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class GameWorld implements GameContext {
    private float lastFrameTime = 0f;

    private GLVec2f display = new GLVec2f();

    private List<AbstractEntity> newEntities = new ArrayList<>();

    private List<AbstractEntity> entities = new ArrayList<>();

    private List<AbstractEntity> deadEntities = new ArrayList<>();

    private final Map<Class<?>, List<AbstractEntity>> entitiesByType = new HashMap<>();

    private List<GestureListener> gestureListeners = new ArrayList<>();

    private SpriteLoader spriteLoader;

    private J2DCanvas j2DCanvas;

    @Override
    public float getLastFrameTime() {
        return lastFrameTime;
    }

    @Override
    public GLVec2f getDisplay() {
        return display;
    }

    @Override
    public void addEntity(AbstractEntity entity) {
        newEntities.add(entity);
    }

    @Override
    public void removeEntity(AbstractEntity entity) {
        deadEntities.add(entity);
    }

    public <T extends AbstractEntity> List<T> getEntities(Class<T> clazz) {
        if (!entitiesByType.containsKey(clazz)){
            return new ArrayList<>();
        }
        return (List<T>) entitiesByType.get(clazz);
    }

    @Override
    public void addTouchListener(GestureListener listener) {
        gestureListeners.add(listener);
    }

    @Override
    public SpriteLoader getSpriteLoader() {
        return spriteLoader;
    }

    @Override
    public J2DCanvas getJ2DCanvas() {
        return j2DCanvas;
    }

    public void setLastFrameTime(float lastFrameTime) {
        this.lastFrameTime = lastFrameTime;
    }

    public void setDisplay(GLVec2f display) {
        this.display = display;
    }

    public List<AbstractEntity> getNewEntities() {
        return newEntities;
    }

    public void setNewEntities(List<AbstractEntity> newEntities) {
        this.newEntities = newEntities;
    }

    public List<AbstractEntity> getEntities() {
        return entities;
    }

    public List<AbstractEntity> getDeadEntities() {
        return deadEntities;
    }

    public void setDeadEntities(List<AbstractEntity> deadEntities) {
        this.deadEntities = deadEntities;
    }

    public List<GestureListener> getGestureListeners() {
        return gestureListeners;
    }

    public void setSpriteLoader(SpriteLoader spriteLoader) {
        this.spriteLoader = spriteLoader;
    }

    public void setJ2DCanvas(J2DCanvas j2DCanvas) {
        this.j2DCanvas = j2DCanvas;
    }

    public void addToWorld(AbstractEntity entity) {
        entities.add(entity);
        entitiesByType.computeIfAbsent(entity.getClass(), type -> new ArrayList<>()).add(entity);
    }

    public void removeFromWorld(AbstractEntity entity) {
        entities.remove(entity);
        entitiesByType.get(entity.getClass()).remove(entity);

        System.out.println("Entity deleted: " + entity);
        System.out.println("Entities: " + entities.size());
        System.out.println(entity.getClass().getSimpleName() + ": " + entitiesByType.get(entity.getClass()).size());
    }
}
