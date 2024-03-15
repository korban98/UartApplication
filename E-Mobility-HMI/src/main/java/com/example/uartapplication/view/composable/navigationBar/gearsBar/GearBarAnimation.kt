package com.example.uartapplication.view.composable.navigationBar.gearsBar

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.FiniteAnimationSpec
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.LayoutDirection
import com.example.uartapplication.view.ui.theme.LightGrey


data class GearAnimation(
    override val animationSpec: FiniteAnimationSpec<Float>
) : GearBottomBarAnimation(animationSpec) {

    @Composable
    override fun AnimatingIcon(
        modifier: Modifier,
        isSelected: Boolean,
        isFromLeft: Boolean,
        icon: Int,
    ) {

        Box(
            modifier = modifier
        ) {
            val fraction = animateFloatAsState(
                targetValue = if (isSelected) 1f else 0f,
                animationSpec = animationSpec,
                label = "fractionAnimation"
            )

            val layoutDirection = LocalLayoutDirection.current
            val isLeftAnimation = remember(isFromLeft) {
                if (layoutDirection == LayoutDirection.Ltr) {
                    isFromLeft
                } else {
                    !isFromLeft
                }
            }

            val color = animateColorAsState(
                targetValue = if (isSelected) Color.White else Color.DarkGray,
                label = "colorAnimation"
            )

            Icon(
                modifier = Modifier
                    .align(Alignment.Center)
                    .graphicsLayer(
                        translationX = if (isSelected) offset(
                            10f,
                            fraction.value,
                            isLeftAnimation
                        ) else 0f
                    ),
                painter = painterResource(id = icon),
                contentDescription = null,
                tint = color.value
            )

        }
    }

    private fun offset(maxHorizontalOffset: Float, fraction: Float, isFromLeft: Boolean): Float {
        val maxOffset = if (isFromLeft) -maxHorizontalOffset else maxHorizontalOffset
        return if (fraction < 0.5) {
            2 * fraction * maxOffset
        } else {
            2 * (1 - fraction) * maxOffset
        }
    }
}