package com.posixprotogen.j2dgameengine.core.render;

public class GLTint {

    public static final GLTint NORMAL = new GLTint(1f, 1f, 1f, 1f);

    public final float red;
    public final float green;
    public final float blue;
    public final float alpha;

    private GLTint(float red, float green, float blue, float alpha) {
        this.red = red;
        this.green = green;
        this.blue = blue;
        this.alpha = alpha;
    }

    public static GLTint modulate(float red, float green, float blue, float alpha) {
        return new GLTint(red, green, blue, alpha);
    }
}
