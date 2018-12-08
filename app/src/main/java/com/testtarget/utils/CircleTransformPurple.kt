package com.testtarget.utils

import android.graphics.*

import com.squareup.picasso.Transformation


class CircleTransformPurple : Transformation {
    override fun transform(source: Bitmap): Bitmap {
        val size = Math.min(source.width, source.height)

        val x = (source.width - size) / 2
        val y = (source.height - size) / 2

        val squaredBitmap = Bitmap.createBitmap(source, x, y, size, size)
        if (squaredBitmap != source) {
            source.recycle()
        }

        val bitmap = Bitmap.createBitmap(size, size, source.config)

        val canvas = Canvas(bitmap)
        val paint = Paint()
        val shader = BitmapShader(squaredBitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP)
        paint.shader = shader
        paint.isAntiAlias = true
        val radius = if (source.width > source.height) source.height.toFloat() / 2f else source.width.toFloat() / 2f
        val r = size / 2f
        canvas.drawCircle(r, r, r, paint)
        squaredBitmap.recycle()

        paint.shader = null
        paint.style = Paint.Style.STROKE
        paint.color = -0x988201
        paint.strokeWidth = 6f
        canvas.drawCircle(r, r, radius - 2, paint)
        return bitmap
    }

    override fun key(): String {
        return "circle"
    }
}