package com.example.ui.screens

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.RecordedStory
import com.example.ui.theme.*
import com.example.viewmodel.StoryLandViewModel
import kotlinx.coroutines.delay

@Composable
fun VoiceRecordingScreen(
    viewModel: StoryLandViewModel,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    var isRecording by remember { mutableStateOf(false) }
    var recordingSeconds by remember { mutableIntStateOf(0) }
    var storyTitle by remember { mutableStateOf("My Starlight Tale") }
    var hasRecordingToPreview by remember { mutableStateOf(false) }
    var isPlayingPreview by remember { mutableStateOf(false) }

    val recordedStories by viewModel.recordedStories.collectAsState()

    // Recording timer
    LaunchedEffect(isRecording) {
        if (isRecording) {
            recordingSeconds = 0
            while (isRecording) {
                delay(1000L)
                recordingSeconds += 1
            }
        }
    }

    // Animated pulse for mic
    val infiniteTransition = rememberInfiniteTransition(label = "mic_pulse")
    val pulseScale by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = if (isRecording) 1.25f else 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(600, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "mic_scale"
    )

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .windowInsetsPadding(WindowInsets.statusBars)
            .testTag("voice_recording_screen")
    ) {
        // Top Bar
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onBack, modifier = Modifier.testTag("recording_back_btn")) {
                Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = StoryPurplePrimary)
            }
            Column {
                Text(
                    text = "Story Voice Recorder",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = StoryPurplePrimary
                )
                Text(
                    text = "Narrate in your own voice and share your imagination",
                    style = MaterialTheme.typography.bodySmall,
                    color = StoryTextSecondary
                )
            }
        }

        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            contentPadding = PaddingValues(bottom = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Recording Studio Stage
            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 12.dp),
                    shape = RoundedCornerShape(26.dp),
                    colors = CardDefaults.cardColors(containerColor = StoryPurpleContainer)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        OutlinedTextField(
                            value = storyTitle,
                            onValueChange = { storyTitle = it },
                            label = { Text("Story Name") },
                            singleLine = true,
                            shape = RoundedCornerShape(16.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .testTag("recording_title_input")
                        )

                        Spacer(modifier = Modifier.height(24.dp))

                        // Waveform / Timer
                        val minutes = recordingSeconds / 60
                        val secs = recordingSeconds % 60
                        Text(
                            text = String.format("%02d:%02d", minutes, secs),
                            style = MaterialTheme.typography.headlineLarge,
                            fontWeight = FontWeight.Bold,
                            color = if (isRecording) Color.Red else StoryPurpleDark
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        // Simulated Audio Waveform Bars
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(4.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.height(40.dp)
                        ) {
                            val barHeights = if (isRecording) {
                                listOf(12.dp, 28.dp, 36.dp, 20.dp, 38.dp, 24.dp, 16.dp, 32.dp, 26.dp, 14.dp)
                            } else {
                                listOf(8.dp, 12.dp, 8.dp, 14.dp, 10.dp, 8.dp, 12.dp, 8.dp, 10.dp, 6.dp)
                            }
                            barHeights.forEach { height ->
                                Box(
                                    modifier = Modifier
                                        .width(6.dp)
                                        .height(height)
                                        .clip(RoundedCornerShape(3.dp))
                                        .background(if (isRecording) StoryPurplePrimary else StoryTextMuted)
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(24.dp))

                        // Big Mic Button
                        Box(
                            modifier = Modifier
                                .size(96.dp)
                                .scale(pulseScale)
                                .clip(CircleShape)
                                .background(if (isRecording) Color.Red else StoryPurplePrimary)
                                .clickable {
                                    if (isRecording) {
                                        isRecording = false
                                        hasRecordingToPreview = true
                                        viewModel.soundManager.playClick()
                                    } else {
                                        isRecording = true
                                        hasRecordingToPreview = false
                                        viewModel.soundManager.playClick()
                                    }
                                }
                                .testTag("mic_toggle_btn"),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = if (isRecording) Icons.Default.Stop else Icons.Default.Mic,
                                contentDescription = "Microphone",
                                tint = Color.White,
                                modifier = Modifier.size(44.dp)
                            )
                        }

                        Spacer(modifier = Modifier.height(14.dp))

                        Text(
                            text = if (isRecording) "Recording... Tap to STOP" else "Tap Mic to RECORD",
                            style = MaterialTheme.typography.labelMedium,
                            fontWeight = FontWeight.Bold,
                            color = if (isRecording) Color.Red else StoryPurplePrimary
                        )

                        // Preview & Save Controls
                        if (hasRecordingToPreview) {
                            Spacer(modifier = Modifier.height(20.dp))
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(10.dp)
                            ) {
                                OutlinedButton(
                                    onClick = {
                                        isPlayingPreview = !isPlayingPreview
                                    },
                                    shape = RoundedCornerShape(16.dp),
                                    modifier = Modifier.weight(1f)
                                ) {
                                    Icon(
                                        imageVector = if (isPlayingPreview) Icons.Default.Pause else Icons.Default.PlayArrow,
                                        contentDescription = null
                                    )
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text(if (isPlayingPreview) "Pause" else "Replay")
                                }

                                Button(
                                    onClick = {
                                        viewModel.saveRecordedStory(storyTitle, recordingSeconds)
                                        hasRecordingToPreview = false
                                    },
                                    shape = RoundedCornerShape(16.dp),
                                    colors = ButtonDefaults.buttonColors(containerColor = StoryGoldAccent),
                                    modifier = Modifier.weight(1f)
                                ) {
                                    Text("Save Story 💾", color = Color.Black, fontWeight = FontWeight.Bold)
                                }
                            }
                        }
                    }
                }
            }

            // Saved Recordings List
            item {
                Spacer(modifier = Modifier.height(10.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "My Recorded Stories",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
            }

            if (recordedStories.isEmpty()) {
                item {
                    Text(
                        text = "No recordings yet! Tap the mic to record your voice reading any tale.",
                        style = MaterialTheme.typography.bodySmall,
                        color = StoryTextMuted
                    )
                }
            } else {
                items(recordedStories) { rec ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp),
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
                    ) {
                        Row(
                            modifier = Modifier.padding(14.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text("🎙️", fontSize = 24.sp)
                            Spacer(modifier = Modifier.width(12.dp))
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = rec.title,
                                    style = MaterialTheme.typography.titleSmall,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                                Text(
                                    text = "${rec.date} • ${rec.durationSeconds}s",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = StoryTextSecondary
                                )
                            }
                            IconButton(onClick = { viewModel.soundManager.playSuccess() }) {
                                Icon(Icons.Default.PlayArrow, contentDescription = "Play", tint = StoryPurplePrimary)
                            }
                        }
                    }
                }
            }
        }
    }
}
