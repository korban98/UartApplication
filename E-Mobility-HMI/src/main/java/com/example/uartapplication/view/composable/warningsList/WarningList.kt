package com.example.uartapplication.view.composable.warningsList

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.SmallFloatingActionButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import kotlinx.coroutines.launch

/**
 * Composable che mostra una dialog contenente la lista dei warning.
 *
 * @param warning Lista dei warning da visualizzare.
 * @param clicked Callback chiamato quando la dialog viene chiusa.
 */
@Composable
fun getWarningListDialog(warning: List<String>?, clicked: () -> Unit) {
    Dialog(onDismissRequest = { clicked() }) {
        ElevatedCard(
            elevation = CardDefaults.cardElevation(
                defaultElevation = 10.dp
            ),
            modifier = Modifier
                .size(width = 400.dp, height = 400.dp)
                .clickable(
                    enabled = false,
                    onClick = {}
                ),
            colors = CardDefaults.cardColors(
                containerColor = Color(0xB3131313)
            )
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                SmallFloatingActionButton(
                    onClick = { clicked() },
                    containerColor = Color.Black
                ) {
                    Icon(Icons.Filled.ArrowBack, "Chiudi lista dei warning", tint = Color.White)
                }
                Row(
                    modifier = Modifier
                        .weight(0.1f),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = " Warnings", color = Color.White)
                }
            }

            SetWarningLazyColumn(warning)
        }
    }
}

/**
 * Composable che mostra una LazyColumn contenente la lista dei warning.
 *
 * @param warningList Lista dei warning da visualizzare.
 */
@Composable
fun SetWarningLazyColumn(warningList: List<String>?) {
    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()

    // Per scorrere fino all'ultimo elemento della LazyColumn
    DisposableEffect(warningList) {
        if (warningList?.isNotEmpty() == true) {
            val lastIndex = warningList.size - 1
            coroutineScope.launch {
                listState.scrollToItem(index = lastIndex)
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
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(space = 5.dp),   // spazio tra gli elementi
            contentPadding = PaddingValues(all = 5.dp),
            state = listState
        ) {
            items(warningList.orEmpty()) {
                WarningsItem(stringWarning = it)
            }
        }
    }
}