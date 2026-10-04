package com.example.ui.components

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.model.RankLevel
import com.example.model.UserProfile
import com.example.ui.theme.*
import com.example.viewmodel.BottomTab
import com.example.viewmodel.RewardCelebration

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StoryLandTopBar(
    userProfile: UserProfile,
    onMenuClick: () -> Unit,
    onProfileClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    TopAppBar(
        modifier = modifier
            .fillMaxWidth()
            .testTag("storyland_top_bar"),
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.background,
            titleContentColor = MaterialTheme.colorScheme.onBackground
        ),
        navigationIcon = {
            IconButton(
                onClick = onMenuClick,
                modifier = Modifier
                    .minimumInteractiveComponentSize()
                    .testTag("top_menu_button")
            ) {
                Icon(
                    imageVector = Icons.Default.Menu,
                    contentDescription = "Open Navigation Menu",
                    tint = StoryPurplePrimary
                )
            }
        },
        title = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(32.dp)
                        .clip(CircleShape)
                        .background(
                            Brush.linearGradient(
                                listOf(StoryPurplePrimary, StoryBlueSecondary)
                            )
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Text("✨", fontSize = 16.sp)
                }
                Column {
                    Text(
                        text = "StoryLand",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = StoryPurplePrimary
                    )
                    Text(
                        text = "Adventure",
                        style = MaterialTheme.typography.labelSmall,
                        color = StoryBlueSecondary,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
        },
        actions = {
            // XP & Star pill
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .clip(RoundedCornerShape(20.dp))
                    .background(StoryPurpleContainer)
                    .padding(horizontal = 10.dp, vertical = 5.dp)
            ) {
                Text("⭐", fontSize = 12.sp)
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = "${userProfile.stars}",
                    style = MaterialTheme.typography.labelMedium,
                    fontWeight = FontWeight.Bold,
                    color = StoryPurpleDark
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text("✨", fontSize = 12.sp)
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = "${userProfile.xp} XP",
                    style = MaterialTheme.typography.labelMedium,
                    fontWeight = FontWeight.Bold,
                    color = StoryPurpleDark
                )
            }

            Spacer(modifier = Modifier.width(6.dp))

            // Profile avatar button
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(StoryGoldLight)
                    .border(2.dp, StoryGoldAccent, CircleShape)
                    .clickable { onProfileClick() }
                    .testTag("top_profile_button"),
                contentAlignment = Alignment.Center
            ) {
                Text(text = userProfile.avatarEmoji, fontSize = 20.sp)
            }
            Spacer(modifier = Modifier.width(8.dp))
        }
    )
}

data class BottomNavItem(
    val tab: BottomTab,
    val label: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val testTag: String
)

@Composable
fun StoryLandBottomNav(
    selectedTab: BottomTab,
    onTabSelected: (BottomTab) -> Unit,
    modifier: Modifier = Modifier
) {
    val items = listOf(
        BottomNavItem(
            tab = BottomTab.HOME,
            label = "Home",
            selectedIcon = Icons.Filled.Home,
            unselectedIcon = Icons.Outlined.Home,
            testTag = "nav_home"
        ),
        BottomNavItem(
            tab = BottomTab.PRACTICE,
            label = "Practice",
            selectedIcon = Icons.Filled.MenuBook,
            unselectedIcon = Icons.Outlined.MenuBook,
            testTag = "nav_practice"
        ),
        BottomNavItem(
            tab = BottomTab.QUIZ,
            label = "Quiz",
            selectedIcon = Icons.Filled.EmojiEvents,
            unselectedIcon = Icons.Outlined.EmojiEvents,
            testTag = "nav_quiz"
        ),
        BottomNavItem(
            tab = BottomTab.ABOUT_US,
            label = "About",
            selectedIcon = Icons.Filled.Info,
            unselectedIcon = Icons.Outlined.Info,
            testTag = "nav_about"
        )
    )

    NavigationBar(
        modifier = modifier
            .windowInsetsPadding(WindowInsets.navigationBars)
            .shadow(12.dp)
            .testTag("storyland_bottom_navigation"),
        containerColor = MaterialTheme.colorScheme.surface,
        tonalElevation = 8.dp
    ) {
        items.forEach { item ->
            val isSelected = selectedTab == item.tab
            NavigationBarItem(
                selected = isSelected,
                onClick = { onTabSelected(item.tab) },
                icon = {
                    Icon(
                        imageVector = if (isSelected) item.selectedIcon else item.unselectedIcon,
                        contentDescription = item.label,
                        modifier = Modifier.size(24.dp)
                    )
                },
                label = {
                    Text(
                        text = item.label,
                        style = MaterialTheme.typography.labelMedium,
                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium
                    )
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = StoryPurplePrimary,
                    selectedTextColor = StoryPurplePrimary,
                    indicatorColor = StoryPurpleContainer,
                    unselectedIconColor = StoryTextMuted,
                    unselectedTextColor = StoryTextMuted
                ),
                modifier = Modifier.testTag(item.testTag)
            )
        }
    }
}

@Composable
fun CelebrationDialog(
    celebration: RewardCelebration,
    onDismiss: () -> Unit
) {
    Dialog(onDismissRequest = onDismiss) {
        Surface(
            shape = RoundedCornerShape(28.dp),
            color = MaterialTheme.colorScheme.surface,
            tonalElevation = 16.dp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .testTag("celebration_dialog")
        ) {
            Column(
                modifier = Modifier.padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Animated star badge
                Box(
                    modifier = Modifier
                        .size(80.dp)
                        .clip(CircleShape)
                        .background(
                            Brush.radialGradient(
                                listOf(StoryGoldLight, StoryGoldAccent)
                            )
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Text("🌟", fontSize = 42.sp)
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = celebration.title,
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.ExtraBold,
                    color = StoryPurplePrimary,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = celebration.message,
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Center,
                    color = StoryTextSecondary
                )

                Spacer(modifier = Modifier.height(20.dp))

                // Rewards Banner
                Row(
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .clip(RoundedCornerShape(16.dp))
                        .background(StoryPurpleContainer)
                        .padding(horizontal = 20.dp, vertical = 12.dp)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text("⭐", fontSize = 20.sp)
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "+${celebration.starsEarned} Stars",
                            fontWeight = FontWeight.Bold,
                            color = StoryPurpleDark,
                            fontSize = 15.sp
                        )
                    }
                    Divider(
                        modifier = Modifier
                            .height(20.dp)
                            .width(1.dp),
                        color = StoryPurpleLight
                    )
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text("✨", fontSize = 20.sp)
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "+${celebration.xpEarned} XP",
                            fontWeight = FontWeight.Bold,
                            color = StoryPurpleDark,
                            fontSize = 15.sp
                        )
                    }
                }

                // If badge unlocked
                celebration.badgeUnlocked?.let { badge ->
                    Spacer(modifier = Modifier.height(16.dp))
                    Card(
                        colors = CardDefaults.cardColors(containerColor = StoryGoldLight),
                        shape = RoundedCornerShape(16.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(
                            modifier = Modifier.padding(12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(text = badge.iconEmoji, fontSize = 28.sp)
                            Spacer(modifier = Modifier.width(12.dp))
                            Column {
                                Text(
                                    text = "Badge Unlocked: ${badge.title}",
                                    fontWeight = FontWeight.Bold,
                                    color = StoryGoldDark,
                                    fontSize = 14.sp
                                )
                                Text(
                                    text = badge.description,
                                    color = StoryTextSecondary,
                                    fontSize = 12.sp
                                )
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                Button(
                    onClick = onDismiss,
                    shape = RoundedCornerShape(20.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = StoryPurplePrimary),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                        .testTag("celebration_continue_button")
                ) {
                    Text(
                        text = "FANTASTIC! CONTINUE",
                        fontWeight = FontWeight.Bold,
                        fontSize = 15.sp
                    )
                }
            }
        }
    }
}

@Composable
fun ParentGateDialog(
    onGatePassed: () -> Unit,
    onDismiss: () -> Unit
) {
    var num1 by remember { mutableIntStateOf((4..9).random()) }
    var num2 by remember { mutableIntStateOf((3..8).random()) }
    var answerInput by remember { mutableStateOf("") }
    var isError by remember { mutableStateOf(false) }

    val correctAnswer = num1 * num2

    Dialog(onDismissRequest = onDismiss) {
        Surface(
            shape = RoundedCornerShape(24.dp),
            color = MaterialTheme.colorScheme.surface,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .testTag("parent_gate_dialog")
        ) {
            Column(
                modifier = Modifier.padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "🔒 Grown-Ups Only",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = StoryPurplePrimary
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Please solve this quick problem to verify you are a parent or guardian:",
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Center,
                    color = StoryTextSecondary
                )
                Spacer(modifier = Modifier.height(16.dp))

                Card(
                    colors = CardDefaults.cardColors(containerColor = StoryCreamSurface),
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier.padding(8.dp)
                ) {
                    Text(
                        text = "$num1 × $num2 = ?",
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold,
                        color = StoryPurpleDark,
                        modifier = Modifier.padding(horizontal = 24.dp, vertical = 12.dp)
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                OutlinedTextField(
                    value = answerInput,
                    onValueChange = {
                        answerInput = it.filter { char -> char.isDigit() }
                        isError = false
                    },
                    label = { Text("Answer") },
                    singleLine = true,
                    isError = isError,
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("parent_gate_input")
                )

                if (isError) {
                    Text(
                        text = "Incorrect answer, please try again",
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier.padding(top = 4.dp)
                    )
                }

                Spacer(modifier = Modifier.height(20.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    OutlinedButton(
                        onClick = onDismiss,
                        modifier = Modifier.weight(1f)
                    ) {
                        Text("Cancel")
                    }
                    Button(
                        onClick = {
                            if (answerInput.toIntOrNull() == correctAnswer) {
                                onGatePassed()
                            } else {
                                isError = true
                                answerInput = ""
                            }
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = StoryPurplePrimary),
                        modifier = Modifier.weight(1f)
                    ) {
                        Text("Unlock")
                    }
                }
            }
        }
    }
}
