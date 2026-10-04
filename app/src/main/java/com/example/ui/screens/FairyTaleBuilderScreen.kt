package com.example.ui.screens

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.StoryData
import com.example.model.FairyTaleOption
import com.example.ui.theme.*
import com.example.viewmodel.AppScreen
import com.example.viewmodel.StoryLandViewModel

@Composable
fun FairyTaleBuilderScreen(
    viewModel: StoryLandViewModel,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    var selectedHero by remember { mutableStateOf(StoryData.fairyTaleHeroes[0]) }
    var selectedWorld by remember { mutableStateOf(StoryData.fairyTaleWorlds[0]) }
    var selectedCompanion by remember { mutableStateOf(StoryData.fairyTaleCompanions[0]) }
    var selectedProblem by remember { mutableStateOf(StoryData.fairyTaleProblems[0]) }
    var selectedEnding by remember { mutableStateOf(StoryData.fairyTaleEndings[0]) }

    var storyTitleInput by remember { mutableStateOf("") }
    var generatedPreviewText by remember { mutableStateOf<String?>(null) }

    fun generateStoryText() {
        val title = storyTitleInput.ifBlank { "${selectedHero.name} & The ${selectedWorld.name}" }
        val text = "Once upon a time, deep in the wonders of the ${selectedWorld.name}, there lived a ${selectedHero.name}.\n\n" +
                "One sunny afternoon, a great challenge arose across the land: ${selectedProblem.description}!\n\n" +
                "Without hesitation, our hero teamed up with a loyal companion: ${selectedCompanion.name} (${selectedCompanion.emoji}).\n\n" +
                "Together, using clever minds, caring hearts, and teamwork, they journeyed through the realm to solve the mystery.\n\n" +
                "Because of their courage, they achieved ${selectedEnding.description.lowercase()}!\n\n" +
                "And from that day onward, their tale was celebrated throughout ${selectedWorld.name} forever."
        generatedPreviewText = text
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .windowInsetsPadding(WindowInsets.statusBars)
            .testTag("fairy_tale_builder_screen")
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
                modifier = Modifier.testTag("builder_back_btn")
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Back",
                    tint = StoryPurplePrimary
                )
            }
            Column {
                Text(
                    text = "Fairy Tale Builder",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = StoryPurplePrimary
                )
                Text(
                    text = "Mix and match to craft your magical adventure",
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
            // STEP 1: HERO
            item {
                BuilderSectionTitle(stepNumber = 1, title = "Who is your hero?", icon = "👑")
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    contentPadding = PaddingValues(vertical = 4.dp)
                ) {
                    items(StoryData.fairyTaleHeroes) { hero ->
                        SelectionTile(
                            item = hero,
                            isSelected = selectedHero.id == hero.id,
                            onClick = {
                                selectedHero = hero
                                generatedPreviewText = null
                            }
                        )
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
            }

            // STEP 2: WORLD
            item {
                BuilderSectionTitle(stepNumber = 2, title = "Where does the story happen?", icon = "🏰")
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    contentPadding = PaddingValues(vertical = 4.dp)
                ) {
                    items(StoryData.fairyTaleWorlds) { world ->
                        SelectionTile(
                            item = world,
                            isSelected = selectedWorld.id == world.id,
                            onClick = {
                                selectedWorld = world
                                generatedPreviewText = null
                            }
                        )
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
            }

            // STEP 3: COMPANION
            item {
                BuilderSectionTitle(stepNumber = 3, title = "Choose a loyal companion", icon = "🐾")
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    contentPadding = PaddingValues(vertical = 4.dp)
                ) {
                    items(StoryData.fairyTaleCompanions) { comp ->
                        SelectionTile(
                            item = comp,
                            isSelected = selectedCompanion.id == comp.id,
                            onClick = {
                                selectedCompanion = comp
                                generatedPreviewText = null
                            }
                        )
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
            }

            // STEP 4: PROBLEM
            item {
                BuilderSectionTitle(stepNumber = 4, title = "What mystery or problem arises?", icon = "🗝️")
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    contentPadding = PaddingValues(vertical = 4.dp)
                ) {
                    items(StoryData.fairyTaleProblems) { prob ->
                        SelectionTile(
                            item = prob,
                            isSelected = selectedProblem.id == prob.id,
                            onClick = {
                                selectedProblem = prob
                                generatedPreviewText = null
                            }
                        )
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
            }

            // STEP 5: ENDING
            item {
                BuilderSectionTitle(stepNumber = 5, title = "How does the adventure end?", icon = "🎉")
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    contentPadding = PaddingValues(vertical = 4.dp)
                ) {
                    items(StoryData.fairyTaleEndings) { ending ->
                        SelectionTile(
                            item = ending,
                            isSelected = selectedEnding.id == ending.id,
                            onClick = {
                                selectedEnding = ending
                                generatedPreviewText = null
                            }
                        )
                    }
                }
                Spacer(modifier = Modifier.height(20.dp))
            }

            // Custom Title Field
            item {
                OutlinedTextField(
                    value = storyTitleInput,
                    onValueChange = { storyTitleInput = it },
                    label = { Text("Story Title (Optional)") },
                    placeholder = { Text("${selectedHero.name} & ${selectedCompanion.name}") },
                    singleLine = true,
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("builder_title_input")
                )
                Spacer(modifier = Modifier.height(16.dp))
            }

            // Generate Button
            item {
                Button(
                    onClick = { generateStoryText() },
                    shape = RoundedCornerShape(20.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = StoryPurplePrimary),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp)
                        .testTag("generate_fairytale_btn")
                ) {
                    Text(
                        text = "✨ WEAVE MY STORY",
                        fontWeight = FontWeight.Bold,
                        fontSize = 15.sp
                    )
                }
                Spacer(modifier = Modifier.height(20.dp))
            }

            // Story Preview Card
            generatedPreviewText?.let { previewText ->
                item {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("generated_fairytale_preview"),
                        shape = RoundedCornerShape(22.dp),
                        colors = CardDefaults.cardColors(containerColor = StoryPurpleContainer)
                    ) {
                        Column(modifier = Modifier.padding(20.dp)) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(
                                    text = "${selectedHero.emoji} + ${selectedCompanion.emoji}",
                                    fontSize = 28.sp
                                )
                                Spacer(modifier = Modifier.width(12.dp))
                                Column {
                                    Text(
                                        text = storyTitleInput.ifBlank { "${selectedHero.name} & The ${selectedWorld.name}" },
                                        style = MaterialTheme.typography.titleMedium,
                                        fontWeight = FontWeight.Bold,
                                        color = StoryPurpleDark
                                    )
                                    Text(
                                        text = "In ${selectedWorld.name}",
                                        style = MaterialTheme.typography.labelSmall,
                                        color = StoryPurplePrimary
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.height(14.dp))

                            Text(
                                text = previewText,
                                style = MaterialTheme.typography.bodyMedium,
                                color = StoryTextPrimary,
                                lineHeight = 24.sp
                            )

                            Spacer(modifier = Modifier.height(18.dp))

                            Button(
                                onClick = {
                                    viewModel.saveCreatedStory(
                                        title = storyTitleInput,
                                        hero = selectedHero,
                                        world = selectedWorld,
                                        companion = selectedCompanion,
                                        problem = selectedProblem,
                                        ending = selectedEnding
                                    )
                                },
                                shape = RoundedCornerShape(16.dp),
                                colors = ButtonDefaults.buttonColors(containerColor = StoryGoldAccent),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .testTag("save_fairytale_btn")
                            ) {
                                Text(
                                    text = "💾 SAVE TO MY STORIES (+40 XP)",
                                    fontWeight = FontWeight.Bold,
                                    color = Color.Black
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun BuilderSectionTitle(stepNumber: Int, title: String, icon: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(vertical = 8.dp)
    ) {
        Box(
            modifier = Modifier
                .size(26.dp)
                .clip(CircleShape)
                .background(StoryPurplePrimary),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "$stepNumber",
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = 12.sp
            )
        }
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground
        )
    }
}

@Composable
fun SelectionTile(
    item: FairyTaleOption,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .width(130.dp)
            .height(120.dp)
            .clip(RoundedCornerShape(18.dp))
            .border(
                width = if (isSelected) 3.dp else 1.dp,
                color = if (isSelected) StoryPurplePrimary else StoryDivider,
                shape = RoundedCornerShape(18.dp)
            )
            .clickable { onClick() }
            .testTag("builder_option_${item.id}"),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isSelected) StoryPurpleContainer else MaterialTheme.colorScheme.surface
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = item.emoji, fontSize = 32.sp)
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = item.name,
                style = MaterialTheme.typography.labelSmall,
                fontWeight = FontWeight.Bold,
                color = if (isSelected) StoryPurpleDark else MaterialTheme.colorScheme.onSurface,
                fontSize = 11.sp,
                maxLines = 2
            )
        }
    }
}
