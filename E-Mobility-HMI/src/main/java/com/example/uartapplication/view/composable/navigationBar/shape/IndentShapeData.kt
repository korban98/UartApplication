package com.example.uartapplication.view.composable.navigationBar.shape

import com.example.uartapplication.view.composable.navigationBar.animation.indendshape.ShapeCornerRadius
import com.example.uartapplication.view.composable.navigationBar.animation.indendshape.shapeCornerRadius

data class IndentShapeData(
    val xIndent: Float = 0f,
    val height: Float = 0f,
    val width: Float = 0f,
    val cornerRadius: ShapeCornerRadius = shapeCornerRadius(0f),
    val ballOffset: Float = 0f,
)