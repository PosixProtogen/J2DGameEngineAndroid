package com.posixprotogen.j2dgameengine.core;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.view.MotionEvent;

import com.posixprotogen.j2dgameengine.core.listeners.TouchListener;

public class RenderView extends GLSurfaceView {

    private TouchListener touchListener;

    public RenderView(Context context) {
        super(context);
        RendererEngine renderer = new RendererEngine();
        new GameEngine(context, renderer, this);
        setRenderer(renderer);
        setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(touchListener != null){
            touchListener.onTouch(event, event.getX(), event.getY());
        }
        return true;
    }

    public void setTouchListener(TouchListener touchListener){
        this.touchListener = touchListener;
    }
}
