package com.example.watertracker.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier

@Composable
fun GlassSizeField(
    name: String,
    value: Int,
    onValueChange: (Int) -> Unit
) {
    var text by remember(value) {
        mutableStateOf(value.toString())
    }

    OutlinedTextField(
        value = text,
        onValueChange = { newValue ->
            if (newValue.all { it.isDigit() }) {
                text = newValue

                newValue.toIntOrNull()?.let {
                    if (it > 0) {
                        onValueChange(it)
                    }
                }
            }
        },
        label = {
            Text(name)
        },
        suffix = {
            Text("ml")
        },
        singleLine = true,
        modifier = Modifier.fillMaxWidth()
    )
}