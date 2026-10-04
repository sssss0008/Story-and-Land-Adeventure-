package com.example.ui.screens

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.Story
import com.example.ui.theme.*
import com.example.viewmodel.StoryLandViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StoryReaderScreen(
    viewModel: StoryLandViewModel,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    val story by viewModel.activeStory.collectAsState()
    val chapterIdx by viewModel.activeChapterIndex.collectAsState()
    val isPlaying by viewModel.narrationManager.isPlaying.collectAsState()
    val currentSentenceIdx by viewModel.narrationManager.currentSentenceIndex.collectAsState()
    val currentSpeed by viewModel.narrationManager.playbackSpeed.collectAsState()
    val userProfile by viewModel.userProfile.collectAsState()
    val appSettings by viewModel.appSettings.collectAsState()

    val currentStory = story ?: return
    val currentChapter = currentStory.chapters.getOrNull(chapterIdx) ?: return
    val totalChapters = currentStory.chapters.size
    val isFavorite = userProfile.favoriteStoryIds.contains(currentStory.id)

    var showTextSizeDialog by remember { mutableStateOf(false) }

    // Text size multiplier
    val baseFontSize = (18 * appSettings.textSizeScale).sp
    val baseLineHeight = (28 * appSettings.textSizeScale).sp

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .windowInsetsPadding(WindowInsets.statusBars)
            .testTag("story_reader_screen")
    ) {
        // Top Navigation Bar
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp, vertical = 6.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            IconButton(
                onClick = {
                    viewModel.narrationManager.stop()
                    onBack()
                },
                modifier = Modifier.testTag("reader_back_btn")
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Back",
                    tint = StoryPurplePrimary
                )
            }

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = currentStory.title,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground,
                    maxLines = 1
                )
                Text(
                    text = "Chapter ${chapterIdx + 1} of $totalChapters",
                    style = MaterialTheme.typography.labelSmall,
                    color = StoryTextSecondary
                )
            }

            Row {
                IconButton(
                    onClick = { showTextSizeDialog = true },
                    modifier = Modifier.testTag("reader_font_size_btn")
                ) {
                    Icon(
                        imageVector = Icons.Default.FormatSize,
                        contentDescription = "Text Size",
                        tint = StoryPurplePrimary
                    )
                }

                IconButton(
                    onClick = { viewModel.toggleFavorite(currentStory.id) },
                    modifier = Modifier.testTag("reader_favorite_btn")
                ) {
                    Icon(
                        imageVector = if (isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                        contentDescription = "Favorite",
                        tint = if (isFavorite) StoryPink else StoryTextMuted
                    )
                }
            }
        }

        // Chapter Reading Progress Bar
        LinearProgressIndicator(
            progress = { ((chapterIdx + 1).toFloat() / totalChapters).coerceIn(0f, 1f) },
            modifier = Modifier
                .fillMaxWidth()
                .height(4.dp),
            color = StoryPurplePrimary,
            trackColor = StoryPurpleContainer
        )

        // Story Content & Illustration
        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
            contentPadding = PaddingValues(top = 16.dp, bottom = 24.dp)
        ) {
            // Chapter Scene Illustration
            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(180.dp),
                    shape = RoundedCornerShape(24.dp),
                    colors = CardDefaults.cardColors(containerColor = StoryPurpleContainer)
                ) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(
                                text = currentChapter.sceneEmoji,
                                fontSize = 68.sp
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = currentChapter.title,
                                style = MaterialTheme.typography.titleLarge,
                                fontWeight = FontWeight.Bold,
                                color = StoryPurpleDark
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.height(20.dp))
            }

            // Paragraphs / Sentences with Narration Highlight
            itemsIndexed(currentChapter.paragraphs) { pIndex, paragraph ->
                val isCurrentReadingParagraph = isPlaying && (pIndex == currentSentenceIdx % currentChapter.paragraphs.size)

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 6.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = if (isCurrentReadingParagraph) StoryGoldLight else MaterialTheme.colorScheme.surface
                    ),
                    elevation = CardDefaults.cardElevation(defaultElevation = if (isCurrentReadingParagraph) 4.dp else 1.dp)
                ) {
                    Row(modifier = Modifier.padding(18.dp)) {
                        if (isCurrentReadingParagraph) {
                            Text("✨", fontSize = 16.sp, modifier = Modifier.padding(end = 8.dp))
                        }
                        Text(
                            text = paragraph,
                            fontSize = baseFontSize,
                            lineHeight = baseLineHeight,
                            color = if (isCurrentReadingParagraph) StoryPurpleDark else MaterialTheme.colorScheme.onSurface,
                            fontWeight = if (isCurrentReadingParagraph) FontWeight.SemiBold else FontWeight.Normal
                        )
                    }
                }
            }

            // End of chapter moral
            if (chapterIdx == totalChapters - 1 && currentStory.moral.isNotBlank()) {
                item {
                    Spacer(modifier = Modifier.height(16.dp))
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(18.dp),
                        colors = CardDefaults.cardColors(containerColor = StoryTealLight)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(
                                text = "💡 Story Wisdom",
                                fontWeight = FontWeight.Bold,
                                color = StoryTeal,
                                fontSize = 14.sp
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = currentStory.moral,
                                style = MaterialTheme.typography.bodyMedium,
                                color = StoryTextPrimary
                            )
                        }
                    }
                }
            }
        }

        // Bottom Narration & Page Turn Controls
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .windowInsetsPadding(WindowInsets.navigationBars),
            tonalElevation = 10.dp,
            shadowElevation = 8.dp
        ) {
            Column(
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp)
            ) {
                // Audio controls row
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    // Previous Chapter Button
                    FilledTonalIconButton(
                        onClick = { viewModel.prevChapter() },
                        enabled = chapterIdx > 0,
                        modifier = Modifier.testTag("reader_prev_chapter_btn")
                    ) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Previous Chapter")
                    }

                    // Narration Play/Pause CTA
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        // Speed Chip
                        AssistChip(
                            onClick = {
                                val nextSpeed = when (currentSpeed) {
                                    1.0f -> 1.25f
                                    1.25f -> 0.75f
                                    else -> 1.0f
                                }
                                viewModel.narrationManager.setSpeed(nextSpeed)
                            },
                            label = { Text("${currentSpeed}x", fontWeight = FontWeight.Bold) },
                            shape = RoundedCornerShape(12.dp)
                        )

                        // Play/Pause Floating Action Button
                        Button(
                            onClick = {
                                if (isPlaying) {
                                    viewModel.narrationManager.pause()
                                } else {
                                    viewModel.narrationManager.play()
                                }
                            },
                            shape = RoundedCornerShape(20.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = StoryPurplePrimary),
                            modifier = Modifier
                                .height(46.dp)
                                .testTag("reader_narration_toggle_btn")
                        ) {
                            Icon(
                                imageVector = if (isPlaying) Icons.Default.Pause else Icons.Default.PlayArrow,
                                contentDescription = if (isPlaying) "Pause Narration" else "Play Narration"
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = if (isPlaying) "PAUSE" else "READ TO ME",
                                fontWeight = FontWeight.Bold,
                                fontSize = 13.sp
                            )
                        }
                    }

                    // Next Chapter / Finish Story Button
                    Button(
                        onClick = { viewModel.nextChapter() },
                        shape = RoundedCornerShape(16.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (chapterIdx + 1 == totalChapters) StoryGoldAccent else StoryPurplePrimary
                        ),
                        modifier = Modifier.testTag("reader_next_chapter_btn")
                    ) {
                        Text(
                            text = if (chapterIdx + 1 < totalChapters) "NEXT ➔" else "FINISH 🌟",
                            fontWeight = FontWeight.Bold,
                            fontSize = 12.sp,
                            color = if (chapterIdx + 1 == totalChapters) Color.Black else Color.White
                        )
                    }
                }
            }
        }
    }

    // Font size selector dialog
    if (showTextSizeDialog) {
        AlertDialog(
            onDismissRequest = { showTextSizeDialog = false },
            title = { Text("Reading Text Size") },
            text = {
                Column {
                    Text("Select a comfortable font size for reading:")
                    Spacer(modifier = Modifier.height(16.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceAround
                    ) {
                        listOf(
                            "Small" to 0.85f,
                            "Regular" to 1.0f,
                            "Large" to 1.2f,
                            "Huge" to 1.35f
                        ).forEach { (label, scale) ->
                            FilterChip(
                                selected = appSettings.textSizeScale == scale,
                                onClick = {
                                    viewModel.updateSettings(appSettings.copy(textSizeScale = scale))
                                },
                                label = { Text(label) }
                            )
                        }
                    }
                }
            },
            confirmButton = {
                TextButton(onClick = { showTextSizeDialog = false }) {
                    Text("Done")
                }
            }
        )
    }
}
