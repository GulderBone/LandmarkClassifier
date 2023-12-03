package com.gulderbone.landmarkclassifier.presentation

import android.graphics.Bitmap
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import com.gulderbone.landmarkclassifier.domain.Classification
import com.gulderbone.landmarkclassifier.domain.LandmarkClassifier

class LandmarkImageAnalyzer(
    private val landmarkClassifier: LandmarkClassifier,
    private val onResults: (List<Classification>) -> Unit,
) : ImageAnalysis.Analyzer {

    private var frameSkipCounter = 0

    override fun analyze(image: ImageProxy) {
        if (frameSkipCounter % 60 == 0) {
            val rotationDegrees: Int = image.imageInfo.rotationDegrees
            val bitmap = image
                .toBitmap()
                .centerCrop(321, 321)

            val results = landmarkClassifier.classify(bitmap, rotationDegrees)
            onResults(results)
            frameSkipCounter = 0
        }

        frameSkipCounter++

        image.close()
    }
}

fun Bitmap.centerCrop(desiredWidth: Int, desiredHeight: Int): Bitmap {
    val xStart = (width - desiredWidth) / 2
    val yStart = (height - desiredHeight) / 2

    if (xStart < 0 || yStart < 0f || desiredWidth > width || desiredHeight > height) {
        throw IllegalArgumentException("Invalid arguments for center cropping")
    }

    return Bitmap.createBitmap(this, xStart, yStart, desiredWidth, desiredHeight)
}
