package com.example.uartapplication.view.composable.navigationBar.utils

fun lerp(start: Float, stop: Float, fraction: Float) =
    (start * (1 - fraction) + stop * fraction)