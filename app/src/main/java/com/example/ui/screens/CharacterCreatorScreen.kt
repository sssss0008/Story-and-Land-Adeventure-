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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.*
import com.example.viewmodel.StoryLandViewModel

data class CharacterTrait(val label: String, val emoji: String)

@Composable
fun CharacterCreatorScreen(
    viewModel: StoryLandViewModel,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    val types = listOf(
        CharacterTrait("Human Hero", "🧑"),
        CharacterTrait("Animal Friend", "🦊"),
        CharacterTrait("Clever Robot", "🤖"),
        CharacterTrait("Star Wizard", "🧙"),
        CharacterTrait("Glimmer Fairy", "🧚"),
        CharacterTrait("Wild Explorer", "🧭"),
        CharacterTrait("Superhero", "🦸")
    )

    val hats = listOf(
        CharacterTrait("Golden Crown", "👑"),
        CharacterTrait("Wizard Hat", "🎩"),
        CharacterTrait("Silver Helmet", "🪖"),
        CharacterTrait("Steampunk Goggles", "🥽"),
        CharacterTrait("Flower Wreath", "🌸"),
        CharacterTrait("Adventurer Cap", "🧢")
    )

    val accessories = listOf(
        CharacterTrait("Magic Wand", "🪄"),
        CharacterTrait("Star Compass", "🧭"),
        CharacterTrait("Honor Shield", "🛡️"),
        CharacterTrait("Magnifying Glass", "🔍"),
        CharacterTrait("Power Belt", "⚡"),
        CharacterTrait("Musical Flute", "🪈")
    )

    val abilities = listOf(
        "Starlight Stardust Glow ✨",
        "Lightning Dash Speed ⚡",
        "Animal Whispering 🐾",
        "Super Jump & Flight 🪽",
        "Water & Ice Crystals ❄️",
        "Master Puzzle Solver 🧠"
    )

    val colorOptions = listOf(
        Color(0xFF7C3AED), // Violet
        Color(0xFF2563EB), // Blue
        Color(0xFFF59E0B), // Gold
        Color(0xFFEC4899), // Pink
        Color(0xFF10B981), // Emerald
        Color(0xFFF97316)  // Orange
    )

    var characterName by remember { mutableStateOf("Starlight Piper") }
    var selectedType by remember { mutableStateOf(types[0]) }
    var selectedHat by remember { mutableStateOf(hats[0]) }
    var selectedAccessory by remember { mutableStateOf(accessories[0]) }
    var selectedAbility by remember { mutableStateOf(abilities[0]) }
    var selectedColor by remember { mutableStateOf(colorOptions[0]) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .windowInsetsPadding(WindowInsets.statusBars)
            .testTag("character_creator_screen")
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
                modifier = Modifier.testTag("creator_back_btn")
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Back",
                    tint = StoryPurplePrimary
                )
            }
            Column {
                Text(
                    text = "Character Creator Studio",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = StoryPurplePrimary
                )
                Text(
                    text = "Bring your original hero to life!",
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
            // Live Character Showcase Stage
            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                        .testTag("character_preview_stage"),
                    shape = RoundedCornerShape(26.dp),
                    colors = CardDefaults.cardColors(containerColor = selectedColor.copy(alpha = 0.15f))
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        // Avatar layered preview
                        Box(
                            modifier = Modifier
                                .size(120.dp)
                                .clip(CircleShape)
                                .background(selectedColor.copy(alpha = 0.25f))
                                .border(4.dp, selectedColor, CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Text(selectedHat.emoji, fontSize = 28.sp)
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Text(selectedType.emoji, fontSize = 42.sp)
                                    Text(selectedAccessory.emoji, fontSize = 24.sp)
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(14.dp))

                        Text(
                            text = characterName.ifBlank { "Hero" },
                            style = MaterialTheme.typography.headlineSmall,
                            fontWeight = FontWeight.Bold,
                            color = StoryPurpleDark
                        )

                        Text(
                            text = "Special Ability: $selectedAbility",
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.SemiBold,
                            color = StoryPurplePrimary
                        )
                    }
                }
            }

            // Name Input
            item {
                OutlinedTextField(
                    value = characterName,
                    onValueChange = { characterName = it },
                    label = { Text("Character Name") },
                    singleLine = true,
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                        .testTag("character_name_input")
                )
            }

            // 1. Character Type
            item {
                Text(
                    text = "1. Character Class",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier.padding(top = 12.dp, bottom = 6.dp)
                )
                LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    items(types) { type ->
                        FilterChip(
                            selected = selectedType.label == type.label,
                            onClick = { selectedType = type },
                            label = { Text("${type.emoji} ${type.label}") },
                            shape = RoundedCornerShape(16.dp)
                        )
                    }
                }
            }

            // 2. Hat / Crown
            item {
                Text(
                    text = "2. Hat or Crown",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier.padding(top = 16.dp, bottom = 6.dp)
                )
                LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    items(hats) { hat ->
                        FilterChip(
                            selected = selectedHat.label == hat.label,
                            onClick = { selectedHat = hat },
                            label = { Text("${hat.emoji} ${hat.label}") },
                            shape = RoundedCornerShape(16.dp)
                        )
                    }
                }
            }

            // 3. Accessory
            item {
                Text(
                    text = "3. Magical Accessory",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier.padding(top = 16.dp, bottom = 6.dp)
                )
                LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    items(accessories) { acc ->
                        FilterChip(
                            selected = selectedAccessory.label == acc.label,
                            onClick = { selectedAccessory = acc },
                            label = { Text("${acc.emoji} ${acc.label}") },
                            shape = RoundedCornerShape(16.dp)
                        )
                    }
                }
            }

            // 4. Aura Color
            item {
                Text(
                    text = "4. Hero Aura Color",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier.padding(top = 16.dp, bottom = 6.dp)
                )
                Row(
                    horizontalArrangement = Arrangement.spacedBy(14.dp),
                    modifier = Modifier.padding(vertical = 6.dp)
                ) {
                    colorOptions.forEach { color ->
                        Box(
                            modifier = Modifier
                                .size(44.dp)
                                .clip(CircleShape)
                                .background(color)
                                .border(
                                    width = if (selectedColor == color) 3.dp else 1.dp,
                                    color = if (selectedColor == color) StoryPurpleDark else Color.Transparent,
                                    shape = CircleShape
                                )
                                .clickable { selectedColor = color }
                        )
                    }
                }
            }

            // 5. Special Ability
            item {
                Text(
                    text = "5. Special Ability",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier.padding(top = 16.dp, bottom = 6.dp)
                )
                Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                    abilities.forEach { ability ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(14.dp))
                                .border(
                                    width = if (selectedAbility == ability) 2.dp else 1.dp,
                                    color = if (selectedAbility == ability) StoryPurplePrimary else StoryDivider,
                                    shape = RoundedCornerShape(14.dp)
                                )
                                .clickable { selectedAbility = ability },
                            colors = CardDefaults.cardColors(
                                containerColor = if (selectedAbility == ability) StoryPurpleContainer else MaterialTheme.colorScheme.surface
                            )
                        ) {
                            Text(
                                text = ability,
                                style = MaterialTheme.typography.bodyMedium,
                                fontWeight = FontWeight.Medium,
                                modifier = Modifier.padding(14.dp)
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.height(20.dp))
            }

            // Save to Collection Button
            item {
                Button(
                    onClick = {
                        viewModel.addXpAndStars(30, 8)
                        viewModel.soundManager.playCelebration()
                        onBack()
                    },
                    shape = RoundedCornerShape(20.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = StoryPurplePrimary),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp)
                        .testTag("save_character_btn")
                ) {
                    Text(
                        text = "⭐ SAVE TO HERO COLLECTION (+30 XP)",
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp
                    )
                }
            }
        }
    }
}
