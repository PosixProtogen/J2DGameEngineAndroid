package com.posixprotogen.j2dgameengine.core;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.view.MotionEvent;

import com.posixprotogen.j2dgameengine.core.input.TouchListener;

public class RendererSurfaceView extends GLSurfaceView {

    private TouchListener touchListener;

    public RendererSurfaceView(Context context, RendererEngine renderer) {
        super(context);
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
