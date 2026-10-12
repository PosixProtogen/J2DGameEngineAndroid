package com.posixprotogen.j2dgameengine.core;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLUtils;

import com.posixprotogen.j2dgameengine.core.utils.GLRect;
import com.posixprotogen.j2dgameengine.core.utils.GLSprite;
import com.posixprotogen.j2dgameengine.core.utils.GLTint;
import com.posixprotogen.j2dgameengine.core.utils.GLVec2f;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.List;

import javax.microedition.khronos.opengles.GL10;
import javax.microedition.khronos.opengles.GL11;

public class J2DCanvas {

    private GL10 gl;
    private GLVec2f display;
    private int idTexHitbox;

    public J2DCanvas(GL10 gl, GLVec2f display, Context context){
        this.gl = gl;
        this.display = display;
        loadHitboxTex(context);
    }

    private void loadHitboxTex(Context context){
        Bitmap image = null;
        try {
            image = BitmapFactory.decodeStream(context.getAssets().open("rectbox.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        float[] textureCoords = {
                0.0f, 1.0f,
                1.0f, 1.0f,
                0.0f, 0.0f,
                1.0f, 0.0f
        };

        FloatBuffer mTextureBuffer;

        ByteBuffer tbb = ByteBuffer.allocateDirect(textureCoords.length * 4);
        tbb.order(ByteOrder.nativeOrder());
        mTextureBuffer = tbb.asFloatBuffer();
        mTextureBuffer.put(textureCoords);
        mTextureBuffer.position(0);

        int[] textureIdArray = new int[1];

        gl.glGenTextures(1 , textureIdArray, 0);
        gl.glBindTexture(GL10.GL_TEXTURE_2D, textureIdArray[0]);

        GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, image, 0);

        gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER, GL10.GL_LINEAR);

        idTexHitbox = textureIdArray[0];
    }

    public void drawSprite(GLSprite glSprite, float x, float y){
        drawSprite(glSprite, x, y, GLTint.NORMAL);
    }

    public void drawSprite(GLSprite glSprite, float x, float y, GLTint tint) {

        float calcX = display.width*3*(-1) + 3*x*2;
        float calcY = 3 - 3*y*2;

        gl.glMatrixMode(GL11.GL_MODELVIEW);
        gl.glLoadIdentity();
        gl.glTranslatef(calcX,calcY, -3.0f);
        gl.glRotatef(glSprite.getAngle(),0.0f,0.0f,1.0f);
        gl.glVertexPointer(2, GL10.GL_FLOAT, 0, glSprite.getVertexBuffer());
        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
        gl.glEnable(GL10.GL_TEXTURE_2D);
        gl.glEnable(GL10.GL_BLEND);
        gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
        gl.glTexEnvf(GL10.GL_TEXTURE_ENV, GL10.GL_TEXTURE_ENV_MODE, GL10.GL_MODULATE);
        gl.glTexCoordPointer(2, GL10.GL_FLOAT,0, glSprite.getTextureBuffer());
        gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
        gl.glDisableClientState(GL10.GL_COLOR_ARRAY);
        gl.glBindTexture(GL10.GL_TEXTURE_2D, glSprite.getTextureId());
        gl.glColor4f(tint.red, tint.green, tint.blue, tint.alpha);
        gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, 4);
        gl.glColor4f(1f, 1f, 1f, 1f);
        gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
        gl.glDisableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
    }

    public void drawTextSprite(TextSprite textSprite, float x, float y) {
        if (textSprite == null) {
            return;
        }

        List<GLSprite> glyphs = textSprite.getCharSprites();
        float cursor = x;
        float spacing = textSprite.getSpacing();
        GLTint tint = textSprite.getTint();

        for (int i = 0; i < glyphs.size(); i++) {
            GLSprite glyph = glyphs.get(i);
            float width = glyph.getWidht();
            drawSprite(glyph, cursor + width * 0.5f, y, tint);
            cursor += width + spacing;
        }
    }

    public void setBackgroundColor(float red, float green, float blue, float alpha){
        gl.glClearColor(red, green, blue, alpha);
    }

    public void drawRect(GLRect glRect){
        float calcX = display.width*3*(-1) + 3*glRect.getW()*2;
        float calcY = 3 - 3*glRect.getH()*2;

        gl.glMatrixMode(GL11.GL_MODELVIEW);
        gl.glLoadIdentity();
        gl.glTranslatef(calcX,calcY, -3.0f);
        gl.glVertexPointer(2, GL10.GL_FLOAT, 0, glRect.getVertexBuffer());
        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
        gl.glEnable(GL10.GL_TEXTURE_2D);
        gl.glEnable(GL10.GL_BLEND);
        gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
        gl.glBindTexture(GL10.GL_TEXTURE_2D, idTexHitbox);
        gl.glTexCoordPointer(2, GL10.GL_FLOAT,0, glRect.getTextureBuffer());
        gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
        gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, 4);
        gl.glDisableClientState(GL10.GL_COLOR_ARRAY);
        gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
        gl.glDisableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
    }
}
