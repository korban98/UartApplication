package com.example.uartapplication.view.composable.navigationBar.gearsBar

import android.annotation.SuppressLint
import androidx.annotation.DrawableRes
import androidx.compose.animation.core.FiniteAnimationSpec
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Stable
abstract class GearBottomBarAnimation(
    open val animationSpec: FiniteAnimationSpec<Float> = tween(10000)
) {
    @Composable
    abstract fun AnimatingIcon(
        modifier: Modifier,
        isSelected: Boolean,
        isFromLeft: Boolean,
        icon: Int,
    )
}

@SuppressLint("RememberReturnType")
@Composable
fun BarGear(
    index: Int,
    selectedIndex: Int,
    prevSelectedIndex: Int,
    @DrawableRes icon: Int,
    animationType: GearBottomBarAnimation
) {

    Box{
        val isSelected = remember(selectedIndex, index) { selectedIndex == index }

        val isFromLeft = remember(prevSelectedIndex, index, selectedIndex) {
            (prevSelectedIndex < index) || (selectedIndex > index)
        }

        animationType.AnimatingIcon(
            modifier = Modifier.align(Alignment.Center),
            isSelected = isSelected,
            isFromLeft = isFromLeft,
            icon = icon,
        )
    }
}
