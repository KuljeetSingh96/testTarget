package com.testtarget.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;

import com.squareup.picasso.Transformation;


public class CircleTransformPurple implements Transformation {
   @Override
   public Bitmap transform(Bitmap source) {
       int size = Math.min(source.getWidth(), source.getHeight());

       int x = (source.getWidth() - size) / 2;
       int y = (source.getHeight() - size) / 2;

       Bitmap squaredBitmap = Bitmap.createBitmap(source, x, y, size, size);
       if (squaredBitmap != source) {
           source.recycle();
       }

       Bitmap bitmap = Bitmap.createBitmap(size, size, source.getConfig());

       Canvas canvas = new Canvas(bitmap);
       Paint paint = new Paint();
       BitmapShader shader = new BitmapShader(squaredBitmap, BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.CLAMP);
       paint.setShader(shader);
       paint.setAntiAlias(true);
       float radius = source.getWidth() > source.getHeight() ? ((float) source.getHeight()) / 2f : ((float) source.getWidth()) / 2f;
       float r = size/2f;
       canvas.drawCircle(r, r, r, paint);
       squaredBitmap.recycle();

       paint.setShader(null);
       paint.setStyle(Paint.Style.STROKE);
       paint.setColor(0xFF677DFF);
       paint.setStrokeWidth(6);
       canvas.drawCircle(r, r,radius-2 ,paint);
       return bitmap;
   }

   @Override
   public String key() {
       return "circle";
   }
}