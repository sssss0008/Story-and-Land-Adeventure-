package com.example.ui.screens

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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.RankLevel
import com.example.model.UserProfile
import com.example.ui.components.ParentGateDialog
import com.example.ui.theme.*
import com.example.viewmodel.AppScreen
import com.example.viewmodel.StoryLandViewModel

// 1. PROFILE SCREEN
@Composable
fun ProfileScreen(
    viewModel: StoryLandViewModel,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    val userProfile by viewModel.userProfile.collectAsState()
    val currentRank = RankLevel.fromXp(userProfile.xp)

    val availableAvatars = listOf(
        "🦁" to "Lion Explorer",
        "🦊" to "Fox Storyteller",
        "🦉" to "Wise Owl",
        "🐉" to "Baby Dragon",
        "🦄" to "Starlight Unicorn",
        "🐼" to "Cozy Panda",
        "🐨" to "Koala Reader",
        "🧙" to "Little Wizard"
    )

    var childNameInput by remember { mutableStateOf(userProfile.name) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .windowInsetsPadding(WindowInsets.statusBars)
            .testTag("profile_screen")
    ) {
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
                text = "Story Explorer Profile",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = StoryPurplePrimary
            )
        }

        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            contentPadding = PaddingValues(bottom = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Profile Card Header
            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    shape = RoundedCornerShape(26.dp),
                    colors = CardDefaults.cardColors(containerColor = StoryPurpleContainer)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Box(
                            modifier = Modifier
                                .size(90.dp)
                                .clip(CircleShape)
                                .background(StoryGoldLight)
                                .border(3.dp, StoryGoldAccent, CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(userProfile.avatarEmoji, fontSize = 48.sp)
                        }

                        Spacer(modifier = Modifier.height(14.dp))

                        Text(
                            text = userProfile.name,
                            style = MaterialTheme.typography.headlineSmall,
                            fontWeight = FontWeight.Bold,
                            color = StoryPurpleDark
                        )

                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(currentRank.badgeIcon, fontSize = 16.sp)
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = "Rank: ${currentRank.title}",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.SemiBold,
                                color = StoryPurplePrimary
                            )
                        }
                    }
                }
            }

            // Edit Name
            item {
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = childNameInput,
                    onValueChange = {
                        childNameInput = it
                        viewModel.updateProfileNameAndAvatar(it, userProfile.avatarEmoji, userProfile.avatarId)
                    },
                    label = { Text("Your Explorer Name") },
                    singleLine = true,
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("profile_name_input")
                )
                Spacer(modifier = Modifier.height(16.dp))
            }

            // Choose Avatar
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Choose Your Avatar",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    contentPadding = PaddingValues(vertical = 4.dp)
                ) {
                    items(availableAvatars) { (emoji, label) ->
                        val isSelected = userProfile.avatarEmoji == emoji
                        Box(
                            modifier = Modifier
                                .size(64.dp)
                                .clip(CircleShape)
                                .background(if (isSelected) StoryGoldLight else StoryCreamSurface)
                                .border(
                                    width = if (isSelected) 3.dp else 1.dp,
                                    color = if (isSelected) StoryGoldAccent else StoryDivider,
                                    shape = CircleShape
                                )
                                .clickable {
                                    viewModel.updateProfileNameAndAvatar(userProfile.name, emoji, label)
                                },
                            contentAlignment = Alignment.Center
                        ) {
                            Text(emoji, fontSize = 32.sp)
                        }
                    }
                }
                Spacer(modifier = Modifier.height(20.dp))
            }

            // Statistics Summary
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(22.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                ) {
                    Column(modifier = Modifier.padding(18.dp)) {
                        Text(
                            text = "Reading Achievements & Stats",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceAround
                        ) {
                            StatItem("Stories Read", "${userProfile.storiesReadCount}", "📚")
                            StatItem("Stories Created", "${userProfile.storiesCreatedCount}", "✍️")
                            StatItem("Quizzes Passed", "${userProfile.quizzesCompletedCount}", "🏆")
                            StatItem("Reading Mins", "${userProfile.totalReadingMinutes}", "⏱️")
                        }
                    }
                }
            }
        }
    }
}

// 2. SETTINGS SCREEN
@Composable
fun SettingsScreen(
    viewModel: StoryLandViewModel,
    onBack: () -> Unit,
    onOpenParentGate: () -> Unit,
    modifier: Modifier = Modifier
) {
    val settings by viewModel.appSettings.collectAsState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .windowInsetsPadding(WindowInsets.statusBars)
            .testTag("settings_screen")
    ) {
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
                text = "StoryLand Settings",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = StoryPurplePrimary
            )
        }

        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            contentPadding = PaddingValues(bottom = 24.dp)
        ) {
            item {
                SettingSwitchItem(
                    title = "Sound Effects",
                    subtitle = "Play magical chimes, button clicks and page flips",
                    icon = "🔔",
                    checked = settings.soundEffects,
                    onCheckedChange = { viewModel.updateSettings(settings.copy(soundEffects = it)) }
                )
                SettingSwitchItem(
                    title = "Story Narration Audio",
                    subtitle = "Read aloud text for Read Along mode",
                    icon = "🎙️",
                    checked = settings.narrationAudio,
                    onCheckedChange = { viewModel.updateSettings(settings.copy(narrationAudio = it)) }
                )
                SettingSwitchItem(
                    title = "Background Music & Ambience",
                    subtitle = "Gentle melodies during bedtime and quests",
                    icon = "🎵",
                    checked = settings.backgroundMusic,
                    onCheckedChange = { viewModel.updateSettings(settings.copy(backgroundMusic = it)) }
                )
                SettingSwitchItem(
                    title = "Playful Animations",
                    subtitle = "Floating magic stars and celebration effects",
                    icon = "✨",
                    checked = settings.animationsEnabled,
                    onCheckedChange = { viewModel.updateSettings(settings.copy(animationsEnabled = it)) }
                )
            }

            // Parent Area Shortcut
            item {
                Spacer(modifier = Modifier.height(16.dp))
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(20.dp))
                        .clickable { onOpenParentGate() },
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(containerColor = StoryPurpleContainer)
                ) {
                    Row(
                        modifier = Modifier.padding(18.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("🔒", fontSize = 28.sp)
                        Spacer(modifier = Modifier.width(14.dp))
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = "Grown-Up / Parent Area",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = StoryPurpleDark
                            )
                            Text(
                                text = "Reading metrics, progress reports & privacy controls",
                                style = MaterialTheme.typography.bodySmall,
                                color = StoryTextSecondary
                            )
                        }
                        Icon(Icons.Default.ChevronRight, contentDescription = null, tint = StoryPurplePrimary)
                    }
                }
            }
        }
    }
}

@Composable
fun SettingSwitchItem(
    title: String,
    subtitle: String,
    icon: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 5.dp),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(icon, fontSize = 24.sp)
            Spacer(modifier = Modifier.width(14.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold
                )
                Text(
                    text = subtitle,
                    style = MaterialTheme.typography.bodySmall,
                    color = StoryTextSecondary
                )
            }
            Switch(
                checked = checked,
                onCheckedChange = onCheckedChange,
                colors = SwitchDefaults.colors(
                    checkedThumbColor = StoryPurplePrimary,
                    checkedTrackColor = StoryPurpleContainer
                )
            )
        }
    }
}

// 3. PARENT AREA SCREEN (Protected)
@Composable
fun ParentAreaScreen(
    viewModel: StoryLandViewModel,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    val userProfile by viewModel.userProfile.collectAsState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .windowInsetsPadding(WindowInsets.statusBars)
            .testTag("parent_area_screen")
    ) {
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
                text = "Parent & Educator Dashboard",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = StoryPurplePrimary
            )
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
                    shape = RoundedCornerShape(22.dp),
                    colors = CardDefaults.cardColors(containerColor = StoryTealLight)
                ) {
                    Column(modifier = Modifier.padding(18.dp)) {
                        Text(
                            text = "📊 Child Reading Insights",
                            fontWeight = FontWeight.Bold,
                            color = StoryTeal,
                            fontSize = 16.sp
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "${userProfile.name} is developing fantastic reading habits and creative writing confidence!",
                            style = MaterialTheme.typography.bodyMedium,
                            color = StoryTextPrimary
                        )
                    }
                }
            }

            item {
                Spacer(modifier = Modifier.height(8.dp))
                ParentStatRow("Total Reading Time", "${userProfile.totalReadingMinutes} minutes", "⏱️")
                ParentStatRow("Stories Completed", "${userProfile.storiesReadCount} stories", "📖")
                ParentStatRow("Comprehension Quizzes", "${userProfile.quizzesCompletedCount} quizzes", "🏆")
                ParentStatRow("Creative Stories Built", "${userProfile.storiesCreatedCount} tales", "✍️")
                ParentStatRow("Current Reading Streak", "${userProfile.readingStreakDays} consecutive days", "🔥")
            }

            item {
                Spacer(modifier = Modifier.height(16.dp))
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
                ) {
                    Column(modifier = Modifier.padding(18.dp)) {
                        Text(
                            text = "🛡️ Child Safety & Privacy Policy",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = "StoryLand Adventure contains no third-party advertisements, no behavioral tracking, and no in-app purchases. All created content, audio recordings, and reading statistics are stored exclusively offline on your device.",
                            style = MaterialTheme.typography.bodySmall,
                            color = StoryTextSecondary,
                            lineHeight = 20.sp
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun ParentStatRow(label: String, value: String, emoji: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(emoji, fontSize = 22.sp)
            Spacer(modifier = Modifier.width(14.dp))
            Text(
                text = label,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.weight(1f)
            )
            Text(
                text = value,
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Bold,
                color = StoryPurplePrimary
            )
        }
    }
}

// 4. CREATE A STORY SCREEN (Guided Prompts)
@Composable
fun CreateStoryScreen(
    viewModel: StoryLandViewModel,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    var storyTitle by remember { mutableStateOf("") }
    var heroDescription by remember { mutableStateOf("A curious hedgehog named Bramble") }
    var settingDescription by remember { mutableStateOf("In a miniature village inside a hollow apple tree") }
    var problemDescription by remember { mutableStateOf("The village musical bells were blown away by autumn wind") }
    var solutionDescription by remember { mutableStateOf("Bramble built a glider kite and retrieved every chime") }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .windowInsetsPadding(WindowInsets.statusBars)
            .testTag("create_story_screen")
    ) {
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
                text = "Guided Story Writing Studio",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = StoryPurplePrimary
            )
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
                            text = "Answer each guided prompt below. StoryLand will assemble them into a beautiful storybook!",
                            style = MaterialTheme.typography.bodyMedium,
                            color = StoryPurpleDark
                        )
                    }
                }
            }

            item {
                OutlinedTextField(
                    value = storyTitle,
                    onValueChange = { storyTitle = it },
                    label = { Text("Story Title") },
                    placeholder = { Text("The Adventure of Bramble") },
                    singleLine = true,
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 6.dp)
                )

                OutlinedTextField(
                    value = heroDescription,
                    onValueChange = { heroDescription = it },
                    label = { Text("1. Who is your hero?") },
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 6.dp)
                )

                OutlinedTextField(
                    value = settingDescription,
                    onValueChange = { settingDescription = it },
                    label = { Text("2. Where does the story happen?") },
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 6.dp)
                )

                OutlinedTextField(
                    value = problemDescription,
                    onValueChange = { problemDescription = it },
                    label = { Text("3. What problem does the hero face?") },
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 6.dp)
                )

                OutlinedTextField(
                    value = solutionDescription,
                    onValueChange = { solutionDescription = it },
                    label = { Text("4. How does the hero solve it?") },
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 6.dp)
                )

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = {
                        viewModel.addXpAndStars(40, 10)
                        viewModel.soundManager.playCelebration()
                        onBack()
                    },
                    shape = RoundedCornerShape(20.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = StoryPurplePrimary),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp)
                ) {
                    Text("🌟 PUBLISH MY STORY (+40 XP)", fontWeight = FontWeight.Bold, fontSize = 15.sp)
                }
            }
        }
    }
}
