package com.posixprotogen.j2dgameengine.core.utils;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

public class GLSprite {

    private int[] textureId;
    private float[] vertices;
    private float[] originalVertices;
    private FloatBuffer mFVertexBuffer;
    private FloatBuffer mTextureBuffer;
    private float w = 0.0f;
    private float h = 0.0f;
    private float originalW = 0.0f;
    private float originalH = 0.0f;
    private float angle = 0.0f;
    private int numSprite = 0;

    public GLSprite(int[] textureId, FloatBuffer mFVertexBuffer, FloatBuffer mTextureBuffer, float[] vertices, GLVec2f sizes){
        this.textureId = textureId;
        this.mFVertexBuffer = mFVertexBuffer;
        this.mTextureBuffer = mTextureBuffer;
        this.vertices = vertices;
        this.w = sizes.v0;
        this.h = sizes.v1;
        this.originalW = sizes.v0;
        this.originalH = sizes.v1;
        this.originalVertices = vertices;
    }

    public GLSprite(GLSprite glSprite){
        textureId = new int[glSprite.getSpriteLength()];

        for(int i = 0; i<glSprite.getSpriteLength(); i++){
            glSprite.setNumSprite(i);
            textureId[i] = glSprite.getTextureId();
        }

        vertices = new float[glSprite.getVertices().length];
        originalVertices = new float[glSprite.getVertices().length];

        for(int i=0; i<glSprite.getVertices().length; i++){
            vertices[i] = glSprite.getVertices()[i];
            originalVertices[i] = glSprite.getVertices()[i];
        }

        this.w = glSprite.getSizes().v0;
        this.h = glSprite.getSizes().v1;
        this.originalW = glSprite.getSizes().v0;
        this.originalH = glSprite.getSizes().v1;

        ByteBuffer vbb = ByteBuffer.allocateDirect(vertices.length * 4);
        vbb.order(ByteOrder.nativeOrder());

        mFVertexBuffer = vbb.asFloatBuffer();
        mFVertexBuffer.put(vertices);
        mFVertexBuffer.position(0);
        mTextureBuffer = glSprite.getTextureBuffer();
    }

    public void resize(float size){
        for(int i=0;i<vertices.length; i++){
            vertices[i] = originalVertices[i]*size;
        }
        this.w = originalW * size;
        this.h = originalH * size;

        ByteBuffer vbb = ByteBuffer.allocateDirect(vertices.length * 4);
        vbb.order(ByteOrder.nativeOrder());

        mFVertexBuffer = vbb.asFloatBuffer();
        mFVertexBuffer.put(vertices);
        mFVertexBuffer.position(0);
    }

    public boolean nextSprite(){
        if((numSprite+1) == textureId.length){
            numSprite = 0;
            return true;
        } else {
            numSprite++;
            return false;
        }
    }

    public int getTextureId() {
        return textureId[numSprite];
    }

    public FloatBuffer getVertexBuffer() {
        return mFVertexBuffer;
    }

    public FloatBuffer getTextureBuffer() {
        return mTextureBuffer;
    }

    public float getAngle() {
        return angle;
    }

    public void setAngle(float angle) {
        this.angle = angle;
    }

    public GLVec2f getSizes() {
        return new GLVec2f(w, h);
    }

    public float getWidht() {
        return this.w;
    }

    public float getHeight() {
        return this.h;
    }

    public void setSizes(GLVec2f sizes) {
        w = sizes.v0;
        h = sizes.v1;
    }

    public void setNumSprite(int numSprite) {
        this.numSprite = numSprite;
    }

    public int getSpriteLength(){
        return textureId.length;
    }

    public float[] getVertices(){
        return vertices;
    }
}
