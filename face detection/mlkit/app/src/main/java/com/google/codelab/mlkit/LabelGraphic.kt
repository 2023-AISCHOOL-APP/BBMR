// Copyright 2018 Google LLC
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//      http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.
package com.google.codelab.mlkit

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.text.TextPaint

/**
 * Graphic instance for rendering image labels.
 */
abstract class LabelGraphic internal constructor(overlay: GraphicOverlay, labels: List<String>) :
    GraphicOverlay.Graphic(overlay) {
    private val textPaint: Paint
    private val bgPaint: Paint
    private val overlay: GraphicOverlay
    private val labels: List<String>

    init {
        this.overlay = overlay
        this.labels = labels
        textPaint = Paint()
        textPaint.color = Color.WHITE
        textPaint.textSize = 60.0f
        bgPaint = Paint()
        bgPaint.color = Color.BLACK
        bgPaint.alpha = 50
    }

    @Synchronized
    @JvmName("labelGraphicDraw")
    fun draw(canvas: Canvas) {
        val x: Float = overlay.getWidth() / 4.0f
        var y: Float = overlay.getHeight() / 4.0f
        for (label in labels) {
            drawTextWithBackground(label, x, y, TextPaint(textPaint), bgPaint, canvas)
            y = y - 62.0f
        }
    }

    private fun drawTextWithBackground(
        text: String, x: Float, y: Float, paint: TextPaint,
        bgPaint: Paint, canvas: Canvas
    ) {
        val fontMetrics = paint.fontMetrics
        canvas.drawRect(
            Rect(
                x.toInt(),
                (y + fontMetrics.top).toInt(),
                (x + paint.measureText(text)).toInt(),
                (y + fontMetrics.bottom).toInt()
            ), bgPaint
        )
        canvas.drawText(text, x, y, textPaint)
    }
}
