package com.example.bbmr_project.mlkit

import android.graphics.Rect
import android.view.MotionEvent
import com.google.mlkit.vision.face.Face

class faceDetectModel (face:Face) {
    var boundingRect: Rect = face.boundingBox
    var faceCallback = {e:MotionEvent -> false}


    init {
        faceCallback = {e:MotionEvent ->
            if (e.action == MotionEvent.ACTION_BUTTON_PRESS && boundingRect.contains(e.getX().toInt(), e.getY().toInt())) {
            }
            true
        }
    }
}