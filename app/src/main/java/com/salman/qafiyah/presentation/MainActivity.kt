package com.salman.qafiyah.presentation

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.salman.qafiyah.presentation.navigation.AppNavigation
import com.salman.qafiyah.presentation.theme.QafiyahTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            QafiyahTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val intent = remember {
                        mutableStateOf<Intent?>(null)
                    }

                    DisposableEffect(Unit) {
                        val listener = { newIntent: Intent ->
                            intent.value = newIntent
                        }
                        addOnNewIntentListener(listener)

                        onDispose {
                            removeOnNewIntentListener(listener)
                        }
                    }
                    AppNavigation(intent.value)
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    QafiyahTheme {
        Greeting("Android")
    }
}