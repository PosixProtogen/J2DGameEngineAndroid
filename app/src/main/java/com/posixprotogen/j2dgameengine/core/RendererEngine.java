package com.posixprotogen.j2dgameengine.core;

import android.opengl.EGL14;
import android.opengl.EGLDisplay;
import android.opengl.GLSurfaceView;

import com.posixprotogen.j2dgameengine.core.listeners.RenderListener;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;
import javax.microedition.khronos.opengles.GL11;

public class RendererEngine implements GLSurfaceView.Renderer {

    private RenderListener renderListener;
    private long sampleStart = System.nanoTime();
    public long currentTimeTick = System.nanoTime();

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        disableVsync();

        gl.glHint(GL11.GL_PERSPECTIVE_CORRECTION_HINT, GL11.GL_FASTEST);

        gl.glClearColor(.5f,.5f,.5f,1.0f);
        gl.glEnable(GL11.GL_CULL_FACE);
        gl.glShadeModel(GL11.GL_SMOOTH);
        //gl.glEnable(GL11.GL_DEPTH_TEST); This break Alpha Color on the textures loaded.

        gl.glMatrixMode(GL11.GL_PROJECTION);
        gl.glLoadIdentity();
    }

    private boolean loadTextures = true;

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        disableVsync();
        gl.glViewport(0, 0, width, height);

        float ratio = (float) width / height;
        gl.glFrustumf(-ratio, ratio, -1, 1, 1, 10);

        renderListener.onDisplayChanged(ratio, (float)width, 1.0f, (float)height);

        if(loadTextures){
            renderListener.onBegin(gl);
            loadTextures = false;
        }
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        gl.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);

        renderListener.onSetup();
        renderListener.onUpdate();
        renderListener.onDraw();

        long now = System.nanoTime();
        long deltaNanos = now - currentTimeTick;
        currentTimeTick = now;

        renderListener.onFrameTime(deltaNanos / 1_000_000_000f);

        if (now >= sampleStart + 100_000_000L && deltaNanos > 0) {
            int fps = (int) (1_000_000_000d / deltaNanos);
            renderListener.onFramePerSecond(fps);
            sampleStart = now;
        }
    }

    public void setRenderListener(RenderListener renderListener){
        this.renderListener = renderListener;
    }

    private void disableVsync() {
        EGLDisplay display = EGL14.eglGetCurrentDisplay();
        if (display == EGL14.EGL_NO_DISPLAY || !EGL14.eglSwapInterval(display, 0)) {
            System.out.println("eglSwapInterval(0) failed");
        }
    }

}
