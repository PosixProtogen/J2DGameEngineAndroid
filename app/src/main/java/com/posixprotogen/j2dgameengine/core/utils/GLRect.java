package com.posixprotogen.j2dgameengine.core.utils;

import android.graphics.Rect;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

public class GLRect {
    private FloatBuffer     mFVertexBuffer;
    private FloatBuffer     mTextureBuffer;
    private float w;
    private float h;
    private float x;
    private float y;

    public GLRect(float x, float y, float w, float h){

        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;

        float xa = -1.0f;
        float xb = 1.0f;
        float ya = 1.0f;
        float yb = -1.0f;

        xa = xa*x;
        xb = xb*x;
        ya = ya*y;
        yb = yb*y;

        xa = xa*3;
        xb = xb*3;
        ya = ya*3;
        yb = yb*3;

        float vertices[] = {
                xa, yb,  //Bottom Left
                xb, yb,  //Bottom Right
                xa, ya,  //Top Left
                xb, ya   //Top Right
        };

        float[] textureCoords = {
                0.0f, 1.0f,
                1.0f, 1.0f,
                0.0f, 0.0f,
                1.0f, 0.0f
        };

        ByteBuffer vbb = ByteBuffer.allocateDirect(vertices.length * 4);
        vbb.order(ByteOrder.nativeOrder());
        mFVertexBuffer = vbb.asFloatBuffer();
        mFVertexBuffer.put(vertices);
        mFVertexBuffer.position(0);

        ByteBuffer tbb = ByteBuffer.allocateDirect(textureCoords.length * 4);
        tbb.order(ByteOrder.nativeOrder());
        mTextureBuffer = tbb.asFloatBuffer();
        mTextureBuffer.put(textureCoords);
        mTextureBuffer.position(0);
    }

    public FloatBuffer getVertexBuffer() {
        return mFVertexBuffer;
    }

    public FloatBuffer getTextureBuffer() {
        return mTextureBuffer;
    }

    public float getW() {
        return w;
    }

    public float getH() {
        return h;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public void setPos(float w, float h){
        this.w = w;
        this.h = h;
    }

    public boolean intersect(GLRect glRect){
        Rect a = new Rect((int)((w-(x/2))*1000),(int)((h-(y/2))*1000),(int)((w+(x/2))*1000),(int)((h+(y/2))*1000));
        Rect b = new Rect((int)((glRect.getW()-(glRect.getX()/2))*1000),(int)((glRect.getH()-(glRect.getY()/2))*1000),(int)((glRect.getW()+(glRect.getX()/2))*1000),(int)((glRect.getH()+(glRect.getY()/2))*1000));

        if(a.intersect(b)){
            return true;
        } else {
            return false;
        }
    }
}
