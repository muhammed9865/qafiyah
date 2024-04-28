package com.salman.qafiyah.presentation.screen.settings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.salman.qafiyah.R
import com.salman.qafiyah.presentation.composable.Screen
import com.salman.qafiyah.presentation.theme.QafiyahTheme

/**
 * Created by Muhammed Salman email(mahmadslman@gmail.com) on 4/27/2024.
 */
@Composable
fun SettingsScreen() {
    var scale by remember {
        mutableFloatStateOf(1f)
    }
    Screen {
        Column {
            Text(
                text = stringResource(id = R.string.settings),
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onBackground
            )
            Spacer(modifier = Modifier.height(24.dp))
            FontScaleSlider(scale = scale) {
                scale = it
            }
        }
    }
}

@Composable
private fun FontScaleSlider(
    modifier: Modifier = Modifier,
    scale: Float,
    onFontScaleChange: (Float) -> Unit
) {
    Column(modifier.fillMaxWidth()) {
        Row(
            Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(id = R.string.font_size),
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onBackground
            )
            Text(
                text = scale.toString(),
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onBackground
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        Slider(
            value = scale,
            onValueChange = onFontScaleChange,
            valueRange = 0.5f..3f,
            steps = 4,
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun SliderPrev() {
    QafiyahTheme {
        FontScaleSlider(
            scale = 1f,
            onFontScaleChange = {}
        )
    }
}