package com.example.uartapplication.view.composable.navigationBar.data

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.runtime.Stable
import com.example.uartapplication.R
import com.example.uartapplication.view.composable.navigationBar.gearsBar.GearAnimation
import com.example.uartapplication.view.composable.navigationBar.gearsBar.GearBottomBarAnimation

@Stable
data class Item(
    @DrawableRes val icon: Int,
    var isSelected: Boolean,
    @StringRes val description: Int,
    val animationType: GearBottomBarAnimation? = null
)

// gear list
val barGears = listOf(
    Item(
        icon = R.drawable.p_gear,
        isSelected = true,
        description = R.string.Home,
        animationType = GearAnimation(
            animationSpec = spring(
                dampingRatio = 0.3f,
                stiffness = Spring.StiffnessVeryLow
            )
        )
    ),
    Item(
        icon = R.drawable.r_gear,
        isSelected = false,
        description = R.string.Bell,
        animationType = GearAnimation(
            animationSpec = spring(
                dampingRatio = 0.3f,
                stiffness = Spring.StiffnessVeryLow
            )
        )
    ),
    Item(
        icon = R.drawable.n_gear,
        isSelected = false,
        description = R.string.Plus,
        animationType = GearAnimation(
            animationSpec = spring(
                dampingRatio = 0.3f,
                stiffness = Spring.StiffnessVeryLow
            )
        )
    ),
    Item(
        icon = R.drawable.d_gear,
        isSelected = false,
        description = R.string.Calendar,
        animationType = GearAnimation(
            animationSpec = spring(
                dampingRatio = 0.3f,
                stiffness = Spring.StiffnessVeryLow
            )
        )
    )
)