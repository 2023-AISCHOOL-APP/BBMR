package com.example.bbmr_project.mlkit

import android.graphics.*
import android.graphics.drawable.Drawable


class faceDrawable(faceDetectModel: faceDetectModel) : Drawable() {
    private val boundingRectPaint = Paint().apply {
        style = Paint.Style.STROKE
        color = Color.YELLOW
        strokeWidth = 5F
        alpha = 200
    }
    private val contentRectPaint = Paint().apply {
        style = Paint.Style.FILL
        color = Color.YELLOW
        alpha = 255
    }

    private val contentTextPaint = Paint().apply {
        color = Color.DKGRAY
        alpha = 255
        textSize = 36F
    }
    private val faceDetectModel = faceDetectModel
    private val contentPadding = 25


    override fun draw (canvas: Canvas) {
        canvas.drawRect(faceDetectModel.boundingRect, boundingRectPaint)
        canvas.drawRect(
            Rect(
                faceDetectModel.boundingRect.left,
                faceDetectModel.boundingRect.bottom +contentPadding / 2,
                faceDetectModel.boundingRect.left +contentPadding * 2,
                faceDetectModel.boundingRect.bottom + contentTextPaint.textSize.toInt() + contentPadding,
            ), contentRectPaint
        )
    }
    override fun setAlpha(alpha: Int) {
        boundingRectPaint.alpha = alpha
        contentRectPaint.alpha = alpha
    }

    override fun setColorFilter(colorFilter: ColorFilter?) {
        boundingRectPaint.colorFilter = colorFilter
        contentTextPaint.colorFilter = colorFilter
    }
    @Deprecated("Deprecated in Java")
    override fun getOpacity(): Int = PixelFormat.TRANSLUCENT


}