package com.example.taskmaster.presentation.components.common.drawable

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun CircleWithText(text: String) {
    Box(modifier = Modifier.wrapContentSize()) { //Modifier.fillMaxSize()
        val circleColor = MaterialTheme.colorScheme.primary
        val textColor = MaterialTheme.colorScheme.onPrimary
        Canvas(modifier = Modifier.fillMaxSize()) {
            val centerX = size.width / 2
            val centerY = size.height / 2
            val radius = size.width / 2 - 4
            drawCircle(
                color = circleColor,
                center = Offset(centerX, centerY),
                radius = radius,
            )
            drawIntoCanvas { canvas ->
                val paint = Paint().asFrameworkPaint()
                paint.color = textColor.hashCode()
                paint.textSize = radius
                val textWidth = paint.measureText(text)
                val xPos = centerX - (textWidth / 2)
                val yPos = centerY + (paint.textSize/3)
                canvas.nativeCanvas.drawText(text, xPos, yPos, paint)
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
private fun prev() {
    CircleWithText(text = "BC")
}