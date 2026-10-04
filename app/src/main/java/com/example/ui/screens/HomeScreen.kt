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
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.RankLevel
import com.example.model.Story
import com.example.model.UserProfile
import com.example.ui.theme.*
import com.example.viewmodel.AppScreen
import com.example.viewmodel.StoryLandViewModel

@Composable
fun HomeScreen(
    viewModel: StoryLandViewModel,
    userProfile: UserProfile,
    onNavigate: (AppScreen) -> Unit,
    modifier: Modifier = Modifier
) {
    val currentRank = RankLevel.fromXp(userProfile.xp)
    val nextRank = when (currentRank) {
        RankLevel.READER -> RankLevel.STORY_EXPLORER
        RankLevel.STORY_EXPLORER -> RankLevel.WRITER
        RankLevel.WRITER -> RankLevel.STORY_CREATOR
        RankLevel.STORY_CREATOR -> RankLevel.STORY_MASTER
        RankLevel.STORY_MASTER -> RankLevel.STORY_MASTER
    }

    val rankProgress = if (nextRank == currentRank) 1.0f else {
        ((userProfile.xp - currentRank.minXp).toFloat() / (currentRank.maxXp - currentRank.minXp + 1)).coerceIn(0f, 1f)
    }

    val todayStory = viewModel.stories.find { it.id == "secret_door" } ?: viewModel.stories.first()
    val continueStory = viewModel.stories.find { it.id == userProfile.lastStoryId } ?: viewModel.stories.first()

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .testTag("home_screen_content"),
        contentPadding = PaddingValues(bottom = 96.dp)
    ) {
        // 1. HERO BANNER
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp)
                    .shadow(8.dp, RoundedCornerShape(28.dp))
                    .testTag("hero_adventure_banner"),
                shape = RoundedCornerShape(28.dp),
                colors = CardDefaults.cardColors(containerColor = Color.Transparent)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            Brush.linearGradient(
                                colors = listOf(
                                    Color(0xFF581C87),
                                    Color(0xFF7C3AED),
                                    Color(0xFF2563EB)
                                )
                            )
                        )
                        .padding(24.dp)
                ) {
                    Column {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Box(
                                    modifier = Modifier
                                        .size(36.dp)
                                        .clip(CircleShape)
                                        .background(Color.White.copy(alpha = 0.2f)),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text("🌟", fontSize = 18.sp)
                                }
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = "Welcome, ${userProfile.name}!",
                                    style = MaterialTheme.typography.titleMedium,
                                    color = Color.White,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                            // Streak Pill
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier
                                    .clip(RoundedCornerShape(16.dp))
                                    .background(Color.White.copy(alpha = 0.25f))
                                    .padding(horizontal = 10.dp, vertical = 4.dp)
                            ) {
                                Text("🔥", fontSize = 14.sp)
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = "${userProfile.readingStreakDays} Day Streak",
                                    color = Color.White,
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        Text(
                            text = "Which adventure will you discover today?",
                            style = MaterialTheme.typography.headlineSmall,
                            color = Color.White,
                            fontWeight = FontWeight.ExtraBold,
                            lineHeight = 28.sp
                        )

                        Spacer(modifier = Modifier.height(10.dp))

                        Text(
                            text = "Explore magical castles, rescue friendly dragons, or build your own fairy tale!",
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color.White.copy(alpha = 0.9f)
                        )

                        Spacer(modifier = Modifier.height(20.dp))

                        Button(
                            onClick = { onNavigate(AppScreen.DragonRescue) },
                            shape = RoundedCornerShape(20.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = StoryGoldAccent,
                                contentColor = Color.Black
                            ),
                            elevation = ButtonDefaults.buttonElevation(defaultElevation = 6.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(50.dp)
                                .testTag("hero_start_adventure_btn")
                        ) {
                            Text(
                                text = "🚀 START AN ADVENTURE",
                                fontWeight = FontWeight.ExtraBold,
                                fontSize = 15.sp,
                                letterSpacing = 0.5.sp
                            )
                        }
                    }
                }
            }
        }

        // 2. CONTINUE READING
        item {
            SectionTitle(title = "Continue Reading", icon = "📖")

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 4.dp)
                    .clip(RoundedCornerShape(20.dp))
                    .clickable {
                        onNavigate(AppScreen.Reader(continueStory.id, userProfile.lastStoryChapter))
                    }
                    .testTag("continue_reading_card"),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(64.dp)
                            .clip(RoundedCornerShape(16.dp))
                            .background(StoryPurpleContainer),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(continueStory.coverEmoji, fontSize = 34.sp)
                    }

                    Spacer(modifier = Modifier.width(16.dp))

                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = continueStory.title,
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Text(
                            text = "Chapter ${userProfile.lastStoryChapter} of ${continueStory.chapters.size}",
                            style = MaterialTheme.typography.bodySmall,
                            color = StoryTextSecondary
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        LinearProgressIndicator(
                            progress = { (userProfile.lastStoryChapter.toFloat() / continueStory.chapters.size).coerceIn(0f, 1f) },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(6.dp)
                                .clip(RoundedCornerShape(3.dp)),
                            color = StoryPurplePrimary,
                            trackColor = StoryPurpleContainer
                        )
                    }

                    Spacer(modifier = Modifier.width(12.dp))

                    FilledTonalButton(
                        onClick = {
                            onNavigate(AppScreen.Reader(continueStory.id, userProfile.lastStoryChapter))
                        },
                        shape = RoundedCornerShape(14.dp),
                        colors = ButtonDefaults.filledTonalButtonColors(
                            containerColor = StoryPurplePrimary,
                            contentColor = Color.White
                        ),
                        modifier = Modifier.testTag("continue_reading_btn")
                    ) {
                        Text("CONTINUE", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }

        // 3. TODAY'S STORY
        item {
            SectionTitle(title = "Today's Story", icon = "⭐")

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 4.dp)
                    .clip(RoundedCornerShape(22.dp))
                    .clickable { onNavigate(AppScreen.Reader(todayStory.id)) }
                    .testTag("todays_story_card"),
                shape = RoundedCornerShape(22.dp),
                colors = CardDefaults.cardColors(containerColor = StoryTealLight)
            ) {
                Row(
                    modifier = Modifier.padding(18.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(70.dp)
                            .clip(RoundedCornerShape(18.dp))
                            .background(Color.White),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(todayStory.coverEmoji, fontSize = 38.sp)
                    }

                    Spacer(modifier = Modifier.width(16.dp))

                    Column(modifier = Modifier.weight(1f)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = "DAILY PICK",
                                style = MaterialTheme.typography.labelSmall,
                                fontWeight = FontWeight.Bold,
                                color = StoryTeal
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "⏱️ ${todayStory.readingTimeMinutes} min",
                                style = MaterialTheme.typography.labelSmall,
                                color = StoryTextSecondary
                            )
                        }
                        Text(
                            text = todayStory.title,
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = StoryTextPrimary
                        )
                        Text(
                            text = "+${todayStory.xpReward} XP Reward",
                            style = MaterialTheme.typography.bodySmall,
                            fontWeight = FontWeight.SemiBold,
                            color = StoryPurpleDark
                        )
                    }

                    Button(
                        onClick = { onNavigate(AppScreen.Reader(todayStory.id)) },
                        colors = ButtonDefaults.buttonColors(containerColor = StoryTeal),
                        shape = RoundedCornerShape(16.dp),
                        modifier = Modifier.testTag("read_todays_story_btn")
                    ) {
                        Text("READ", fontWeight = FontWeight.Bold, fontSize = 13.sp)
                    }
                }
            }
        }

        // 4. FEATURED ADVENTURES
        item {
            SectionTitle(title = "Featured Adventures", icon = "🗺️")

            LazyRow(
                contentPadding = PaddingValues(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(14.dp),
                modifier = Modifier.padding(vertical = 4.dp)
            ) {
                item {
                    AdventureCard(
                        title = "Dragon Rescue",
                        subtitle = "Help friendly Pip the baby dragon",
                        emoji = "🐉",
                        bgColor = Color(0xFF10B981),
                        onClick = { onNavigate(AppScreen.DragonRescue) }
                    )
                }
                item {
                    AdventureCard(
                        title = "Pirate Story",
                        subtitle = "Sail with Captain Barnaby",
                        emoji = "🏴‍☠️",
                        bgColor = Color(0xFF0284C7),
                        onClick = { onNavigate(AppScreen.PirateStory) }
                    )
                }
                item {
                    AdventureCard(
                        title = "Magic Kingdom",
                        subtitle = "The Lost Crown of Solaria",
                        emoji = "🏰",
                        bgColor = Color(0xFF8B5CF6),
                        onClick = { onNavigate(AppScreen.HeroAdventure) }
                    )
                }
                item {
                    AdventureCard(
                        title = "Mystery Island",
                        subtitle = "Search for secret clues",
                        emoji = "🏝️",
                        bgColor = Color(0xFFD97706),
                        onClick = { onNavigate(AppScreen.MysteryIsland) }
                    )
                }
                item {
                    AdventureCard(
                        title = "Superhero Mission",
                        subtitle = "Save Tech City with Spark",
                        emoji = "🦸",
                        bgColor = Color(0xFFEC4899),
                        onClick = { onNavigate(AppScreen.SuperheroMission) }
                    )
                }
            }
        }

        // 5. QUICK CREATE
        item {
            SectionTitle(title = "Quick Create", icon = "🎨")

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                QuickCreateTile(
                    title = "Create Story",
                    emoji = "✍️",
                    bg = StoryPurpleContainer,
                    modifier = Modifier.weight(1f),
                    onClick = { onNavigate(AppScreen.CreateStory) }
                )
                QuickCreateTile(
                    title = "Fairy Tale",
                    emoji = "✨",
                    bg = StoryGoldLight,
                    modifier = Modifier.weight(1f),
                    onClick = { onNavigate(AppScreen.FairyTaleBuilder) }
                )
                QuickCreateTile(
                    title = "Character",
                    emoji = "🧑‍🎨",
                    bg = StoryPinkLight,
                    modifier = Modifier.weight(1f),
                    onClick = { onNavigate(AppScreen.CharacterCreator) }
                )
                QuickCreateTile(
                    title = "Record",
                    emoji = "🎙️",
                    bg = StoryTealLight,
                    modifier = Modifier.weight(1f),
                    onClick = { onNavigate(AppScreen.VoiceRecording) }
                )
            }
        }

        // 6. READING PROGRESS
        item {
            SectionTitle(title = "Your Reading Journey", icon = "📈")

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 6.dp)
                    .testTag("reading_progress_card"),
                shape = RoundedCornerShape(22.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(modifier = Modifier.padding(18.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(currentRank.badgeIcon, fontSize = 20.sp)
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = currentRank.title,
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.Bold,
                                    color = StoryPurplePrimary
                                )
                            }
                            Text(
                                text = "Next rank: ${nextRank.title}",
                                style = MaterialTheme.typography.bodySmall,
                                color = StoryTextSecondary
                            )
                        }
                        Text(
                            text = "${userProfile.xp} / ${if (nextRank == currentRank) currentRank.maxXp else currentRank.maxXp + 1} XP",
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.Bold,
                            color = StoryPurpleDark
                        )
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    LinearProgressIndicator(
                        progress = { rankProgress },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(10.dp)
                            .clip(RoundedCornerShape(5.dp)),
                        color = StoryPurplePrimary,
                        trackColor = StoryPurpleContainer
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceAround
                    ) {
                        StatItem(label = "Stories Read", value = "${userProfile.storiesReadCount}", emoji = "📚")
                        StatItem(label = "Created", value = "${userProfile.storiesCreatedCount}", emoji = "✍️")
                        StatItem(label = "Quizzes", value = "${userProfile.quizzesCompletedCount}", emoji = "🏆")
                        StatItem(label = "Streak", value = "${userProfile.readingStreakDays}d", emoji = "🔥")
                    }
                }
            }
        }

        // 7. RECENT ACHIEVEMENTS
        item {
            SectionTitle(title = "Recent Achievements", icon = "🏆")

            LazyRow(
                contentPadding = PaddingValues(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                modifier = Modifier.padding(vertical = 4.dp)
            ) {
                items(viewModel.achievements) { badge ->
                    val isUnlocked = userProfile.unlockedBadgeIds.contains(badge.id)
                    BadgeTile(badge.title, badge.iconEmoji, isUnlocked)
                }
            }
        }
    }
}

@Composable
fun SectionTitle(title: String, icon: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 20.dp, end = 20.dp, top = 20.dp, bottom = 8.dp)
    ) {
        Text(text = icon, fontSize = 18.sp)
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
fun AdventureCard(
    title: String,
    subtitle: String,
    emoji: String,
    bgColor: Color,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .width(180.dp)
            .height(210.dp)
            .clip(RoundedCornerShape(22.dp))
            .clickable { onClick() }
            .testTag("adventure_card_$title"),
        shape = RoundedCornerShape(22.dp),
        colors = CardDefaults.cardColors(containerColor = bgColor)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Box(
                modifier = Modifier
                    .size(54.dp)
                    .clip(CircleShape)
                    .background(Color.White.copy(alpha = 0.25f)),
                contentAlignment = Alignment.Center
            ) {
                Text(text = emoji, fontSize = 28.sp)
            }

            Column {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = subtitle,
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.White.copy(alpha = 0.9f),
                    maxLines = 2
                )
                Spacer(modifier = Modifier.height(10.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "PLAY",
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.ExtraBold,
                        color = Color.White
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Icon(
                        imageVector = Icons.Default.ArrowForward,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(14.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun QuickCreateTile(
    title: String,
    emoji: String,
    bg: Color,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Card(
        modifier = modifier
            .height(86.dp)
            .clip(RoundedCornerShape(18.dp))
            .clickable { onClick() },
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = bg)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(emoji, fontSize = 24.sp)
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = title,
                style = MaterialTheme.typography.labelSmall,
                fontWeight = FontWeight.Bold,
                color = StoryTextPrimary,
                fontSize = 11.sp
            )
        }
    }
}

@Composable
fun StatItem(label: String, value: String, emoji: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(emoji, fontSize = 18.sp)
        Spacer(modifier = Modifier.height(2.dp))
        Text(
            text = value,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            color = StoryPurplePrimary
        )
        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall,
            color = StoryTextSecondary,
            fontSize = 10.sp
        )
    }
}

@Composable
fun BadgeTile(title: String, emoji: String, isUnlocked: Boolean) {
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isUnlocked) StoryGoldLight else StoryCreamSurface
        ),
        modifier = Modifier.width(100.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = if (isUnlocked) emoji else "🔒",
                fontSize = 28.sp
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = title,
                style = MaterialTheme.typography.labelSmall,
                fontWeight = FontWeight.Bold,
                color = if (isUnlocked) StoryGoldDark else StoryTextMuted,
                fontSize = 10.sp,
                maxLines = 1
            )
        }
    }
}
