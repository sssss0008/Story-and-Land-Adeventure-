package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.UserProfile
import com.example.ui.theme.*
import com.example.viewmodel.AppScreen
import com.example.viewmodel.StoryLandViewModel

data class PracticeModule(
    val id: String,
    val title: String,
    val description: String,
    val emoji: String,
    val category: String,
    val xpReward: Int,
    val progress: Float,
    val screenTarget: AppScreen
)

@Composable
fun PracticeScreen(
    viewModel: StoryLandViewModel,
    userProfile: UserProfile,
    onNavigate: (AppScreen) -> Unit,
    modifier: Modifier = Modifier
) {
    var selectedCategory by remember { mutableStateOf("All") }
    val categories = listOf("All", "Reading", "Storytelling", "Listening", "Creativity")

    val modules = listOf(
        PracticeModule(
            id = "read_along",
            title = "Read Along Mode",
            description = "Follow synchronized sentence highlighting with audio narration.",
            emoji = "🎧",
            category = "Reading",
            xpReward = 20,
            progress = 0.8f,
            screenTarget = AppScreen.Reader("secret_door")
        ),
        PracticeModule(
            id = "story_sequencing",
            title = "Story Sequencing",
            description = "Arrange illustrated scene cards in correct order from start to end.",
            emoji = "🧩",
            category = "Reading",
            xpReward = 35,
            progress = 0.5f,
            screenTarget = AppScreen.StorySequencing
        ),
        PracticeModule(
            id = "fairy_tale_builder",
            title = "Fairy Tale Builder",
            description = "Combine heroes, worlds, companions & twists into magical stories.",
            emoji = "✨",
            category = "Creativity",
            xpReward = 40,
            progress = 0.6f,
            screenTarget = AppScreen.FairyTaleBuilder
        ),
        PracticeModule(
            id = "character_creator",
            title = "Character Creation Studio",
            description = "Design magical heroes with unique powers, outfits and backstories.",
            emoji = "🧑‍🎨",
            category = "Creativity",
            xpReward = 30,
            progress = 0.7f,
            screenTarget = AppScreen.CharacterCreator
        ),
        PracticeModule(
            id = "guided_story",
            title = "Story Structure & Writing",
            description = "Practice Beginning, Middle, and End with guided visual prompts.",
            emoji = "✍️",
            category = "Storytelling",
            xpReward = 40,
            progress = 0.4f,
            screenTarget = AppScreen.CreateStory
        ),
        PracticeModule(
            id = "voice_storytelling",
            title = "Voice Storytelling & Recording",
            description = "Narrate story scenes in your own voice and save your recordings.",
            emoji = "🎙️",
            category = "Listening",
            xpReward = 35,
            progress = 0.3f,
            screenTarget = AppScreen.VoiceRecording
        ),
        PracticeModule(
            id = "mystery_logic",
            title = "Detective Logic & Clues",
            description = "Practice observation, statement analysis, and deductive reasoning.",
            emoji = "🔍",
            category = "Storytelling",
            xpReward = 30,
            progress = 0.9f,
            screenTarget = AppScreen.DetectiveKids
        ),
        PracticeModule(
            id = "hero_choices",
            title = "Hero Decision Making",
            description = "Evaluate consequences at story decision points to guide the quest.",
            emoji = "🛡️",
            category = "Storytelling",
            xpReward = 25,
            progress = 0.5f,
            screenTarget = AppScreen.HeroAdventure
        )
    )

    val filteredModules = if (selectedCategory == "All") {
        modules
    } else {
        modules.filter { it.category == selectedCategory }
    }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .testTag("practice_screen_content"),
        contentPadding = PaddingValues(bottom = 96.dp)
    ) {
        // Header
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 12.dp)
            ) {
                Text(
                    text = "Learn Through Stories",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.ExtraBold,
                    color = StoryPurplePrimary
                )
                Text(
                    text = "Read, imagine, create, and explore.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = StoryTextSecondary
                )
            }
        }

        // Category Filter Chips
        item {
            LazyRow(
                contentPadding = PaddingValues(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.padding(bottom = 12.dp)
            ) {
                items(categories) { cat ->
                    val isSelected = selectedCategory == cat
                    FilterChip(
                        selected = isSelected,
                        onClick = { selectedCategory = cat },
                        label = {
                            Text(
                                text = cat,
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium
                            )
                        },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = StoryPurplePrimary,
                            selectedLabelColor = Color.White
                        ),
                        shape = RoundedCornerShape(16.dp)
                    )
                }
            }
        }

        // Practice Modules List
        items(filteredModules) { module ->
            PracticeModuleCard(module = module, onStart = { onNavigate(module.screenTarget) })
        }
    }
}

@Composable
fun PracticeModuleCard(
    module: PracticeModule,
    onStart: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 6.dp)
            .clip(RoundedCornerShape(22.dp))
            .clickable { onStart() }
            .testTag("practice_card_${module.id}"),
        shape = RoundedCornerShape(22.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(18.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(54.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .background(StoryPurpleContainer),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = module.emoji, fontSize = 28.sp)
                }

                Spacer(modifier = Modifier.width(14.dp))

                Column(modifier = Modifier.weight(1f)) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = module.category.uppercase(),
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = FontWeight.Bold,
                            color = StoryPurplePrimary,
                            fontSize = 10.sp
                        )
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text("✨", fontSize = 12.sp)
                            Spacer(modifier = Modifier.width(3.dp))
                            Text(
                                text = "+${module.xpReward} XP",
                                style = MaterialTheme.typography.labelSmall,
                                fontWeight = FontWeight.Bold,
                                color = StoryGoldDark
                            )
                        }
                    }

                    Text(
                        text = module.title,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = module.description,
                style = MaterialTheme.typography.bodySmall,
                color = StoryTextSecondary,
                lineHeight = 18.sp
            )

            Spacer(modifier = Modifier.height(14.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "Progress: ${(module.progress * 100).toInt()}%",
                        style = MaterialTheme.typography.labelSmall,
                        color = StoryTextMuted
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    LinearProgressIndicator(
                        progress = { module.progress },
                        modifier = Modifier
                            .fillMaxWidth(0.85f)
                            .height(6.dp)
                            .clip(RoundedCornerShape(3.dp)),
                        color = StoryPurplePrimary,
                        trackColor = StoryPurpleContainer
                    )
                }

                Button(
                    onClick = onStart,
                    shape = RoundedCornerShape(16.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = StoryPurplePrimary),
                    contentPadding = PaddingValues(horizontal = 18.dp, vertical = 8.dp),
                    modifier = Modifier.testTag("start_module_${module.id}")
                ) {
                    Text("START", fontWeight = FontWeight.Bold, fontSize = 12.sp)
                }
            }
        }
    }
}
