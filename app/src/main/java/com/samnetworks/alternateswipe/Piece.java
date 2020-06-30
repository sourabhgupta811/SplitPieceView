package com.samnetworks.alternateswipe;

import android.graphics.Bitmap;
import android.graphics.Matrix;

class Piece implements Comparable{
    Bitmap bitmap;
    Matrix matrix;
    private int x;
    private int y;
    private int width;
    private float speed;
    private int shadow;
    private int limitY;
    public Piece(int x, int y,int width, Bitmap bitmap, int shadow){
        this.bitmap = bitmap;
        this.x = x;
        this.y = y;
        this.shadow = shadow;
        this.width = width;

        if(bitmap != null) {
            matrix = new Matrix();
            matrix.postTranslate(x, y);

            speed = Utils.nextFloat(1,3f);
            int bitmapW = bitmap.getWidth();
            int bitmapH = bitmap.getHeight();
            limitY = bitmapW > bitmapH ? bitmapW : bitmapH;
            limitY += Utils.screenHeight;
        }
    }

    @Override
    public int compareTo(Object another) {
        return shadow - ((Piece)another).shadow;
    }

    public boolean advance(float fraction){
        float s = (float)Math.pow(fraction * 1.1226f, 2) * 8 * speed;
        float zy =  y - s * Utils.screenHeight / 10;
        matrix.reset();
        matrix.setScale(1-0.7f*fraction,1-0.7f*fraction);
        matrix.postTranslate(width-(width*(1-0.7f*fraction)) + x, zy);
        if(zy <= limitY)
            return true;
        else
            return false;
    }
}
