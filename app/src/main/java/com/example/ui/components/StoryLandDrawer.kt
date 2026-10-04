package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.RankLevel
import com.example.model.UserProfile
import com.example.ui.theme.*
import com.example.viewmodel.AppScreen

@Composable
fun StoryLandDrawerContent(
    userProfile: UserProfile,
    onNavigate: (AppScreen) -> Unit,
    onCloseDrawer: () -> Unit,
    onOpenParentGate: () -> Unit
) {
    val currentRank = RankLevel.fromXp(userProfile.xp)

    ModalDrawerSheet(
        modifier = Modifier
            .widthIn(max = 330.dp)
            .fillMaxHeight()
            .testTag("storyland_drawer_sheet"),
        drawerContainerColor = MaterialTheme.colorScheme.surface,
        drawerTonalElevation = 6.dp
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            contentPadding = PaddingValues(top = 16.dp, bottom = 32.dp)
        ) {
            // Profile & Rank Header
            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp)
                        .clickable {
                            onCloseDrawer()
                            onNavigate(AppScreen.ProfileScreen)
                        },
                    shape = RoundedCornerShape(24.dp),
                    colors = CardDefaults.cardColors(containerColor = StoryPurpleContainer)
                ) {
                    Row(
                        modifier = Modifier.padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(56.dp)
                                .clip(CircleShape)
                                .background(StoryGoldLight),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(text = userProfile.avatarEmoji, fontSize = 28.sp)
                        }
                        Spacer(modifier = Modifier.width(14.dp))
                        Column {
                            Text(
                                text = userProfile.name,
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = StoryPurpleDark
                            )
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(text = currentRank.badgeIcon, fontSize = 14.sp)
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = currentRank.title,
                                    style = MaterialTheme.typography.labelMedium,
                                    fontWeight = FontWeight.SemiBold,
                                    color = StoryPurplePrimary
                                )
                            }
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "✨ ${userProfile.xp} XP  •  ⭐ ${userProfile.stars} Stars",
                                style = MaterialTheme.typography.bodySmall,
                                color = StoryTextSecondary
                            )
                        }
                    }
                }
            }

            // 1. STORIES
            item {
                DrawerSectionHeader(title = "STORIES", icon = "📚")
            }
            item {
                DrawerMenuItem("Story Library", "📖", "All magical adventures") {
                    onCloseDrawer()
                    onNavigate(AppScreen.StoryLibrary)
                }
                DrawerMenuItem("Bedtime Stories", "🌙", "Calm tales & gentle music") {
                    onCloseDrawer()
                    onNavigate(AppScreen.BedtimeLibrary)
                }
                DrawerMenuItem("Fantasy Stories", "🏰", "Dragons, wizards & kingdoms") {
                    onCloseDrawer()
                    onNavigate(AppScreen.StoryLibrary)
                }
                DrawerMenuItem("Adventure Stories", "⛵", "Pirates, explorers & quests") {
                    onCloseDrawer()
                    onNavigate(AppScreen.StoryLibrary)
                }
                DrawerMenuItem("Mystery Stories", "🔍", "Clues, secrets & detective cases") {
                    onCloseDrawer()
                    onNavigate(AppScreen.DetectiveKids)
                }
                DrawerMenuItem("Hero Stories", "🦸", "City missions & brave champions") {
                    onCloseDrawer()
                    onNavigate(AppScreen.SuperheroMission)
                }
            }

            // 2. CREATE
            item {
                DrawerSectionHeader(title = "CREATE", icon = "🎨")
            }
            item {
                DrawerMenuItem("Create A Story", "✍️", "Guided story maker") {
                    onCloseDrawer()
                    onNavigate(AppScreen.CreateStory)
                }
                DrawerMenuItem("Fairy Tale Builder", "✨", "Pick heroes, worlds & problems") {
                    onCloseDrawer()
                    onNavigate(AppScreen.FairyTaleBuilder)
                }
                DrawerMenuItem("Character Creator", "🧑‍🎨", "Design heroes & outfits") {
                    onCloseDrawer()
                    onNavigate(AppScreen.CharacterCreator)
                }
                DrawerMenuItem("Voice Storytelling", "🎙️", "Narrate your own story") {
                    onCloseDrawer()
                    onNavigate(AppScreen.VoiceRecording)
                }
            }

            // 3. ADVENTURE
            item {
                DrawerSectionHeader(title = "ADVENTURE", icon = "🗺️")
            }
            item {
                DrawerMenuItem("Hero Adventure", "🛡️", "Choose your own story path") {
                    onCloseDrawer()
                    onNavigate(AppScreen.HeroAdventure)
                }
                DrawerMenuItem("Magic Kingdom", "🏰", "Explore fantasy realm locations") {
                    onCloseDrawer()
                    onNavigate(AppScreen.StoryLibrary)
                }
                DrawerMenuItem("Dragon Rescue", "🐉", "Help Pip the baby dragon") {
                    onCloseDrawer()
                    onNavigate(AppScreen.DragonRescue)
                }
                DrawerMenuItem("Pirate Story", "🏴‍☠️", "Sail the Star Compass sea") {
                    onCloseDrawer()
                    onNavigate(AppScreen.PirateStory)
                }
                DrawerMenuItem("Superhero Mission", "⚡", "Save Tech City with Spark") {
                    onCloseDrawer()
                    onNavigate(AppScreen.SuperheroMission)
                }
                DrawerMenuItem("Detective Kids", "🔎", "Solve the Museum Clock mystery") {
                    onCloseDrawer()
                    onNavigate(AppScreen.DetectiveKids)
                }
                DrawerMenuItem("Mystery Island", "🏝️", "Collect secret clues & logs") {
                    onCloseDrawer()
                    onNavigate(AppScreen.MysteryIsland)
                }
            }

            // 4. READ & LEARN
            item {
                DrawerSectionHeader(title = "READ & LEARN", icon = "🧠")
            }
            item {
                DrawerMenuItem("Read Along", "🎧", "Sentence-by-sentence narration") {
                    onCloseDrawer()
                    onNavigate(AppScreen.Reader("secret_door"))
                }
                DrawerMenuItem("Story Sequencing", "🧩", "Order the story cards game") {
                    onCloseDrawer()
                    onNavigate(AppScreen.StorySequencing)
                }
            }

            // 5. COLLECTION
            item {
                DrawerSectionHeader(title = "COLLECTION", icon = "⭐")
            }
            item {
                DrawerMenuItem("My Stories", "📁", "Created, saved & favorite stories") {
                    onCloseDrawer()
                    onNavigate(AppScreen.MyCollection)
                }
                DrawerMenuItem("Character Collection", "🦊", "Unlocked friends gallery") {
                    onCloseDrawer()
                    onNavigate(AppScreen.MyCollection)
                }
                DrawerMenuItem("Achievement Badges", "🏆", "Collectible story trophies") {
                    onCloseDrawer()
                    onNavigate(AppScreen.MyCollection)
                }
            }

            // 6. PROGRESS
            item {
                DrawerSectionHeader(title = "PROGRESS", icon = "📈")
            }
            item {
                DrawerMenuItem("My Progress", "📊", "Reading stats, XP & streak") {
                    onCloseDrawer()
                    onNavigate(AppScreen.ProgressOverview)
                }
            }

            // 7. SETTINGS & PARENT AREA
            item {
                DrawerSectionHeader(title = "SETTINGS", icon = "⚙️")
            }
            item {
                DrawerMenuItem("Settings", "🛠️", "Sound, text size & narration") {
                    onCloseDrawer()
                    onNavigate(AppScreen.SettingsScreen)
                }
                DrawerMenuItem("Parent Area", "🔒", "Protected guardian controls") {
                    onCloseDrawer()
                    onOpenParentGate()
                }
            }
        }
    }
}

@Composable
fun DrawerSectionHeader(title: String, icon: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp, bottom = 8.dp, start = 4.dp)
    ) {
        Text(text = icon, fontSize = 14.sp)
        Spacer(modifier = Modifier.width(6.dp))
        Text(
            text = title,
            style = MaterialTheme.typography.labelSmall,
            fontWeight = FontWeight.Bold,
            color = StoryPurplePrimary,
            letterSpacing = 1.sp
        )
    }
}

@Composable
fun DrawerMenuItem(
    title: String,
    emoji: String,
    subtitle: String,
    onClick: () -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 3.dp)
            .clip(RoundedCornerShape(14.dp))
            .clickable { onClick() },
        color = Color.Transparent
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(36.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(StoryPurpleContainer),
                contentAlignment = Alignment.Center
            ) {
                Text(text = emoji, fontSize = 18.sp)
            }
            Spacer(modifier = Modifier.width(12.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = subtitle,
                    style = MaterialTheme.typography.bodySmall,
                    color = StoryTextMuted,
                    fontSize = 11.sp,
                    maxLines = 1
                )
            }
            Icon(
                imageVector = Icons.Default.ChevronRight,
                contentDescription = null,
                tint = StoryTextMuted,
                modifier = Modifier.size(18.dp)
            )
        }
    }
}
