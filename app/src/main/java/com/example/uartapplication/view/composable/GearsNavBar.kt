package com.example.uartapplication.view.composable

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.uartapplication.view.composable.navigationBar.AnimatedNavigationBar
import com.example.uartapplication.view.composable.navigationBar.animation.balltrajectory.Straight
import com.example.uartapplication.view.composable.navigationBar.animation.indendshape.StraightIndent
import com.example.uartapplication.view.composable.navigationBar.animation.indendshape.shapeCornerRadius
import com.example.uartapplication.view.composable.navigationBar.data.barGears
import com.example.uartapplication.view.composable.navigationBar.gearsBar.BarGear

/**
 * Composable che rappresenta una barra animata per la visualizzazione delle marce inserite.
 *
 * @param currGear Marca corrente ottenuta dai dati telemetrici.
 */
@Composable
fun GearsNavBar(currGear: String?) {
    // Ricorda l'indice selezionato in base alla marcia corrente dei dati telemetrici
    var selectedItem by remember { mutableStateOf(0) }

    // Ricorda l'indice selezionato precedente per scopi di animazione
    val prevSelectedIndex = remember { mutableStateOf(selectedItem) }

    // Effetto di lancio basato su currentGear
    LaunchedEffect(currGear) {
        if (currGear != null) {
            prevSelectedIndex.value = selectedItem
            selectedItem = getIndexOfGear(currGear)
        }
    }

    // Barra di navigazione animata
    AnimatedNavigationBar(
        modifier = Modifier
            .padding(horizontal = 8.dp, vertical = 15.dp)
            .height(85.dp)
            .width(400.dp),
        selectedIndex = selectedItem,
        ballColor = Color(0x80000000),
        cornerRadius = shapeCornerRadius(25.dp),
        ballAnimation = Straight(spring(dampingRatio = 0.6f, stiffness = Spring.StiffnessVeryLow)),
        indentAnimation = StraightIndent(
            indentWidth = 56.dp,
            indentHeight = 15.dp,
            animationSpec = tween(1000)
        ),
    ) {
        // Itera attraverso le icone delle marce nella barra
        barGears.forEachIndexed { index, it ->
            BarGear(
                prevSelectedIndex = prevSelectedIndex.value,
                selectedIndex = selectedItem,
                index = index,
                icon = it.icon,
                animationType = it.animationType!!
            )
        }
    }
}

/**
 * Ottiene l'indice della marcia in base al suo valore.
 *
 * @param gear Valore della marcia ("P", "R", "N", "D").
 * @return Indice associato alla marcia.
 */
private fun getIndexOfGear(gear: String?): Int {
    return when (gear) {
        "P" -> 0
        "R" -> 1
        "N" -> 2
        "D" -> 3
        else -> 0
    }
}