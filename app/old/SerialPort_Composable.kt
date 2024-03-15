@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.uartapplication.view.old

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.uartapplication.model.entities.TelemetriesData
import com.example.uartapplication.view.ui.theme.UartApplicationTheme
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import kotlinx.coroutines.launch
import androidx.compose.ui.geometry.*
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import com.example.uartapplication.R
import com.example.uartapplication.view.composable.batteryCanvas.Battery
import com.example.uartapplication.view.composable.topBar.ErrorsItem

@Preview(showSystemUi = true, device = Devices.AUTOMOTIVE_1024p)
@Composable
fun SerialPort_ComposablePreview() {
    val mockTelemetriesData: TelemetriesData? = null// Inizializza con dati di esempio
    val mockErrorData: List<String>? = null// Inizializza con dati di esempio
    val mockStringa: String = "Esempio di stringa"

    SerialPort_Composable(telemetriesData = mockTelemetriesData, errorData = mockErrorData, stringa = mockStringa)
}

@Composable
fun SerialPort_Composable(telemetriesData: TelemetriesData?, errorData: List<String>?, stringa: String) {
    var currentTelemetriesData by remember { mutableStateOf<TelemetriesData?>(null) }
    var errorsList by remember { mutableStateOf<List<String>?>(mutableListOf()) }
    var str by remember { mutableStateOf("") }

    LaunchedEffect(telemetriesData) {
        currentTelemetriesData = telemetriesData
    }

    LaunchedEffect(errorData) {
        errorsList = errorData
    }

    LaunchedEffect(stringa) {
        str = stringa
    }

    UartApplicationTheme {
        SetBarColor(color = MaterialTheme.colorScheme.background)
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            ElectricCarData(currentTelemetriesData, errorsList, str)
        }
    }
}

@Composable
private fun SetBarColor(color: Color) {
    val systemUiController = rememberSystemUiController()
    SideEffect {
        systemUiController.setSystemBarsColor(
            color = color
        )
    }
}

@ExperimentalMaterial3Api
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ElectricCarData(telemetriesData: TelemetriesData?, errorList: List<String>?, stringa: String) {
    var selectedTabIndex by remember { mutableStateOf(0) }

    Scaffold(
        bottomBar = {
            BottomNavigationBar(selectedTabIndex = selectedTabIndex, errorList) {
                // Callback to update the selectedTabIndex
                selectedTabIndex = it
            }
        }
    ) { padding ->
            Spacer(modifier = Modifier.height(25.dp))
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            SetTelemetriesStates(telemetriesData, padding)
            /*  Text(text = "JSON_STRING:   $stringa")
              Text(text = "\nSTRING->JSON & JSON->OBJ:")
              Text(text = "SOH: ${telemetriesData?.stateOfHealth ?: "null"}")
              Text(text = "SOC: ${telemetriesData?.stateOfCharge ?: "null"}")
              Text(text = "RE: ${telemetriesData?.remainingEnergy ?: "null"}")
              Text(text = "CS: ${telemetriesData?.chargeState ?: "null"}")
              Text(text = "KS: ${telemetriesData?.keyStatus ?: "null"}")
              Text(text = "ISL: ${telemetriesData?.inverterStatusL ?: "null"}")
              Text(text = "ISR: ${telemetriesData?.inverterStatusR ?: "null"}")
              Text(text = "MTL: ${telemetriesData?.motorTemperatureL ?: "null"}")
              Text(text = "MTR: ${telemetriesData?.motorTemperatureR ?: "null"}")
              Text(text = "CG: ${telemetriesData?.currentGear ?: "null"}")
              Text(text = "BE: ${telemetriesData?.error ?: "null"}")
              Text(text = "BE decoded: \"${errorList?.lastOrNull() ?: "null"}\"")*/
          }
            /*when (selectedTabIndex) {
                0 -> {
                    //SetTelemetriesStates(telemetriesData, padding)
                }
                1 -> {
                    SetErrorsLazyColumn(padding = padding, errorList)
                }
            }*/
    }
}

@Composable
private fun SetTelemetriesStates(telemetriesData: TelemetriesData?, padding: PaddingValues) {
    Row(
        modifier = Modifier
            .fillMaxSize()
            .padding(padding)
    ) {
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(25.dp))
                .weight(1f)
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier
                    .clip(RoundedCornerShape(25.dp))
                    .background(Color.LightGray)
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .padding(vertical = 12.dp, horizontal = 16.dp),
                verticalAlignment = Alignment.Top,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                if(telemetriesData?.stateOfCharge != null){
                    Row(
                        modifier = Modifier
                            .padding(start = 16.dp) // Aggiunto spazio tra le Box interne
                    ) {
                        Text(
                            text = "${telemetriesData.stateOfCharge}%",
                            color = Color.Black
                        )
                        Spacer(modifier = Modifier.width(5.dp))
                        Battery(
                            value = telemetriesData.stateOfCharge!!,
                            colore = when {
                                telemetriesData.stateOfCharge!! >= 50 -> { Color.Green }
                                telemetriesData.stateOfCharge!! in 21..49 -> { Color.Yellow }
                                telemetriesData.stateOfCharge!! <= 20 -> { Color.Red }
                                else -> { Color.Gray }
                            },
                            modifier = Modifier
                                .height(20.dp)
                                .width(30.dp)
                        )
                    }
                }
                if(telemetriesData?.stateOfHealth != null) {
                    val batteryPainter: Painter = painterResource(id = R.drawable.battery_icon)
                    Row(
                        modifier = Modifier
                            .padding(start = 16.dp) // Aggiunto spazio tra le Box interne
                    ) {
                        Text(
                            text = "${telemetriesData.stateOfHealth}%",
                            color = Color.Black
                        )
                        Icon(
                            painter = batteryPainter,
                            contentDescription = null,
                            tint = when {
                                telemetriesData.stateOfHealth!! > 70 -> { Color.Green }
                                telemetriesData.stateOfHealth!! in 51..70 -> { Color.Yellow }
                                telemetriesData.stateOfHealth!! < 50 -> { Color.Red }
                                else -> { Color.Gray }
                            },
                            modifier = Modifier
                                .height(30.dp)
                                .width(40.dp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun SetErrorsLazyColumn(padding: PaddingValues, errorList: List<String>?) {
    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()

    // to scroll to the last item of the lazyColumn
    DisposableEffect(errorList) {
        if (errorList?.isNotEmpty() == true) {
            val lastIndex = errorList.size - 1
            coroutineScope.launch {
                listState.animateScrollToItem(index = lastIndex)
            }
        }
        onDispose { }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            verticalArrangement = Arrangement.spacedBy(space = 5.dp),   // gap between items
            contentPadding = PaddingValues(all = 5.dp),
            state = listState
        ) {
            items(errorList.orEmpty()) {
                ErrorsItem(stringError = it)
            }
        }
    }
}