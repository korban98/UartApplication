package com.example.uartapplication.view.old

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.uartapplication.R

val BottomBar_items = listOf(
    BottomNavigation(
        title = "Telemetrie",
        icon = R.drawable.electric_car_icon
    ),
    BottomNavigation(
        title = "Errori",
        icon = R.drawable.alert_off
    )
)

@Composable
fun BottomNavigationBar(selectedTabIndex: Int, errorList: List<String>?, onTabSelected: (Int) -> Unit) {
    NavigationBar {
        Row(
            modifier = Modifier.background(MaterialTheme.colorScheme.inverseOnSurface)
        ) {
            NavigationBarItem(
                selected = 0 == selectedTabIndex,
                onClick = {
                    onTabSelected(0)
                },
                icon = {
                    val painter: Painter = painterResource(id = BottomBar_items[0].icon)
                    Icon(
                        painter = painter,
                        contentDescription = BottomBar_items[0].title,
                        tint = MaterialTheme.colorScheme.onBackground,
                        modifier = Modifier.size(30.dp)
                    )
                },
                label = {
                    Text(
                        text = BottomBar_items[0].title,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                }
            )
            NavigationBarItem(
                selected = 1 == selectedTabIndex,
                onClick = {
                    onTabSelected(1)
                },
                icon = {
                    val painter_alert_off: Painter = painterResource(id = BottomBar_items[1].icon)
                    val painter_alert_on: Painter = painterResource(id = R.drawable.alert_on)
                    Icon(
                        painter = if(errorList?.isNotEmpty()!!) painter_alert_on else painter_alert_off,
                        contentDescription = BottomBar_items[1].title,
                        tint = if(errorList?.isNotEmpty()!!) Color.Red else MaterialTheme.colorScheme.onBackground,
                        modifier = Modifier.size(30.dp)
                    )
                },
                label = {
                    Text(
                        text = BottomBar_items[1].title,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                }
            )
        }
    }
}

/*
@Composable
fun BottomNavigationBar(selectedTabIndex: Int, onTabSelected: (Int) -> Unit) {
    NavigationBar {
        Row(
            modifier = Modifier.background(MaterialTheme.colorScheme.inverseOnSurface)
        ) {
            BottomBar_items.forEachIndexed { index, item ->
                NavigationBarItem(
                    selected = index == selectedTabIndex,
                    onClick = {
                        onTabSelected(index)
                    },
                    icon = {
                        val painter: Painter = painterResource(id = item.icon)
                        Icon(
                            painter = painter,
                            contentDescription = item.title,
                            tint = MaterialTheme.colorScheme.onBackground,
                            modifier = Modifier.size(30.dp)
                        )
                    },
                    label = {
                        Text(
                            text = item.title,
                            color = MaterialTheme.colorScheme.onBackground
                        )
                    }
                )
            }
        }
    }
}*/