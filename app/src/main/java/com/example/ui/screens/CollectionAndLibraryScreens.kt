package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
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
import com.example.model.Story
import com.example.model.StoryCategory
import com.example.ui.theme.*
import com.example.viewmodel.AppScreen
import com.example.viewmodel.StoryLandViewModel

// 1. STORY LIBRARY SCREEN
@Composable
fun StoryLibraryScreen(
    viewModel: StoryLandViewModel,
    onNavigate: (AppScreen) -> Unit,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    var selectedCategory by remember { mutableStateOf<StoryCategory?>(null) }
    var searchQuery by remember { mutableStateOf("") }

    val filteredStories = viewModel.stories.filter { story ->
        val matchesCategory = selectedCategory == null || story.category == selectedCategory
        val matchesSearch = searchQuery.isBlank() || story.title.contains(searchQuery, ignoreCase = true) || story.subtitle.contains(searchQuery, ignoreCase = true)
        matchesCategory && matchesSearch
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .windowInsetsPadding(WindowInsets.statusBars)
            .testTag("story_library_screen")
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
                text = "Storybook Library",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = StoryPurplePrimary
            )
        }

        // Search bar
        OutlinedTextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            placeholder = { Text("Search stories, heroes, worlds...") },
            leadingIcon = { Icon(Icons.Default.Search, contentDescription = null, tint = StoryPurplePrimary) },
            singleLine = true,
            shape = RoundedCornerShape(18.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 4.dp)
        )

        // Categories
        LazyRow(
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            item {
                FilterChip(
                    selected = selectedCategory == null,
                    onClick = { selectedCategory = null },
                    label = { Text("All Stories") },
                    shape = RoundedCornerShape(16.dp)
                )
            }
            items(StoryCategory.entries) { cat ->
                FilterChip(
                    selected = selectedCategory == cat,
                    onClick = { selectedCategory = cat },
                    label = { Text("${cat.emoji} ${cat.displayName}") },
                    shape = RoundedCornerShape(16.dp)
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
            items(filteredStories) { story ->
                StoryBookCard(story = story, onRead = { onNavigate(AppScreen.Reader(story.id)) })
            }
        }
    }
}

@Composable
fun StoryBookCard(
    story: Story,
    onRead: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp)
            .clip(RoundedCornerShape(20.dp))
            .clickable { onRead() },
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(64.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(Color(story.primaryColorHex).copy(alpha = 0.15f)),
                contentAlignment = Alignment.Center
            ) {
                Text(story.coverEmoji, fontSize = 34.sp)
            }
            Spacer(modifier = Modifier.width(14.dp))
            Column(modifier = Modifier.weight(1f)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = story.category.displayName.uppercase(),
                        style = MaterialTheme.typography.labelSmall,
                        color = Color(story.primaryColorHex),
                        fontWeight = FontWeight.Bold,
                        fontSize = 10.sp
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "⏱️ ${story.readingTimeMinutes} min",
                        style = MaterialTheme.typography.labelSmall,
                        color = StoryTextSecondary
                    )
                }
                Text(
                    text = story.title,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = "${story.chapters.size} Chapters • +${story.xpReward} XP",
                    style = MaterialTheme.typography.bodySmall,
                    color = StoryTextSecondary
                )
            }
            Button(
                onClick = onRead,
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(story.primaryColorHex))
            ) {
                Text("READ", fontWeight = FontWeight.Bold, fontSize = 12.sp)
            }
        }
    }
}

// 2. BEDTIME STORIES MODE
@Composable
fun BedtimeLibraryScreen(
    viewModel: StoryLandViewModel,
    onNavigate: (AppScreen) -> Unit,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    val bedtimeStories = viewModel.stories.filter { it.isBedtime || it.category == StoryCategory.BEDTIME }
    var sleepTimer by remember { mutableIntStateOf(15) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(BedtimeNightDark)
            .windowInsetsPadding(WindowInsets.statusBars)
            .testTag("bedtime_library_screen")
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onBack) {
                Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = BedtimeStarGlow)
            }
            Column {
                Text(
                    text = "🌙 Bedtime Storybook",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = BedtimeStarGlow
                )
                Text(
                    text = "Calming tales for sweet and peaceful dreams",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.White.copy(alpha = 0.7f)
                )
            }
        }

        // Sleep Timer Card
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = BedtimeSurface)
        ) {
            Row(
                modifier = Modifier.padding(16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text("⏰", fontSize = 24.sp)
                    Spacer(modifier = Modifier.width(10.dp))
                    Column {
                        Text(
                            text = "Sleep Timer",
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                        Text(
                            text = if (sleepTimer > 0) "Stops audio after $sleepTimer min" else "Timer off",
                            style = MaterialTheme.typography.bodySmall,
                            color = Color.White.copy(alpha = 0.6f)
                        )
                    }
                }
                Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    listOf(10, 15, 30).forEach { mins ->
                        FilterChip(
                            selected = sleepTimer == mins,
                            onClick = { sleepTimer = if (sleepTimer == mins) 0 else mins },
                            label = { Text("${mins}m") },
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = BedtimeStarGlow,
                                selectedLabelColor = Color.Black
                            )
                        )
                    }
                }
            }
        }

        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            contentPadding = PaddingValues(bottom = 24.dp)
        ) {
            items(bedtimeStories) { story ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 6.dp)
                        .clip(RoundedCornerShape(20.dp))
                        .clickable { onNavigate(AppScreen.Reader(story.id)) },
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(containerColor = BedtimeSurface)
                ) {
                    Row(
                        modifier = Modifier.padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(story.coverEmoji, fontSize = 36.sp)
                        Spacer(modifier = Modifier.width(16.dp))
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = story.title,
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                            Text(
                                text = story.subtitle,
                                style = MaterialTheme.typography.bodySmall,
                                color = Color.White.copy(alpha = 0.7f)
                            )
                        }
                        IconButton(onClick = { onNavigate(AppScreen.Reader(story.id)) }) {
                            Icon(Icons.Default.PlayArrow, contentDescription = "Play", tint = BedtimeStarGlow)
                        }
                    }
                }
            }
        }
    }
}

// 3. MY COLLECTION SCREEN
@Composable
fun MyCollectionScreen(
    viewModel: StoryLandViewModel,
    onNavigate: (AppScreen) -> Unit,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    var selectedTab by remember { mutableIntStateOf(0) }
    val tabs = listOf("Created Stories", "Favorites", "Characters", "Badges")

    val createdStories by viewModel.createdStories.collectAsState()
    val userProfile by viewModel.userProfile.collectAsState()
    val favStories = viewModel.stories.filter { userProfile.favoriteStoryIds.contains(it.id) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .windowInsetsPadding(WindowInsets.statusBars)
            .testTag("my_collection_screen")
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
                text = "My Story Collection",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = StoryPurplePrimary
            )
        }

        ScrollableTabRow(
            selectedTabIndex = selectedTab,
            edgePadding = 16.dp,
            containerColor = MaterialTheme.colorScheme.surface
        ) {
            tabs.forEachIndexed { index, title ->
                Tab(
                    selected = selectedTab == index,
                    onClick = { selectedTab = index },
                    text = { Text(title, fontWeight = FontWeight.Bold) }
                )
            }
        }

        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            contentPadding = PaddingValues(top = 16.dp, bottom = 24.dp)
        ) {
            when (selectedTab) {
                0 -> {
                    // Created Stories
                    if (createdStories.isEmpty()) {
                        item {
                            EmptyState(
                                icon = "✍️",
                                title = "No Created Stories Yet",
                                subtitle = "Craft your very own fairy tale with the Story Builder!",
                                actionText = "CREATE A STORY",
                                onAction = { onNavigate(AppScreen.FairyTaleBuilder) }
                            )
                        }
                    } else {
                        items(createdStories) { story ->
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 6.dp),
                                shape = RoundedCornerShape(20.dp),
                                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
                            ) {
                                Column(modifier = Modifier.padding(18.dp)) {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Text(story.coverEmoji, fontSize = 28.sp)
                                        Spacer(modifier = Modifier.width(10.dp))
                                        Column {
                                            Text(
                                                text = story.title,
                                                style = MaterialTheme.typography.titleMedium,
                                                fontWeight = FontWeight.Bold
                                            )
                                            Text(
                                                text = "Created ${story.dateCreated} • ${story.world}",
                                                style = MaterialTheme.typography.bodySmall,
                                                color = StoryTextSecondary
                                            )
                                        }
                                    }
                                    Spacer(modifier = Modifier.height(10.dp))
                                    Text(
                                        text = story.fullText,
                                        style = MaterialTheme.typography.bodySmall,
                                        color = StoryTextPrimary,
                                        lineHeight = 20.sp
                                    )
                                }
                            }
                        }
                    }
                }
                1 -> {
                    // Favorite Stories
                    if (favStories.isEmpty()) {
                        item {
                            EmptyState(
                                icon = "💖",
                                title = "No Favorites Yet",
                                subtitle = "Tap the heart icon while reading any story to save it here!",
                                actionText = "EXPLORE LIBRARY",
                                onAction = { onNavigate(AppScreen.StoryLibrary) }
                            )
                        }
                    } else {
                        items(favStories) { story ->
                            StoryBookCard(story = story, onRead = { onNavigate(AppScreen.Reader(story.id)) })
                        }
                    }
                }
                2 -> {
                    // Characters Gallery
                    items(viewModel.characters) { char ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 6.dp),
                            shape = RoundedCornerShape(18.dp),
                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
                        ) {
                            Row(
                                modifier = Modifier.padding(14.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(char.avatarEmoji, fontSize = 34.sp)
                                Spacer(modifier = Modifier.width(14.dp))
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(
                                        text = char.name,
                                        style = MaterialTheme.typography.titleSmall,
                                        fontWeight = FontWeight.Bold
                                    )
                                    Text(
                                        text = "${char.role} • ${char.world}",
                                        style = MaterialTheme.typography.bodySmall,
                                        color = StoryPurplePrimary
                                    )
                                    Text(
                                        text = char.bio,
                                        style = MaterialTheme.typography.bodySmall,
                                        color = StoryTextSecondary
                                    )
                                }
                            }
                        }
                    }
                }
                3 -> {
                    // Badges Gallery
                    items(viewModel.achievements) { badge ->
                        val unlocked = userProfile.unlockedBadgeIds.contains(badge.id)
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 5.dp),
                            shape = RoundedCornerShape(18.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = if (unlocked) StoryGoldLight else MaterialTheme.colorScheme.surface
                            )
                        ) {
                            Row(
                                modifier = Modifier.padding(16.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(if (unlocked) badge.iconEmoji else "🔒", fontSize = 32.sp)
                                Spacer(modifier = Modifier.width(14.dp))
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(
                                        text = badge.title,
                                        style = MaterialTheme.typography.titleSmall,
                                        fontWeight = FontWeight.Bold,
                                        color = if (unlocked) StoryGoldDark else StoryTextMuted
                                    )
                                    Text(
                                        text = badge.description,
                                        style = MaterialTheme.typography.bodySmall,
                                        color = StoryTextSecondary
                                    )
                                }
                                Text(
                                    text = "+${badge.xpBonus} XP",
                                    fontWeight = FontWeight.Bold,
                                    color = StoryPurplePrimary,
                                    fontSize = 12.sp
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

// 4. PROGRESS OVERVIEW
@Composable
fun ProgressOverviewScreen(
    viewModel: StoryLandViewModel,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    val userProfile by viewModel.userProfile.collectAsState()
    val currentRank = RankLevel.fromXp(userProfile.xp)

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .windowInsetsPadding(WindowInsets.statusBars)
            .testTag("progress_overview_screen")
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
                text = "My Reading Journey & Level",
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
            // Rank progression
            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    shape = RoundedCornerShape(24.dp),
                    colors = CardDefaults.cardColors(containerColor = StoryPurpleContainer)
                ) {
                    Column(
                        modifier = Modifier.padding(20.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(currentRank.badgeIcon, fontSize = 48.sp)
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "Rank: ${currentRank.title}",
                            style = MaterialTheme.typography.headlineSmall,
                            fontWeight = FontWeight.Bold,
                            color = StoryPurpleDark
                        )
                        Text(
                            text = "${userProfile.xp} Total Story XP",
                            style = MaterialTheme.typography.titleMedium,
                            color = StoryPurplePrimary
                        )
                    }
                }
            }

            // All Ranks hierarchy
            item {
                Text(
                    text = "StoryLand Ranking Ranks",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
            }

            items(RankLevel.entries) { rank ->
                val achieved = userProfile.xp >= rank.minXp
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = if (achieved) StoryGreenLight else StoryCreamSurface
                    )
                ) {
                    Row(
                        modifier = Modifier.padding(14.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(rank.badgeIcon, fontSize = 28.sp)
                        Spacer(modifier = Modifier.width(12.dp))
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = rank.title,
                                fontWeight = FontWeight.Bold,
                                color = if (achieved) StoryGreen else StoryTextMuted
                            )
                            Text(
                                text = "${rank.minXp} - ${if (rank.maxXp == Int.MAX_VALUE) "∞" else rank.maxXp} XP",
                                style = MaterialTheme.typography.bodySmall,
                                color = StoryTextSecondary
                            )
                        }
                        if (achieved) {
                            Text("✓ Achieved", color = StoryGreen, fontWeight = FontWeight.Bold, fontSize = 12.sp)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun EmptyState(
    icon: String,
    title: String,
    subtitle: String,
    actionText: String,
    onAction: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(icon, fontSize = 48.sp)
        Spacer(modifier = Modifier.height(14.dp))
        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface
        )
        Spacer(modifier = Modifier.height(6.dp))
        Text(
            text = subtitle,
            style = MaterialTheme.typography.bodySmall,
            color = StoryTextMuted
        )
        Spacer(modifier = Modifier.height(18.dp))
        Button(
            onClick = onAction,
            shape = RoundedCornerShape(18.dp),
            colors = ButtonDefaults.buttonColors(containerColor = StoryPurplePrimary)
        ) {
            Text(actionText, fontWeight = FontWeight.Bold)
        }
    }
}
