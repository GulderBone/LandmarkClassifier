package com.gulderbone.landmarkclassifier.domain

import android.graphics.Bitmap
import com.gulderbone.landmarkclassifier.domain.Classification

interface LandmarkClassifier {

    fun classify(bitmap: Bitmap, rotation: Int): List<Classification>
}
