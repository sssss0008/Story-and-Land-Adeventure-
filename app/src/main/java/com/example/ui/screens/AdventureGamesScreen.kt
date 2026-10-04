package com.example.ui.screens

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.StoryData
import com.example.ui.theme.*
import com.example.viewmodel.RewardCelebration
import com.example.viewmodel.StoryLandViewModel

// 1. DRAGON RESCUE ADVENTURE
@Composable
fun DragonRescueAdventure(
    viewModel: StoryLandViewModel,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    var step by remember { mutableIntStateOf(1) }
    var selectedOption by remember { mutableStateOf<Int?>(null) }
    var stepFeedback by remember { mutableStateOf<String?>(null) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .windowInsetsPadding(WindowInsets.statusBars)
            .testTag("dragon_rescue_screen")
    ) {
        AdventureHeader(title = "Dragon Rescue Quest", onBack = onBack)

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
                        .height(180.dp),
                    shape = RoundedCornerShape(24.dp),
                    colors = CardDefaults.cardColors(containerColor = StoryGreenLight)
                ) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(
                                text = when (step) {
                                    1 -> "🏔️ 🐉"
                                    2 -> "🍯 🍎"
                                    else -> "🌟 👑"
                                },
                                fontSize = 52.sp
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = "Mission Step $step of 3",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = StoryGreen
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
            }

            // Step Content
            item {
                when (step) {
                    1 -> {
                        Text(
                            text = "A gentle baby dragon named Pip is stranded on the high misty cliff of Moonlight Valley! What path should you take to reach the ledge?",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.SemiBold,
                            color = MaterialTheme.colorScheme.onBackground
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        ChoiceTile(
                            text = "Follow the trail of glowing blue spark-flowers",
                            isSelected = selectedOption == 0,
                            onClick = { selectedOption = 0 }
                        )
                        ChoiceTile(
                            text = "Walk through the dark slippery waterfall cave",
                            isSelected = selectedOption == 1,
                            onClick = { selectedOption = 1 }
                        )
                    }
                    2 -> {
                        Text(
                            text = "You reached Pip safely, but Pip is cold and frightened. How can you show friendship and warm him up?",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.SemiBold,
                            color = MaterialTheme.colorScheme.onBackground
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        ChoiceTile(
                            text = "Offer sweet dried honey apples & wrap him in your warm cloak",
                            isSelected = selectedOption == 0,
                            onClick = { selectedOption = 0 }
                        )
                        ChoiceTile(
                            text = "Shout loudly to call the village adults",
                            isSelected = selectedOption == 1,
                            onClick = { selectedOption = 1 }
                        )
                    }
                    3 -> {
                        Text(
                            text = "Pip purrs happily and spreads his wings! Together, you safely glide across the Star Chasm back to the valley celebration!",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.SemiBold,
                            color = MaterialTheme.colorScheme.onBackground
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Card(
                            colors = CardDefaults.cardColors(containerColor = StoryGoldLight),
                            shape = RoundedCornerShape(18.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column(
                                modifier = Modifier.padding(16.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text("🏆", fontSize = 36.sp)
                                Text(
                                    text = "Dragon Friend Badge Unlocked!",
                                    fontWeight = FontWeight.Bold,
                                    color = StoryGoldDark,
                                    fontSize = 16.sp
                                )
                                Text(
                                    text = "You showed immense kindness, empathy, and courage.",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = StoryTextSecondary
                                )
                            }
                        }
                    }
                }
            }

            stepFeedback?.let { fb ->
                item {
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = fb,
                        style = MaterialTheme.typography.bodyMedium,
                        color = StoryPurplePrimary,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
        }

        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .windowInsetsPadding(WindowInsets.navigationBars),
            tonalElevation = 8.dp
        ) {
            Box(modifier = Modifier.padding(16.dp)) {
                Button(
                    onClick = {
                        if (step < 3) {
                            if (selectedOption != null) {
                                viewModel.soundManager.playSuccess()
                                step += 1
                                selectedOption = null
                                stepFeedback = null
                            } else {
                                stepFeedback = "Please choose an action first!"
                            }
                        } else {
                            viewModel.soundManager.playCelebration()
                            viewModel.addXpAndStars(50, 15)
                            onBack()
                        }
                    },
                    shape = RoundedCornerShape(20.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = StoryGreen),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp)
                        .testTag("dragon_adventure_action_btn")
                ) {
                    Text(
                        text = if (step < 3) "CONTINUE QUEST ➔" else "COMPLETE ADVENTURE (+50 XP) 🌟",
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp
                    )
                }
            }
        }
    }
}

// 2. HERO ADVENTURE (Branching Choice Points)
@Composable
fun HeroAdventureScreen(
    viewModel: StoryLandViewModel,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    var branchPath by remember { mutableStateOf("start") }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .windowInsetsPadding(WindowInsets.statusBars)
            .testTag("hero_adventure_screen")
    ) {
        AdventureHeader(title = "The Hero's Quest", onBack = onBack)

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
                    shape = RoundedCornerShape(22.dp),
                    colors = CardDefaults.cardColors(containerColor = StoryPurpleContainer)
                ) {
                    Column(modifier = Modifier.padding(20.dp)) {
                        Text("🛡️ Quest Decision", fontWeight = FontWeight.Bold, color = StoryPurpleDark)
                        Spacer(modifier = Modifier.height(8.dp))

                        val promptText = when (branchPath) {
                            "start" -> "Sir Alden stands at the Crossroads of Whispering Woods. To the left is the Glowing Crystal Cave; to the right flows the Silver River towards the Old Castle. What should our hero do?"
                            "cave" -> "Inside the Crystal Cave, three harmonic chime-stones glow. A playful echo asks: 'Which note awakens the sleeping treasure door?'"
                            "river" -> "Beside the Silver River, an otter ferryman offers passage in exchange for a cheerful story or riddle!"
                            "castle" -> "You arrive at the castle courtyard! The kingdom bells chime in harmony to celebrate your clever decisions."
                            else -> "The quest was triumphant! You guided the kingdom to peace."
                        }

                        Text(
                            text = promptText,
                            style = MaterialTheme.typography.bodyLarge,
                            color = StoryTextPrimary,
                            lineHeight = 26.sp
                        )
                    }
                }
            }

            item {
                Spacer(modifier = Modifier.height(16.dp))
                when (branchPath) {
                    "start" -> {
                        ChoiceTile(
                            text = "Explore the Glowing Crystal Cave 💎",
                            isSelected = false,
                            onClick = {
                                viewModel.soundManager.playClick()
                                branchPath = "cave"
                            }
                        )
                        ChoiceTile(
                            text = "Follow the Silver River ⛵",
                            isSelected = false,
                            onClick = {
                                viewModel.soundManager.playClick()
                                branchPath = "river"
                            }
                        )
                    }
                    "cave" -> {
                        ChoiceTile(
                            text = "Play the High Bell of Courage 🔔",
                            isSelected = false,
                            onClick = {
                                viewModel.soundManager.playSuccess()
                                branchPath = "castle"
                            }
                        )
                        ChoiceTile(
                            text = "Whisper a gentle song to the crystals 🎶",
                            isSelected = false,
                            onClick = {
                                viewModel.soundManager.playSuccess()
                                branchPath = "castle"
                            }
                        )
                    }
                    "river" -> {
                        ChoiceTile(
                            text = "Share the tale of Pip the friendly dragon 🐉",
                            isSelected = false,
                            onClick = {
                                viewModel.soundManager.playSuccess()
                                branchPath = "castle"
                            }
                        )
                    }
                    "castle" -> {
                        Button(
                            onClick = {
                                viewModel.soundManager.playCelebration()
                                viewModel.addXpAndStars(35, 10)
                                onBack()
                            },
                            shape = RoundedCornerShape(20.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = StoryPurplePrimary),
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(52.dp)
                        ) {
                            Text("FINISH QUEST (+35 XP) 🏆", fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        }
    }
}

// 3. DETECTIVE KIDS
@Composable
fun DetectiveKidsScreen(
    viewModel: StoryLandViewModel,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    val caseData = StoryData.detectiveCase
    var selectedSuspect by remember { mutableStateOf<Int?>(null) }
    var isSolved by remember { mutableStateOf(false) }
    var feedbackMessage by remember { mutableStateOf<String?>(null) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .windowInsetsPadding(WindowInsets.statusBars)
            .testTag("detective_kids_screen")
    ) {
        AdventureHeader(title = "Detective Kids Clue Lab", onBack = onBack)

        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            contentPadding = PaddingValues(bottom = 24.dp)
        ) {
            // Case Title
            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    shape = RoundedCornerShape(22.dp),
                    colors = CardDefaults.cardColors(containerColor = StoryPurpleContainer)
                ) {
                    Column(modifier = Modifier.padding(18.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text("🔎", fontSize = 28.sp)
                            Spacer(modifier = Modifier.width(10.dp))
                            Text(
                                text = caseData.title,
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = StoryPurpleDark
                            )
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = caseData.mysteryDescription,
                            style = MaterialTheme.typography.bodyMedium,
                            color = StoryTextPrimary
                        )
                    }
                }
            }

            // Clues Notebook
            item {
                Text(
                    text = "Collected Clues Notebook",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
            }

            items(caseData.clues) { clue ->
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
                        Text(clue.emoji, fontSize = 28.sp)
                        Spacer(modifier = Modifier.width(12.dp))
                        Column {
                            Text(
                                text = clue.title,
                                style = MaterialTheme.typography.titleSmall,
                                fontWeight = FontWeight.Bold,
                                color = StoryPurpleDark
                            )
                            Text(
                                text = "${clue.description} (Found at: ${clue.location})",
                                style = MaterialTheme.typography.bodySmall,
                                color = StoryTextSecondary
                            )
                        }
                    }
                }
            }

            // Suspect deduction
            item {
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Who is the culprit based on the clues?",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground
                )
                Spacer(modifier = Modifier.height(8.dp))
            }

            items(caseData.suspects.indices.toList()) { sIdx ->
                ChoiceTile(
                    text = caseData.suspects[sIdx],
                    isSelected = selectedSuspect == sIdx,
                    onClick = {
                        selectedSuspect = sIdx
                        feedbackMessage = null
                    }
                )
            }

            feedbackMessage?.let { msg ->
                item {
                    Spacer(modifier = Modifier.height(12.dp))
                    Card(
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = if (isSolved) StoryGreenLight else StoryGoldLight
                        ),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(
                                text = if (isSolved) "🎉 MYSTERY SOLVED!" else "🤔 Not quite the right suspect!",
                                fontWeight = FontWeight.Bold,
                                color = if (isSolved) StoryGreen else StoryGoldDark
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = msg,
                                style = MaterialTheme.typography.bodyMedium,
                                color = StoryTextPrimary
                            )
                        }
                    }
                }
            }
        }

        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .windowInsetsPadding(WindowInsets.navigationBars),
            tonalElevation = 8.dp
        ) {
            Box(modifier = Modifier.padding(16.dp)) {
                Button(
                    onClick = {
                        if (selectedSuspect == caseData.correctCulpritIndex) {
                            isSolved = true
                            feedbackMessage = caseData.solutionExplanation
                            viewModel.soundManager.playCelebration()
                            viewModel.addXpAndStars(40, 10)
                        } else {
                            isSolved = false
                            feedbackMessage = "Look closely at the blue feathers and webbed footprints!"
                            viewModel.soundManager.playClick()
                        }
                    },
                    shape = RoundedCornerShape(20.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = StoryPurplePrimary),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp)
                        .testTag("submit_deduction_btn")
                ) {
                    Text("VERIFY DEDUCTION 🔍", fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}

// Common Adventure Header
@Composable
fun AdventureHeader(title: String, onBack: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = onBack) {
            Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = StoryPurplePrimary)
        }
        Text(
            text = title,
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            color = StoryPurplePrimary
        )
    }
}

@Composable
fun ChoiceTile(
    text: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 5.dp)
            .clip(RoundedCornerShape(16.dp))
            .border(
                width = if (isSelected) 3.dp else 1.dp,
                color = if (isSelected) StoryPurplePrimary else StoryDivider,
                shape = RoundedCornerShape(16.dp)
            )
            .clickable { onClick() },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isSelected) StoryPurpleContainer else MaterialTheme.colorScheme.surface
        )
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            RadioButton(
                selected = isSelected,
                onClick = onClick,
                colors = RadioButtonDefaults.colors(selectedColor = StoryPurplePrimary)
            )
            Spacer(modifier = Modifier.width(10.dp))
            Text(
                text = text,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}
