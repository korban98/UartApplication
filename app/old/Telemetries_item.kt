package com.example.uartapplication.view.old

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.uartapplication.model.entities.TelemetriesData

val Telemetries_items = listOf(
    TelemetryProps(
        name = "stateOfHealth",
        value = ""
    ),
    TelemetryProps(
        name = "stateOfCharge",
        value = ""
    ),
    TelemetryProps(
        name = "remainingEnergy",
        value = ""
    ),
    TelemetryProps(
        name = "chargeState",
        value = ""
    ),
    TelemetryProps(
        name = "keyStatus",
        value = ""
    ),
    TelemetryProps(
        name = "inverterStatusL",
        value = ""
    ),
    TelemetryProps(
        name = "inverterStatusR",
        value = ""
    ),
    TelemetryProps(
        name = "motorTemperatureL",
        value = ""
    ),
    TelemetryProps(
        name = "motorTemperatureR",
        value = ""
    ),
    TelemetryProps(
        name = "currentGear",
        value = ""
    )
)

@Composable
fun TelemetryItem(index: Int, currentTelemetriesData: TelemetriesData?) {

    Telemetries_items[index].value = currentTelemetriesData?.getProps(index)?.toString() ?: ""
    val telemetry = Telemetries_items[index]
    var lastItemPaddingEnd = 0.dp
    if (index == Telemetries_items.size - 1) {
        lastItemPaddingEnd = 16.dp
    }

    Box(
        modifier = Modifier
            .padding(start = 16.dp, end = lastItemPaddingEnd)
    ) {
        Column(
            modifier = Modifier
                .clip(RoundedCornerShape(25.dp))
                .background(Color.LightGray)
                .width(250.dp)
                .height(160.dp)
                .padding(vertical = 12.dp, horizontal = 16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {

            Text(
                text = telemetry.name,
                color = Color.Black,
                fontSize = 17.sp,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = telemetry.value,
                color = Color.Black,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold
            )

        }
    }
}
