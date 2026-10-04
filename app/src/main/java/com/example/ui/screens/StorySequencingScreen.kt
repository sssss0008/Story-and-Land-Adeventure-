package com.example.ui.screens

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import com.example.data.StoryData
import com.example.model.SequenceCard
import com.example.ui.theme.*
import com.example.viewmodel.StoryLandViewModel

@Composable
fun StorySequencingScreen(
    viewModel: StoryLandViewModel,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    val game = StoryData.sequencingGame
    // Start with a shuffled order
    var currentCards by remember {
        mutableStateOf(
            listOf(
                game.scenes[2],
                game.scenes[0],
                game.scenes[4],
                game.scenes[1],
                game.scenes[3]
            )
        )
    }

    var selectedIndex by remember { mutableStateOf<Int?>(null) }
    var isChecking by remember { mutableStateOf(false) }
    var isCorrect by remember { mutableStateOf<Boolean?>(null) }

    fun checkOrder() {
        val inCorrectOrder = currentCards.indices.all { idx ->
            currentCards[idx].originalOrder == idx + 1
        }
        isCorrect = inCorrectOrder
        if (inCorrectOrder) {
            viewModel.soundManager.playCelebration()
            viewModel.addXpAndStars(game.xpReward, game.starsReward)
        } else {
            viewModel.soundManager.playClick()
        }
    }

    fun swap(from: Int, to: Int) {
        if (to in currentCards.indices) {
            val list = currentCards.toMutableList()
            val temp = list[from]
            list[from] = list[to]
            list[to] = temp
            currentCards = list
            isCorrect = null
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .windowInsetsPadding(WindowInsets.statusBars)
            .testTag("story_sequencing_screen")
    ) {
        // Top App Bar
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = onBack,
                modifier = Modifier.testTag("sequencing_back_btn")
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Back",
                    tint = StoryPurplePrimary
                )
            }
            Column {
                Text(
                    text = "Story Sequencing Game",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = StoryPurplePrimary
                )
                Text(
                    text = "Arrange the story in order from beginning to end",
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
            contentPadding = PaddingValues(bottom = 24.dp)
        ) {
            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(containerColor = StoryPurpleContainer)
                ) {
                    Row(
                        modifier = Modifier.padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("💡", fontSize = 28.sp)
                        Spacer(modifier = Modifier.width(12.dp))
                        Text(
                            text = "Tap the up/down arrows or tap two cards to swap them until the story flows chronologically!",
                            style = MaterialTheme.typography.bodyMedium,
                            color = StoryPurpleDark
                        )
                    }
                }
                Spacer(modifier = Modifier.height(10.dp))
            }

            // Cards in current order
            itemsIndexed(currentCards) { index, card ->
                val isSelected = selectedIndex == index
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 5.dp)
                        .clip(RoundedCornerShape(18.dp))
                        .border(
                            width = if (isSelected) 3.dp else 1.dp,
                            color = if (isSelected) StoryPurplePrimary else StoryDivider,
                            shape = RoundedCornerShape(18.dp)
                        )
                        .clickable {
                            if (selectedIndex == null) {
                                selectedIndex = index
                            } else {
                                swap(selectedIndex!!, index)
                                selectedIndex = null
                            }
                        }
                        .testTag("sequence_card_$index"),
                    shape = RoundedCornerShape(18.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                ) {
                    Row(
                        modifier = Modifier.padding(14.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // Position order pill
                        Box(
                            modifier = Modifier
                                .size(34.dp)
                                .clip(CircleShape)
                                .background(StoryPurplePrimary),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "${index + 1}",
                                color = Color.White,
                                fontWeight = FontWeight.Bold,
                                fontSize = 15.sp
                            )
                        }

                        Spacer(modifier = Modifier.width(14.dp))

                        Text(card.emoji, fontSize = 32.sp)

                        Spacer(modifier = Modifier.width(14.dp))

                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = card.title,
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            Text(
                                text = card.description,
                                style = MaterialTheme.typography.bodySmall,
                                color = StoryTextSecondary
                            )
                        }

                        // Up / Down reorder arrows
                        Column {
                            IconButton(
                                onClick = { swap(index, index - 1) },
                                enabled = index > 0,
                                modifier = Modifier.size(32.dp)
                            ) {
                                Icon(Icons.Default.KeyboardArrowUp, contentDescription = "Move Up")
                            }
                            IconButton(
                                onClick = { swap(index, index + 1) },
                                enabled = index < currentCards.size - 1,
                                modifier = Modifier.size(32.dp)
                            ) {
                                Icon(Icons.Default.KeyboardArrowDown, contentDescription = "Move Down")
                            }
                        }
                    }
                }
            }

            // Verification Result
            isCorrect?.let { correct ->
                item {
                    Spacer(modifier = Modifier.height(16.dp))
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(20.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = if (correct) StoryGreenLight else StoryGoldLight
                        )
                    ) {
                        Column(
                            modifier = Modifier.padding(18.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = if (correct) "🎉 STORY SEQUENCED PERFECTLY!" else "🤔 Not quite in order yet!",
                                fontWeight = FontWeight.Bold,
                                color = if (correct) StoryGreen else StoryGoldDark,
                                fontSize = 16.sp
                            )
                            Spacer(modifier = Modifier.height(6.dp))
                            Text(
                                text = if (correct) {
                                    "+${game.xpReward} XP & +${game.starsReward} Stars Earned! You unlocked the Story Sequencer Badge!"
                                } else {
                                    "Think about what happened first: discovering the map, then entering the woods..."
                                },
                                textAlign = TextAlign.Center,
                                style = MaterialTheme.typography.bodyMedium,
                                color = StoryTextPrimary
                            )
                        }
                    }
                }
            }
        }

        // Bottom CTA
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .windowInsetsPadding(WindowInsets.navigationBars),
            tonalElevation = 8.dp
        ) {
            Box(modifier = Modifier.padding(16.dp)) {
                Button(
                    onClick = { checkOrder() },
                    shape = RoundedCornerShape(20.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = StoryPurplePrimary),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp)
                        .testTag("check_sequence_btn")
                ) {
                    Text(
                        text = "⭐ CHECK STORY ORDER",
                        fontWeight = FontWeight.Bold,
                        fontSize = 15.sp
                    )
                }
            }
        }
    }
}
