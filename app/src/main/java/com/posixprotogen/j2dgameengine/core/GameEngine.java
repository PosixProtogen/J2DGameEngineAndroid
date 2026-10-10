package com.posixprotogen.j2dgameengine.core;

import android.content.Context;

import com.posixprotogen.j2dgameengine.J2DGameEngineMain;
import com.posixprotogen.j2dgameengine.core.abstractions.AbstractEntity;
import com.posixprotogen.j2dgameengine.core.listeners.GestureListener;
import com.posixprotogen.j2dgameengine.core.listeners.RenderListener;
import com.posixprotogen.j2dgameengine.core.utils.GLVec2f;

import java.util.ArrayList;
import java.util.List;

import javax.microedition.khronos.opengles.GL10;

public class GameEngine {


    private int framesPerSecond;
    private Context context;
    private RendererSurfaceView rendererSurfaceView;
    private float realX;
    private float realY;

    private final GameWorld gameWorld = new GameWorld();

    public RendererSurfaceView setupEngine(Context context) {
        this.context = context;

        RendererEngine rendererEngine = new RendererEngine();
        rendererEngine.setRenderListener(new RenderListenerImp(this));

        this.rendererSurfaceView = new RendererSurfaceView(context, rendererEngine);

        return this.rendererSurfaceView;
    }

    public int getFramesPerSecond() {
        return framesPerSecond;
    }

    class RenderListenerImp implements RenderListener {

        private GameEngine gameEngine;

        public RenderListenerImp(GameEngine gameEngine) {
            this.gameEngine = gameEngine;
        }

        @Override
        public void onDisplayChanged(float w, float x, float h, float y) {
            gameWorld.setDisplay(new GLVec2f(w, h));
            realX = x;
            realY = y;
        }

        @Override
        public void onFramePerSecond(int fps) {
            framesPerSecond = fps;
        }

        @Override
        public void onFrameTime(float frameTime) {
            gameWorld.setLastFrameTime(frameTime);
        }

        @Override
        public void onBegin(GL10 gl) {
            J2DCanvas j2DCanvas = new J2DCanvas(gl, gameWorld.getDisplay(), context);
            gameWorld.setJ2DCanvas(j2DCanvas);

            SpriteLoader spriteLoader = new SpriteLoader(gl, context);
            gameWorld.setSpriteLoader(spriteLoader);

            new J2DGameEngineMain().setupGame(gameWorld);

            rendererSurfaceView.setTouchListener((event, x, y) -> {
                float posY = y/realY;
                float posX = (x/realX) * gameWorld.getDisplay().width;
                List<GestureListener> gestureListeners = gameWorld.getGestureListeners();

                if(event.getAction() == 0){
                    for (int i = 0; i < gestureListeners.size(); i++) {
                        gestureListeners.get(i).onDown(posX, posY);
                    }

                } else if(event.getAction() == 1){
                    for (int i = 0; i < gestureListeners.size(); i++) {
                        gestureListeners.get(i).onUp(posX, posY);
                    }
                } else {
                    for (int i = 0; i < gestureListeners.size(); i++) {
                        gestureListeners.get(i).onMoving(posX, posY);
                    }
                }
            });
        }

        @Override
        public void onSetup() {
            List<AbstractEntity> pending = gameWorld.getNewEntities();
            gameWorld.setNewEntities(new ArrayList<>());

            for (int i = 0; i < pending.size(); i++) {
                AbstractEntity newEntity = pending.get(i);
                newEntity.setGameContext(gameWorld);
                newEntity.setup();
                gameWorld.getEntities().add(newEntity);
            }
        }

        @Override
        public void onUpdate() {
            List<AbstractEntity> entities = gameWorld.getEntities();
            List<AbstractEntity> entitiesUpdated = new ArrayList<>();

            for (int i = 0; i < entities.size(); i++) {
                AbstractEntity entity = entities.get(i);
                entity.update();

                if(entity.isAlive()){
                    entitiesUpdated.add(entity);

                } else {
                    System.out.println("Entity deleted: " + entity);
                }
            }
            gameWorld.setEntities(entitiesUpdated);
        }

        @Override
        public void onDraw() {
            List<AbstractEntity> entities = gameWorld.getEntities();

            for (int i = 0; i < entities.size(); i++) {
                entities.get(i).draw();
            }
        }
    }
}
