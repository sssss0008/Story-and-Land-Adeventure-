package com.example.model

enum class RankLevel(val title: String, val minXp: Int, val maxXp: Int, val badgeIcon: String) {
    READER("Reader", 0, 100, "📖"),
    STORY_EXPLORER("Story Explorer", 101, 300, "🧭"),
    WRITER("Writer", 301, 600, "✍️"),
    STORY_CREATOR("Story Creator", 601, 1000, "🎨"),
    STORY_MASTER("Story Master", 1001, Int.MAX_VALUE, "👑");

    companion object {
        fun fromXp(xp: Int): RankLevel {
            return entries.lastOrNull { xp >= it.minXp } ?: READER
        }
    }
}

enum class StoryCategory(val displayName: String, val emoji: String) {
    FANTASY("Fantasy", "🏰"),
    ADVENTURE("Adventure", "⛵"),
    MYSTERY("Mystery", "🔍"),
    BEDTIME("Bedtime", "🌙"),
    HEROES("Heroes", "🦸"),
    ANIMALS("Animals", "🐾"),
    MAGIC("Magic", "✨"),
    SHORT_STORIES("Short Stories", "⚡")
}

data class StoryChoice(
    val id: String,
    val text: String,
    val targetChapter: Int,
    val consequenceNote: String = ""
)

data class StoryChapter(
    val chapterNumber: Int,
    val title: String,
    val paragraphs: List<String>,
    val sceneEmoji: String = "✨",
    val choices: List<StoryChoice> = emptyList()
)

data class Story(
    val id: String,
    val title: String,
    val subtitle: String,
    val category: StoryCategory,
    val readingTimeMinutes: Int,
    val xpReward: Int = 20,
    val starsReward: Int = 5,
    val chapters: List<StoryChapter>,
    val coverEmoji: String = "📚",
    val primaryColorHex: Long = 0xFF7C3AED,
    val isFeatured: Boolean = false,
    val isBedtime: Boolean = false,
    val moral: String = "",
    val ageRange: String = "4–10 yrs"
)

data class QuizQuestion(
    val id: String,
    val question: String,
    val illustrationEmoji: String,
    val options: List<String>,
    val correctAnswerIndex: Int,
    val explanation: String
)

data class StoryQuiz(
    val id: String,
    val storyId: String? = null,
    val title: String,
    val subtitle: String,
    val category: String,
    val questions: List<QuizQuestion>,
    val xpReward: Int = 25,
    val starsReward: Int = 5,
    val difficulty: String = "Easy"
)

data class Achievement(
    val id: String,
    val title: String,
    val description: String,
    val iconEmoji: String,
    val xpBonus: Int = 30,
    val category: String = "General"
)

data class CharacterItem(
    val id: String,
    val name: String,
    val role: String,
    val world: String,
    val avatarEmoji: String,
    val power: String,
    val bio: String,
    val isUnlocked: Boolean = false
)

data class WorldLocation(
    val id: String,
    val name: String,
    val emoji: String,
    val description: String,
    val isUnlocked: Boolean = false
)

data class WorldItem(
    val id: String,
    val name: String,
    val emoji: String,
    val description: String,
    val locations: List<WorldLocation>,
    val themeColorHex: Long = 0xFF7C3AED,
    val isUnlocked: Boolean = false
)

data class SequenceCard(
    val id: Int,
    val originalOrder: Int,
    val title: String,
    val description: String,
    val emoji: String
)

data class SequencingGame(
    val id: String,
    val storyTitle: String,
    val description: String,
    val scenes: List<SequenceCard>,
    val xpReward: Int = 30,
    val starsReward: Int = 8
)

// Fairy Tale Elements
data class FairyTaleOption(
    val id: String,
    val name: String,
    val emoji: String,
    val description: String
)

// User Created Story
data class CreatedStory(
    val id: String,
    val title: String,
    val hero: String,
    val world: String,
    val companion: String,
    val problem: String,
    val ending: String,
    val fullText: String,
    val dateCreated: String,
    val coverEmoji: String = "📖"
)

// Voice Recording
data class RecordedStory(
    val id: String,
    val title: String,
    val date: String,
    val durationSeconds: Int,
    val filePath: String? = null,
    val note: String = ""
)

// Reading challenge
data class ReadingChallenge(
    val id: String,
    val title: String,
    val description: String,
    val emoji: String,
    val xpReward: Int,
    val starsReward: Int,
    val currentProgress: Int,
    val maxProgress: Int,
    val isCompleted: Boolean = false
)

// Detective Case
data class DetectiveClue(
    val id: String,
    val title: String,
    val description: String,
    val emoji: String,
    val location: String,
    val isFound: Boolean = false
)

data class DetectiveCase(
    val id: String,
    val title: String,
    val mysteryDescription: String,
    val clues: List<DetectiveClue>,
    val suspects: List<String>,
    val correctCulpritIndex: Int,
    val solutionExplanation: String,
    val isSolved: Boolean = false
)

// App Settings
data class AppSettings(
    val soundEffects: Boolean = true,
    val backgroundMusic: Boolean = true,
    val narrationAudio: Boolean = true,
    val narrationSpeed: Float = 1.0f,
    val textSizeScale: Float = 1.0f, // 0.85f (Small), 1.0f (Normal), 1.2f (Large), 1.35f (XLarge)
    val animationsEnabled: Boolean = true,
    val sleepTimerMinutes: Int = 0,
    val highContrast: Boolean = false
)

// User Profile State
data class UserProfile(
    val name: String = "Story Explorer",
    val avatarEmoji: String = "🦁",
    val avatarId: String = "explorer",
    val xp: Int = 120,
    val stars: Int = 24,
    val readingStreakDays: Int = 5,
    val storiesReadCount: Int = 4,
    val storiesCreatedCount: Int = 1,
    val quizzesCompletedCount: Int = 3,
    val lastStoryId: String = "dragon_moonlight",
    val lastStoryChapter: Int = 3,
    val completedStoryIds: Set<String> = setOf("dragon_moonlight"),
    val favoriteStoryIds: Set<String> = setOf("dragon_moonlight", "secret_door"),
    val unlockedBadgeIds: Set<String> = setOf("first_story", "book_explorer"),
    val unlockedWorldIds: Set<String> = setOf("magic_kingdom", "dragon_valley"),
    val collectedClueIds: Set<String> = emptySet(),
    val solvedCaseIds: Set<String> = emptySet(),
    val totalReadingMinutes: Int = 48
)
