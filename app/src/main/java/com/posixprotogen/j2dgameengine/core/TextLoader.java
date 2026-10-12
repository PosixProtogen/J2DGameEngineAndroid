package com.posixprotogen.j2dgameengine.core;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;

import com.posixprotogen.j2dgameengine.core.utils.GLSprite;

import java.util.HashMap;
import java.util.Map;

public class TextLoader {

    private SpriteLoader spriteLoader;
    private Context context;
    private final Map<String, Map<Character, GLSprite>> mapFileToAtlas = new HashMap<>();

    public TextLoader(SpriteLoader spriteLoader, Context context) {
        this.spriteLoader = spriteLoader;
        this.context = context;
    }

    public void loadFont(String file) {
        Typeface typeFace = Typeface.createFromAsset(context.getAssets(), file);

        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setTypeface(typeFace);
        paint.setColor(Color.rgb(255, 255, 255));
        paint.setTextSize(192);

        Map<Character, GLSprite> atlas = new HashMap<>();
        Rect bounds = new Rect();

        for (char c = 32; c <= 255; c++) {
            if (c >= 127 && c <= 159) {
                c = 160;
            }
            String letter = String.valueOf(c);
            paint.getTextBounds(letter, 0, 1, bounds);

            int width = bounds.width();
            int height = bounds.height();
            if (width < 1) {
                width = Math.max(1, (int) Math.ceil(paint.measureText(letter)));
            }
            if (height < 1) {
                height = 1;
            }
            Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);

            Canvas canvas = new Canvas(bitmap);
            canvas.drawText(letter, -bounds.left, -bounds.top, paint);

            GLSprite sprite = spriteLoader.createSprite(bitmap);

            atlas.put(c, sprite);

            bitmap.recycle();
        }
        mapFileToAtlas.put(file, atlas);
    }

    public TextSprite create(String file) {
        Map<Character, GLSprite> atlas = mapFileToAtlas.get(file);
        if (atlas == null) {
            throw new IllegalStateException("Font not loaded: " + file);
        }
        Map<Character, GLSprite> atlasCopy = new HashMap<>();

        for (Character c : atlas.keySet()) {
            atlasCopy.put(c, new GLSprite(atlas.get(c)));
        }
        TextSprite textSprite = new TextSprite(file, atlasCopy);

        return textSprite;
    }
}
