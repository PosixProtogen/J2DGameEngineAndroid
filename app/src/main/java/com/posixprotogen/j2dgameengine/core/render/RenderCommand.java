package com.posixprotogen.j2dgameengine.core.render;


public class RenderCommand {
    private GLSprite glSprite;
    private float x;
    private float y;

    public void set(GLSprite glSprite, float x, float y) {
        this.glSprite = glSprite;
        this.x = x;
        this.y = y;
    }

    public GLSprite getGlSprite() {
        return glSprite;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }
}
