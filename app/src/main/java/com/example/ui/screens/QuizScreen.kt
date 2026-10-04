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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.QuizQuestion
import com.example.model.StoryQuiz
import com.example.model.UserProfile
import com.example.ui.theme.*
import com.example.viewmodel.AppScreen
import com.example.viewmodel.StoryLandViewModel

@Composable
fun QuizScreen(
    viewModel: StoryLandViewModel,
    userProfile: UserProfile,
    onNavigate: (AppScreen) -> Unit,
    modifier: Modifier = Modifier
) {
    var selectedCategory by remember { mutableStateOf("All") }
    val categories = listOf("All", "Fantasy", "Fairy Tales", "Adventure", "Mystery", "Vocabulary")

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .testTag("quiz_screen_content"),
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
                    text = "Story Quiz",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.ExtraBold,
                    color = StoryPurplePrimary
                )
                Text(
                    text = "How well did you understand the story?",
                    style = MaterialTheme.typography.bodyMedium,
                    color = StoryTextSecondary
                )
            }
        }

        // Quiz Modes Banner
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 4.dp),
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(containerColor = StoryPurpleContainer)
            ) {
                Row(
                    modifier = Modifier.padding(18.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(54.dp)
                            .clip(CircleShape)
                            .background(StoryPurplePrimary),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("⚡", fontSize = 28.sp)
                    }
                    Spacer(modifier = Modifier.width(16.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "DAILY QUIZ CHALLENGE",
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = FontWeight.Bold,
                            color = StoryPurplePrimary
                        )
                        Text(
                            text = "Moonlight Valley Quiz",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = StoryPurpleDark
                        )
                        Text(
                            text = "5 questions • +30 XP • +6 Stars",
                            style = MaterialTheme.typography.bodySmall,
                            color = StoryTextSecondary
                        )
                    }
                    Button(
                        onClick = { onNavigate(AppScreen.QuizGame("quiz_dragon_valley")) },
                        colors = ButtonDefaults.buttonColors(containerColor = StoryPurplePrimary),
                        shape = RoundedCornerShape(16.dp),
                        modifier = Modifier.testTag("play_daily_quiz_btn")
                    ) {
                        Text("PLAY", fontWeight = FontWeight.Bold)
                    }
                }
            }
        }

        // Category Filter Chips
        item {
            LazyRow(
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 12.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
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

        // Quizzes List
        items(viewModel.quizzes) { quiz ->
            QuizCard(quiz = quiz, onStart = { onNavigate(AppScreen.QuizGame(quiz.id)) })
        }

        // Quick Quiz Mode Card
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp)
                    .clip(RoundedCornerShape(22.dp))
                    .clickable { onNavigate(AppScreen.QuizGame("quiz_quick_fairytales")) }
                    .testTag("quick_quiz_card"),
                shape = RoundedCornerShape(22.dp),
                colors = CardDefaults.cardColors(containerColor = StoryGoldLight)
            ) {
                Row(
                    modifier = Modifier.padding(18.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(54.dp)
                            .clip(CircleShape)
                            .background(StoryGoldAccent),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("🎯", fontSize = 28.sp)
                    }
                    Spacer(modifier = Modifier.width(16.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "QUICK QUIZ MODE",
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = FontWeight.Bold,
                            color = StoryGoldDark
                        )
                        Text(
                            text = "Fairy Tale Wonders",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = StoryTextPrimary
                        )
                        Text(
                            text = "4 quick questions to test your imagination",
                            style = MaterialTheme.typography.bodySmall,
                            color = StoryTextSecondary
                        )
                    }
                    Icon(
                        imageVector = Icons.Default.ArrowForward,
                        contentDescription = "Start Quiz",
                        tint = StoryGoldDark
                    )
                }
            }
        }
    }
}

@Composable
fun QuizCard(
    quiz: StoryQuiz,
    onStart: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 6.dp)
            .clip(RoundedCornerShape(20.dp))
            .clickable { onStart() }
            .testTag("quiz_card_${quiz.id}"),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier.padding(18.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(50.dp)
                    .clip(RoundedCornerShape(14.dp))
                    .background(StoryPurpleContainer),
                contentAlignment = Alignment.Center
            ) {
                Text("🏆", fontSize = 26.sp)
            }
            Spacer(modifier = Modifier.width(14.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = quiz.category.uppercase(),
                    style = MaterialTheme.typography.labelSmall,
                    color = StoryPurplePrimary,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = quiz.title,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = "${quiz.questions.size} Questions • +${quiz.xpReward} XP",
                    style = MaterialTheme.typography.bodySmall,
                    color = StoryTextSecondary
                )
            }
            Button(
                onClick = onStart,
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(containerColor = StoryPurplePrimary),
                modifier = Modifier.testTag("start_quiz_${quiz.id}")
            ) {
                Text("START", fontWeight = FontWeight.Bold, fontSize = 12.sp)
            }
        }
    }
}

@Composable
fun QuizGameScreen(
    viewModel: StoryLandViewModel,
    quizId: String,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    val quiz by viewModel.activeQuiz.collectAsState()
    val questionIdx by viewModel.quizQuestionIndex.collectAsState()
    val score by viewModel.quizScore.collectAsState()
    val selectedAnswer by viewModel.selectedAnswerIndex.collectAsState()
    val showFeedback by viewModel.showAnswerFeedback.collectAsState()

    val currentQuiz = quiz ?: return
    val currentQuestion = currentQuiz.questions.getOrNull(questionIdx) ?: return
    val totalQuestions = currentQuiz.questions.size

    val isCorrect = selectedAnswer == currentQuestion.correctAnswerIndex

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .windowInsetsPadding(WindowInsets.statusBars)
            .testTag("quiz_gameplay_screen")
    ) {
        // Quiz Top Bar
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            IconButton(
                onClick = onBack,
                modifier = Modifier.testTag("quiz_back_button")
            ) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = "Close Quiz",
                    tint = StoryPurplePrimary
                )
            }

            Text(
                text = "Question ${questionIdx + 1} of $totalQuestions",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = StoryPurplePrimary
            )

            // Score counter
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .clip(RoundedCornerShape(14.dp))
                    .background(StoryPurpleContainer)
                    .padding(horizontal = 10.dp, vertical = 4.dp)
            ) {
                Text("⭐", fontSize = 14.sp)
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = "$score",
                    fontWeight = FontWeight.Bold,
                    color = StoryPurpleDark
                )
            }
        }

        // Progress bar
        LinearProgressIndicator(
            progress = { ((questionIdx + 1).toFloat() / totalQuestions).coerceIn(0f, 1f) },
            modifier = Modifier
                .fillMaxWidth()
                .height(6.dp),
            color = StoryPurplePrimary,
            trackColor = StoryPurpleContainer
        )

        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
            contentPadding = PaddingValues(vertical = 20.dp)
        ) {
            // Question Illustration Scene
            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(140.dp),
                    shape = RoundedCornerShape(24.dp),
                    colors = CardDefaults.cardColors(containerColor = StoryPurpleContainer)
                ) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = currentQuestion.illustrationEmoji,
                            fontSize = 64.sp
                        )
                    }
                }
            }

            // Question Text
            item {
                Spacer(modifier = Modifier.height(20.dp))
                Text(
                    text = currentQuestion.question,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(24.dp))
            }

            // Options
            items(currentQuestion.options.indices.toList()) { index ->
                val optionText = currentQuestion.options[index]
                val isSelected = selectedAnswer == index
                val isAnswerTarget = currentQuestion.correctAnswerIndex == index

                val optionBg = when {
                    !showFeedback -> if (isSelected) StoryPurpleContainer else MaterialTheme.colorScheme.surface
                    isAnswerTarget -> StoryGreenLight
                    isSelected && !isCorrect -> StoryPinkLight
                    else -> MaterialTheme.colorScheme.surface
                }

                val borderColor = when {
                    !showFeedback -> if (isSelected) StoryPurplePrimary else StoryDivider
                    isAnswerTarget -> StoryGreen
                    isSelected && !isCorrect -> StoryPink
                    else -> StoryDivider
                }

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 6.dp)
                        .clip(RoundedCornerShape(18.dp))
                        .border(2.dp, borderColor, RoundedCornerShape(18.dp))
                        .clickable(enabled = !showFeedback) {
                            viewModel.selectQuizAnswer(index)
                        }
                        .testTag("quiz_option_$index"),
                    shape = RoundedCornerShape(18.dp),
                    colors = CardDefaults.cardColors(containerColor = optionBg)
                ) {
                    Row(
                        modifier = Modifier
                            .padding(16.dp)
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(32.dp)
                                .clip(CircleShape)
                                .background(borderColor.copy(alpha = 0.2f)),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "${('A' + index)}",
                                fontWeight = FontWeight.Bold,
                                color = StoryPurpleDark
                            )
                        }
                        Spacer(modifier = Modifier.width(14.dp))
                        Text(
                            text = optionText,
                            style = MaterialTheme.typography.bodyLarge,
                            fontWeight = FontWeight.Medium,
                            color = MaterialTheme.colorScheme.onSurface,
                            modifier = Modifier.weight(1f)
                        )
                    }
                }
            }

            // Feedback Card
            if (showFeedback) {
                item {
                    Spacer(modifier = Modifier.height(16.dp))
                    Card(
                        shape = RoundedCornerShape(18.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = if (isCorrect) StoryGreenLight else StoryGoldLight
                        ),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(
                                text = if (isCorrect) "🌟 Great reading!" else "💡 Good try! Let's look back at the story.",
                                fontWeight = FontWeight.Bold,
                                color = if (isCorrect) StoryGreen else StoryGoldDark,
                                fontSize = 16.sp
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = currentQuestion.explanation,
                                style = MaterialTheme.typography.bodyMedium,
                                color = StoryTextPrimary
                            )
                        }
                    }
                }
            }
        }

        // Bottom CTA Button
        if (showFeedback) {
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .windowInsetsPadding(WindowInsets.navigationBars),
                tonalElevation = 8.dp
            ) {
                Box(modifier = Modifier.padding(16.dp)) {
                    Button(
                        onClick = { viewModel.nextQuizQuestion() },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(52.dp)
                            .testTag("quiz_next_button"),
                        shape = RoundedCornerShape(20.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = StoryPurplePrimary)
                    ) {
                        Text(
                            text = if (questionIdx + 1 < totalQuestions) "NEXT QUESTION ➔" else "FINISH QUIZ 🏆",
                            fontWeight = FontWeight.Bold,
                            fontSize = 15.sp
                        )
                    }
                }
            }
        }
    }
}
