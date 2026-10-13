package com.posixprotogen.j2dgameengine.core.assets;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLUtils;

import com.posixprotogen.j2dgameengine.core.render.GLSprite;
import com.posixprotogen.j2dgameengine.core.render.GLVec2f;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.HashMap;

import javax.microedition.khronos.opengles.GL10;

public class SpriteLoader {

    private GL10 gl;
    private Context context;
    private HashMap<String, GLSprite> texturePool = new HashMap<>();

    public SpriteLoader(GL10 gl, Context context) {
        this.gl = gl;
        this.context = context;
    }

    public void addTexture(String file){
        Bitmap[] tempImage = new Bitmap[1];

        try {
            tempImage[0] = BitmapFactory.decodeStream(context.getAssets().open(file));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        texturePool.put(file, createSprite(tempImage));

        for (int i=0; i<tempImage.length; i++) {
            tempImage[i].recycle();
        }
    }

    public void addTexture(String file, int cutW, int cutH) {
        Bitmap bitmap = null;
        try {
            bitmap = BitmapFactory.decodeStream(context.getAssets().open(file));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Bitmap[] bitmapArray = new Bitmap[cutH*cutW];
        int w = bitmap.getWidth()/cutW;
        int h = bitmap.getHeight()/cutH;

        for (int i = 0; i < cutH; i++) {
            for (int j=0;j<cutW;j++) {
                bitmapArray[j+(cutW*i)] = Bitmap.createBitmap(bitmap,j*w,i*h,w,h);
            }
        }
        texturePool.put(file, createSprite(bitmapArray));

        for (int i=0; i<bitmapArray.length; i++) {
            bitmapArray[i].recycle();
        }
    }

    public GLSprite getTexture(String file) {
        return new GLSprite(texturePool.get(file));
    }

    public GLSprite createSprite(Bitmap... bitmap) {
        float xa = -1.0f;
        float xb = 1.0f;
        float ya = 1.0f;
        float yb = -1.0f;

        if (bitmap[0].getWidth() > bitmap[0].getHeight()) {
            float calc = 0;

            calc = (float)bitmap[0].getHeight()/(float)bitmap[0].getWidth();
            yb = yb*calc;
            ya = calc;

        } else if (bitmap[0].getWidth() < bitmap[0].getHeight()) {
            float calc = 0.0f;

            calc = (float)bitmap[0].getWidth()/(float)bitmap[0].getHeight();
            xb = calc;
            xa = xa*calc;
        }

        GLVec2f resolution = new GLVec2f(xb, ya);

        xa = xa*3;
        xb = xb*3;
        ya = ya*3;
        yb = yb*3;

        float vertices[] = {
                xa, yb,
                xb, yb,
                xa, ya,
                xb, ya
        };

        float[] textureCoords = {
                0.0f, 1.0f,
                1.0f, 1.0f,
                0.0f, 0.0f,
                1.0f, 0.0f
        };

        FloatBuffer mFVertexBuffer;
        FloatBuffer mTextureBuffer;

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

        int[] textures = new int[bitmap.length];
        int[] textureLoad = new int[bitmap.length];

        for (int i=0; i<bitmap.length; i++) {
            gl.glGenTextures(1 , textures, i);
            gl.glBindTexture(GL10.GL_TEXTURE_2D, textures[i]);

            GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, bitmap[i], 0);

            applySampling();

            textureLoad[i] = textures[i];
        }
        return new GLSprite(textureLoad, mFVertexBuffer, mTextureBuffer, vertices, resolution);
    }

    public void release(GLSprite sprite) {
        if (sprite == null) {
            return;
        }

        int[] ids = new int[]{sprite.getTextureId()};
        gl.glDeleteTextures(1, ids, 0);
    }

    private void applySampling() {
        gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER, GL10.GL_LINEAR);
        gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MAG_FILTER, GL10.GL_LINEAR);
        gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_WRAP_S, GL10.GL_CLAMP_TO_EDGE);
        gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_WRAP_T, GL10.GL_CLAMP_TO_EDGE);
    }
}
