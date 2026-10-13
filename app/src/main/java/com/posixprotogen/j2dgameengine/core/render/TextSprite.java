package com.posixprotogen.j2dgameengine.core.render;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TextSprite {

    private String text;

    private Map<Character, GLSprite> atlas;

    private final List<GLSprite> glyphs = new ArrayList<>();

    private GLTint tint = GLTint.NORMAL;

    private float size = 1;

    private float spacing = 0;

    public TextSprite(String text, Map<Character, GLSprite> atlas) {
        this.text = text;
        this.atlas = atlas;
    }

    public void setText(String text) {
        this.text = text == null ? "" : text;
        glyphs.clear();

        for (int i = 0; i < this.text.length(); i++) {
            GLSprite glyph = atlas.get(this.text.charAt(i));
            if (glyph == null) {
                continue;
            }
            glyph.resize(size);
            glyphs.add(glyph);
        }
    }

    public void setText(Integer number) {
        setText(number.toString());
    }

    public List<GLSprite> getCharSprites() {
        return glyphs;
    }

    public GLTint getTint() {
        return tint;
    }

    public void setTint(GLTint tint) {
        this.tint = tint;
    }

    public float getSpacing() {
        return spacing;
    }

    public void setSpacing(float spacing) {
        this.spacing = spacing;
    }

    public void setSize(float size) {
        this.size = size;
        if (text != null) {
            for (GLSprite glyph : glyphs) {
                glyph.resize(size);
            }
        }
    }
}
