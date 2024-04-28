package com.salman.qafiyah.presentation.screen.home

import android.app.Activity
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.DrawableRes
import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Button
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.salman.qafiyah.R
import com.salman.qafiyah.presentation.composable.Screen
import com.salman.qafiyah.presentation.composable.WavesAnimation
import org.koin.androidx.compose.koinViewModel

/**
 * Created by Muhammed Salman email(mahmadslman@gmail.com) on 4/27/2024.
 */
private const val AUDIO_PERMISSION = android.Manifest.permission.RECORD_AUDIO

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsState()
    val permissionLauncher = requestAudioPermission { granted ->
        if (granted) {
            viewModel.startRecording()
        }
    }

    DisposableEffect(key1 = Unit) {
        onDispose {
            viewModel.stopRecording()
        }
    }

    Screen {
        Column(
            Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Section(
                modifier = Modifier.weight(1f),
                title = stringResource(R.string.text_to_be_diacritize),
                actionIcon = R.drawable.ic_mic,
                hint = stringResource(R.string.enter_text_to_diacritize),
                showHint = state.showToBeDiacritizedHint,
                content = {
                    DiacritizeContent(
                        text = state.textToBeDiacritized,
                        onStopRecording = { viewModel.stopRecording() },
                        onTextChanged = viewModel::onTextToBeDiacritizedChanged,
                        isRecordingVoice = state.isVoiceRecordingRunning,
                    )
                },
                onActionClicked = {
                    if (!state.isVoiceRecordingRunning)
                        permissionLauncher.launch(AUDIO_PERMISSION)
                }
            )
            Button(
                modifier = Modifier.fillMaxWidth(),
                shape = MaterialTheme.shapes.small,
                contentPadding = PaddingValues(vertical = 16.dp),
                onClick = { /*TODO*/ }) {
                Text(text = stringResource(R.string.diacritize_text))
            }
            Section(
                modifier = Modifier.weight(1f),
                title = stringResource(R.string.text_after_diacritization),
                actionIcon = R.drawable.ic_clipboard
            )
            Spacer(modifier = Modifier.weight(0.2f))
        }
    }
}

@Composable
private fun Section(
    modifier: Modifier = Modifier,
    title: String,
    hint: String = "",
    showHint: Boolean = true,
    content: @Composable () -> Unit = {},
    @DrawableRes actionIcon: Int,
    onActionClicked: () -> Unit = {},
) {
    Column(modifier = modifier.fillMaxWidth()) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onBackground
        )
        Spacer(modifier = Modifier.height(14.dp))
        ElevatedCard(
            modifier = Modifier.fillMaxSize(),
            shape = MaterialTheme.shapes.small,
            elevation = CardDefaults.elevatedCardElevation(defaultElevation = 8.dp),
            colors = CardDefaults.elevatedCardColors(
                containerColor = MaterialTheme.colorScheme.surface,
            )
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.surface)
                    .padding(8.dp),
            ) {
                content()
                if (showHint)
                    Text(
                        text = hint,
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
                    )

                IconButton(
                    onClick = onActionClicked,
                    modifier = Modifier.align(Alignment.BottomEnd),
                    colors = IconButtonDefaults.iconButtonColors(
                        containerColor = MaterialTheme.colorScheme.tertiary,
                        contentColor = MaterialTheme.colorScheme.primary
                    )
                ) {
                    Icon(painter = painterResource(id = actionIcon), contentDescription = hint)
                }
            }
        }
    }
}

@Composable
private fun DiacritizeContent(
    modifier: Modifier = Modifier,
    text: String,
    isRecordingVoice: Boolean = true,
    onStopRecording: () -> Unit = {},
    onTextChanged: (String) -> Unit,
) {
    Box(modifier = modifier.fillMaxSize()) {
        AnimatedContent(targetState = isRecordingVoice, label = "diacritizeContent") {
            if (it) {
                WavesAnimation(onIconClick = onStopRecording)
            } else
                BasicTextField(
                    value = text, onValueChange = onTextChanged,
                    modifier = Modifier.fillMaxSize(),
                    textStyle = MaterialTheme.typography.bodyLarge.copy(
                        color = MaterialTheme.colorScheme.onSurface
                    ),
                )
        }
    }
}

@Composable
private fun requestAudioPermission(
    onShowRationaleDialog: () -> Unit = {},
    onResult: (Boolean) -> Unit
): ManagedActivityResultLauncher<String, Boolean> {
    val context = LocalContext.current as Activity
    val permissionLauncher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.RequestPermission()) {
            onResult(it)
        }
    if (context.shouldShowRequestPermissionRationale(AUDIO_PERMISSION)) {
        onShowRationaleDialog()
    }

    return permissionLauncher
}