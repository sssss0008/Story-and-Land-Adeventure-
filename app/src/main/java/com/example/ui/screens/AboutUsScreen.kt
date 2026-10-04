package com.example.ui.screens

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.components.ParentGateDialog
import com.example.ui.theme.*

@Composable
fun AboutUsScreen(
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    var showParentGateForLinkedIn by remember { mutableStateOf(false) }

    fun openLinkedInUrl() {
        try {
            val intent = Intent(
                Intent.ACTION_VIEW,
                Uri.parse("https://www.linkedin.com/in/awiskaracharya/")
            )
            context.startActivity(intent)
        } catch (_: Exception) {}
    }

    if (showParentGateForLinkedIn) {
        ParentGateDialog(
            onGatePassed = {
                showParentGateForLinkedIn = false
                openLinkedInUrl()
            },
            onDismiss = { showParentGateForLinkedIn = false }
        )
    }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .testTag("about_us_screen_content"),
        contentPadding = PaddingValues(bottom = 96.dp)
    ) {
        // App Header Banner
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        Brush.linearGradient(
                            listOf(StoryPurplePrimary, StoryBlueSecondary)
                        )
                    )
                    .padding(28.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Box(
                        modifier = Modifier
                            .size(72.dp)
                            .clip(CircleShape)
                            .background(Color.White.copy(alpha = 0.2f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("✨", fontSize = 38.sp)
                    }
                    Spacer(modifier = Modifier.height(14.dp))
                    Text(
                        text = "StoryLand Adventure",
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.ExtraBold,
                        color = Color.White
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Read. Imagine. Create.",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Medium,
                        color = Color.White.copy(alpha = 0.9f)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Surface(
                        shape = RoundedCornerShape(12.dp),
                        color = Color.White.copy(alpha = 0.25f)
                    ) {
                        Text(
                            text = "Version 1.0 • Child Safe & Offline First",
                            style = MaterialTheme.typography.labelSmall,
                            color = Color.White,
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp)
                        )
                    }
                }
            }
        }

        // Section 1: About StoryLand
        item {
            AboutCard(
                title = "About StoryLand",
                icon = "🏰",
                description = "StoryLand Adventure combines interactive storytelling, creative writing, reading comprehension, and magical games into one playful universe. Children explore fantasy realms, build characters, and develop lifelong literacy skills through joyful discovery."
            )
        }

        // Section 2: Reading & Creativity
        item {
            AboutCard(
                title = "Reading & Creativity Studio",
                icon = "🎨",
                description = "Children can craft their own fairy tales, narrate with their voice, order story sequencing cards, and choose adventure paths. We nurture active creators rather than passive screen consumers."
            )
        }

        // Section 3: Story Adventures
        item {
            AboutCard(
                title = "Interactive Story Adventures",
                icon = "🗺️",
                description = "From rescuing friendly dragons to sailing with pirate captains and solving museum mysteries, every quest invites children to make thoughtful choices and practice critical thinking."
            )
        }

        // Section 4: Child Safety & Privacy
        item {
            AboutCard(
                title = "Child Safety & Privacy",
                icon = "🛡️",
                description = "StoryLand is 100% ad-free with zero in-app purchases. All reading progress, custom stories, and voice recordings remain safely on your device with no third-party data tracking."
            )
        }

        // Section 5: Learning Through Stories
        item {
            AboutCard(
                title = "Learning Through Stories",
                icon = "📚",
                description = "Every story integrates early reading skills: phonics awareness, vocabulary development, cause-and-effect reasoning, empathy, and positive moral lessons."
            )
        }

        // DEVELOPER / CREATOR SECTION
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 12.dp)
                    .testTag("developer_section_card"),
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
            ) {
                Column(
                    modifier = Modifier.padding(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Box(
                        modifier = Modifier
                            .size(56.dp)
                            .clip(CircleShape)
                            .background(StoryPurpleContainer),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("🧑‍💻", fontSize = 28.sp)
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = "Developed By",
                        style = MaterialTheme.typography.labelMedium,
                        color = StoryTextMuted,
                        letterSpacing = 1.sp
                    )

                    Text(
                        text = "Awiskar Acharya",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = StoryPurplePrimary
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        text = "Passionate Android Engineer crafting magical, playful, and educational experiences.",
                        style = MaterialTheme.typography.bodySmall,
                        textAlign = TextAlign.Center,
                        color = StoryTextSecondary
                    )

                    Spacer(modifier = Modifier.height(18.dp))

                    // Connect on LinkedIn Button with child-safe parent gate
                    Button(
                        onClick = { showParentGateForLinkedIn = true },
                        shape = RoundedCornerShape(16.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF0A66C2), // LinkedIn signature blue
                            contentColor = Color.White
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp)
                            .testTag("linkedin_connect_button")
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            // In icon or text
                            Surface(
                                shape = RoundedCornerShape(4.dp),
                                color = Color.White,
                                modifier = Modifier.size(20.dp)
                            ) {
                                Box(contentAlignment = Alignment.Center) {
                                    Text(
                                        text = "in",
                                        fontWeight = FontWeight.ExtraBold,
                                        color = Color(0xFF0A66C2),
                                        fontSize = 13.sp
                                    )
                                }
                            }
                            Spacer(modifier = Modifier.width(10.dp))
                            Text(
                                text = "Connect on LinkedIn",
                                fontWeight = FontWeight.Bold,
                                fontSize = 14.sp
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        text = "Protected by Grown-Up Verification",
                        style = MaterialTheme.typography.labelSmall,
                        color = StoryTextMuted,
                        fontSize = 11.sp
                    )
                }
            }
        }
    }
}

@Composable
fun AboutCard(
    title: String,
    icon: String,
    description: String
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 6.dp),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Row(
            modifier = Modifier.padding(18.dp),
            verticalAlignment = Alignment.Top
        ) {
            Box(
                modifier = Modifier
                    .size(44.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(StoryPurpleContainer),
                contentAlignment = Alignment.Center
            ) {
                Text(text = icon, fontSize = 22.sp)
            }
            Spacer(modifier = Modifier.width(14.dp))
            Column {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = description,
                    style = MaterialTheme.typography.bodySmall,
                    color = StoryTextSecondary,
                    lineHeight = 20.sp
                )
            }
        }
    }
}
