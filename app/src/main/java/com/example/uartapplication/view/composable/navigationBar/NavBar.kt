package com.example.uartapplication.view.composable.navigationBar

import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.isSpecified
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.uartapplication.view.composable.navigationBar.animation.balltrajectory.BallAnimInfo
import com.example.uartapplication.view.composable.navigationBar.animation.balltrajectory.BallAnimation
import com.example.uartapplication.view.composable.navigationBar.animation.balltrajectory.Parabolic
import com.example.uartapplication.view.composable.navigationBar.animation.indendshape.Height
import com.example.uartapplication.view.composable.navigationBar.animation.indendshape.IndentAnimation
import com.example.uartapplication.view.composable.navigationBar.animation.indendshape.ShapeCornerRadius
import com.example.uartapplication.view.composable.navigationBar.animation.indendshape.shapeCornerRadius
import com.example.uartapplication.view.composable.navigationBar.layout.animatedNavBarMeasurePolicy
import com.example.uartapplication.view.composable.navigationBar.utils.ballTransform

/**
 * A composable function that creates an animated navigation bar with a moving ball and indent
 * to indicate the selected item.
 *
 *@param [modifier] Modifier to be applied to the navigation bar
 *@param [selectedIndex] The index of the currently selected item
 *@param [barColor] The color of the navigation bar
 *@param [ballColor] The color of the moving ball
 *@param [cornerRadius] The corner radius of the navigation bar
 *@param [ballAnimation] The animation to be applied to the moving ball
 *@param [indentAnimation] The animation to be applied to the navigation bar to indent selected item
 *@param [content] The composable content of the navigation bar
 */
@Composable
fun AnimatedNavigationBar(
    modifier: Modifier = Modifier,
    selectedIndex: Int,
    barColor: Color = Color.Black,
    ballColor: Color = Color.DarkGray,
    cornerRadius: ShapeCornerRadius = shapeCornerRadius(0f),
    ballAnimation: BallAnimation = Parabolic(tween(300)),
    indentAnimation: IndentAnimation = Height(tween(300)),
    content: @Composable () -> Unit,
) {

    var itemPositions by remember { mutableStateOf(listOf<Offset>()) }
    val measurePolicy = animatedNavBarMeasurePolicy {
        itemPositions = it.map { xCord ->
            Offset(xCord, 0f)
        }
    }

    val selectedItemOffset by remember(selectedIndex, itemPositions) {
        derivedStateOf {
            if (itemPositions.isNotEmpty()) itemPositions[selectedIndex] else Offset.Unspecified
        }
    }

    val indentShape = indentAnimation.animateIndentShapeAsState(
        shapeCornerRadius = cornerRadius,
        targetOffset = selectedItemOffset
    )

    val ballAnimInfoState = ballAnimation.animateAsState(
        targetOffset = selectedItemOffset,
    )

    Box(
        modifier = modifier
    ) {
        Layout(
            modifier = Modifier
                .graphicsLayer {
                    clip = true
                    shape = indentShape.value
                }
                .background(barColor)
                .padding(top = 20.dp, bottom = 20.dp),
            content = content,
            measurePolicy = measurePolicy
        )

        if (ballAnimInfoState.value.offset.isSpecified) {
            ColorBall(
                ballAnimInfo = ballAnimInfoState.value,
                ballColor = ballColor,
                sizeDp = ballSize
            )
        }
    }
}

val ballSize = 10.dp

@Composable
private fun ColorBall(
    modifier: Modifier = Modifier,
    ballColor: Color,
    ballAnimInfo: BallAnimInfo,
    sizeDp: Dp,
) {
    Box(
        modifier = modifier
            .ballTransform(ballAnimInfo)
            .size(sizeDp)
            .clip(shape = CircleShape)
            .background(ballColor)
    )
}