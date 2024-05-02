package com.salman.qafiyah.presentation.composable

import androidx.compose.foundation.text.BasicTextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LifecycleEventEffect

/**
 * Created by Muhammed Salman email(mahmadslman@gmail.com) on 5/2/2024.
 */
@Composable
fun ArabicTextField(
    modifier: Modifier = Modifier,
    value: String,
    textStyle: TextStyle = TextStyle.Default,
    onValueChange: (String) -> Unit,
) {
    val focusManager = LocalFocusManager.current
    LifecycleEventEffect(event = Lifecycle.Event.ON_PAUSE) {
        focusManager.clearFocus(true)
    }
    BasicTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier,
        textStyle = textStyle,
    )

}