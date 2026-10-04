package com.example.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.audio.NarrationManager
import com.example.audio.SoundEffectsManager
import com.example.data.PreferencesManager
import com.example.data.StoryData
import com.example.model.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

enum class BottomTab {
    HOME,
    PRACTICE,
    QUIZ,
    ABOUT_US
}

sealed class AppScreen {
    data object MainTabs : AppScreen()
    data class Reader(val storyId: String, val startChapter: Int = 1) : AppScreen()
    data object FairyTaleBuilder : AppScreen()
    data object CreateStory : AppScreen()
    data object CharacterCreator : AppScreen()
    data object StorySequencing : AppScreen()
    data object HeroAdventure : AppScreen()
    data object DragonRescue : AppScreen()
    data object PirateStory : AppScreen()
    data object SuperheroMission : AppScreen()
    data object DetectiveKids : AppScreen()
    data object MysteryIsland : AppScreen()
    data object VoiceRecording : AppScreen()
    data object StoryLibrary : AppScreen()
    data object BedtimeLibrary : AppScreen()
    data object MyCollection : AppScreen()
    data object ProgressOverview : AppScreen()
    data object ProfileScreen : AppScreen()
    data object SettingsScreen : AppScreen()
    data object ParentArea : AppScreen()
    data class QuizGame(val quizId: String) : AppScreen()
}

data class RewardCelebration(
    val title: String,
    val xpEarned: Int,
    val starsEarned: Int,
    val badgeUnlocked: Achievement? = null,
    val message: String = "Great reading! You are an amazing Story Explorer!"
)

class StoryLandViewModel(application: Application) : AndroidViewModel(application) {

    private val prefsManager = PreferencesManager(application)
    val narrationManager = NarrationManager(application)
    val soundManager = SoundEffectsManager(application)

    // Navigation State
    private val _currentBottomTab = MutableStateFlow(BottomTab.HOME)
    val currentBottomTab: StateFlow<BottomTab> = _currentBottomTab.asStateFlow()

    private val _currentScreen = MutableStateFlow<AppScreen>(AppScreen.MainTabs)
    val currentScreen: StateFlow<AppScreen> = _currentScreen.asStateFlow()

    private val screenBackStack = mutableListOf<AppScreen>()

    // User Profile & Settings
    private val _userProfile = MutableStateFlow(prefsManager.loadProfile())
    val userProfile: StateFlow<UserProfile> = _userProfile.asStateFlow()

    private val _appSettings = MutableStateFlow(prefsManager.loadSettings())
    val appSettings: StateFlow<AppSettings> = _appSettings.asStateFlow()

    // Stories & Content
    val stories = StoryData.allStories
    val quizzes = StoryData.storyQuizzes
    val achievements = StoryData.allAchievements
    val characters = StoryData.collectibleCharacters
    val worlds = StoryData.fantasyWorlds
    val dailyChallenges = StoryData.dailyChallenges

    private val _createdStories = MutableStateFlow<List<CreatedStory>>(emptyList())
    val createdStories: StateFlow<List<CreatedStory>> = _createdStories.asStateFlow()

    private val _recordedStories = MutableStateFlow<List<RecordedStory>>(emptyList())
    val recordedStories: StateFlow<List<RecordedStory>> = _recordedStories.asStateFlow()

    // Active Reader State
    private val _activeStory = MutableStateFlow<Story?>(null)
    val activeStory: StateFlow<Story?> = _activeStory.asStateFlow()

    private val _activeChapterIndex = MutableStateFlow(0)
    val activeChapterIndex: StateFlow<Int> = _activeChapterIndex.asStateFlow()

    // Active Quiz State
    private val _activeQuiz = MutableStateFlow<StoryQuiz?>(null)
    val activeQuiz: StateFlow<StoryQuiz?> = _activeQuiz.asStateFlow()
    private val _quizQuestionIndex = MutableStateFlow(0)
    val quizQuestionIndex: StateFlow<Int> = _quizQuestionIndex.asStateFlow()
    private val _quizScore = MutableStateFlow(0)
    val quizScore: StateFlow<Int> = _quizScore.asStateFlow()
    private val _selectedAnswerIndex = MutableStateFlow<Int?>(null)
    val selectedAnswerIndex: StateFlow<Int?> = _selectedAnswerIndex.asStateFlow()
    private val _showAnswerFeedback = MutableStateFlow(false)
    val showAnswerFeedback: StateFlow<Boolean> = _showAnswerFeedback.asStateFlow()

    // Active Celebration Dialog
    private val _celebration = MutableStateFlow<RewardCelebration?>(null)
    val celebration: StateFlow<RewardCelebration?> = _celebration.asStateFlow()

    init {
        // Load default created story if none
        _createdStories.value = listOf(
            CreatedStory(
                id = "created_1",
                title = "The Stardust Princess & The Baby Dragon",
                hero = "Brave Princess",
                world = "Enchanted Forest",
                companion = "Baby Dragon",
                problem = "The Lost Treasure",
                ending = "A Joyful Celebration",
                fullText = "Once upon a time in the Enchanted Forest, Brave Princess met a friendly Baby Dragon. Together, they searched for the lost royal jewel and celebrated with songs under the starry sky.",
                dateCreated = "Today",
                coverEmoji = "👸"
            )
        )
    }

    // Navigation Methods
    fun selectTab(tab: BottomTab) {
        soundManager.playClick()
        _currentBottomTab.value = tab
        _currentScreen.value = AppScreen.MainTabs
        screenBackStack.clear()
    }

    fun navigateTo(screen: AppScreen) {
        soundManager.playClick()
        screenBackStack.add(_currentScreen.value)
        _currentScreen.value = screen

        if (screen is AppScreen.Reader) {
            openStory(screen.storyId, screen.startChapter)
        } else if (screen is AppScreen.QuizGame) {
            openQuiz(screen.quizId)
        }
    }

    fun navigateBack(): Boolean {
        soundManager.playClick()
        narrationManager.stop()
        if (screenBackStack.isNotEmpty()) {
            _currentScreen.value = screenBackStack.removeAt(screenBackStack.lastIndex)
            return true
        } else if (_currentScreen.value !is AppScreen.MainTabs) {
            _currentScreen.value = AppScreen.MainTabs
            return true
        }
        return false
    }

    // Story Reader
    fun openStory(storyId: String, chapterNumber: Int = 1) {
        val story = stories.find { it.id == storyId } ?: stories.first()
        _activeStory.value = story
        val targetIdx = (chapterNumber - 1).coerceIn(0, story.chapters.size - 1)
        _activeChapterIndex.value = targetIdx
        narrationManager.loadContent(story.chapters[targetIdx].paragraphs)

        // Update last read story
        updateProfile { current ->
            current.copy(
                lastStoryId = story.id,
                lastStoryChapter = chapterNumber
            )
        }
    }

    fun nextChapter() {
        val story = _activeStory.value ?: return
        if (_activeChapterIndex.value + 1 < story.chapters.size) {
            soundManager.playPageTurn()
            _activeChapterIndex.value += 1
            narrationManager.loadContent(story.chapters[_activeChapterIndex.value].paragraphs)
        } else {
            // Story complete
            completeStory(story)
        }
    }

    fun prevChapter() {
        val story = _activeStory.value ?: return
        if (_activeChapterIndex.value > 0) {
            soundManager.playPageTurn()
            _activeChapterIndex.value -= 1
            narrationManager.loadContent(story.chapters[_activeChapterIndex.value].paragraphs)
        }
    }

    fun completeStory(story: Story) {
        soundManager.playCelebration()
        addXpAndStars(story.xpReward, story.starsReward)
        updateProfile { current ->
            val updatedCompleted = current.completedStoryIds + story.id
            val readCount = current.storiesReadCount + 1
            val updatedBadges = current.unlockedBadgeIds.toMutableSet()
            if (readCount >= 1) updatedBadges.add("first_story")
            if (readCount >= 3) updatedBadges.add("book_explorer")
            current.copy(
                completedStoryIds = updatedCompleted,
                storiesReadCount = readCount,
                unlockedBadgeIds = updatedBadges
            )
        }
        _celebration.value = RewardCelebration(
            title = "STORY COMPLETE!",
            xpEarned = story.xpReward,
            starsEarned = story.starsReward,
            badgeUnlocked = achievements.find { it.id == "book_explorer" },
            message = "Congratulations! You completed \"${story.title}\"! Keep exploring!"
        )
    }

    fun toggleFavorite(storyId: String) {
        soundManager.playClick()
        updateProfile { current ->
            val favs = if (current.favoriteStoryIds.contains(storyId)) {
                current.favoriteStoryIds - storyId
            } else {
                current.favoriteStoryIds + storyId
            }
            current.copy(favoriteStoryIds = favs)
        }
    }

    // Quiz Gameplay
    fun openQuiz(quizId: String) {
        val q = quizzes.find { it.id == quizId } ?: quizzes.first()
        _activeQuiz.value = q
        _quizQuestionIndex.value = 0
        _quizScore.value = 0
        _selectedAnswerIndex.value = null
        _showAnswerFeedback.value = false
    }

    fun selectQuizAnswer(index: Int) {
        if (_showAnswerFeedback.value) return
        _selectedAnswerIndex.value = index
        _showAnswerFeedback.value = true

        val q = _activeQuiz.value?.questions?.getOrNull(_quizQuestionIndex.value) ?: return
        if (index == q.correctAnswerIndex) {
            soundManager.playSuccess()
            _quizScore.value += 1
        } else {
            soundManager.playClick()
        }
    }

    fun nextQuizQuestion() {
        val q = _activeQuiz.value ?: return
        if (_quizQuestionIndex.value + 1 < q.questions.size) {
            _quizQuestionIndex.value += 1
            _selectedAnswerIndex.value = null
            _showAnswerFeedback.value = false
        } else {
            // Quiz Complete
            soundManager.playCelebration()
            val totalQuestions = q.questions.size
            val earnedXp = q.xpReward + (_quizScore.value * 5)
            val earnedStars = q.starsReward
            addXpAndStars(earnedXp, earnedStars)

            updateProfile { current ->
                val newCount = current.quizzesCompletedCount + 1
                val updatedBadges = current.unlockedBadgeIds.toMutableSet()
                if (_quizScore.value == totalQuestions) {
                    updatedBadges.add("quiz_master")
                }
                current.copy(
                    quizzesCompletedCount = newCount,
                    unlockedBadgeIds = updatedBadges
                )
            }

            _celebration.value = RewardCelebration(
                title = "QUIZ COMPLETE!",
                xpEarned = earnedXp,
                starsEarned = earnedStars,
                badgeUnlocked = if (_quizScore.value == totalQuestions) achievements.find { it.id == "quiz_master" } else null,
                message = "You scored ${_quizScore.value}/$totalQuestions correct! Wonderful reading!"
            )
            _showAnswerFeedback.value = false
        }
    }

    // Fairy Tale Creation
    fun saveCreatedStory(
        title: String,
        hero: FairyTaleOption,
        world: FairyTaleOption,
        companion: FairyTaleOption,
        problem: FairyTaleOption,
        ending: FairyTaleOption
    ) {
        soundManager.playCelebration()
        val text = "Once upon a time in the magical realm of ${world.name}, a brave ${hero.name} lived in harmony with friends. One sunny morning, trouble struck: ${problem.description}! Accompanied by loyal companion ${companion.name}, our hero set out with kindness, cleverness, and courage. Through mountains and starlit glens, they persevered together. In the end, their journey brought about ${ending.description.lowercase()}. All across the land, songs of their friendship will be sung forever!"

        val newStory = CreatedStory(
            id = "custom_${System.currentTimeMillis()}",
            title = title.ifBlank { "${hero.name} & ${companion.name}" },
            hero = hero.name,
            world = world.name,
            companion = companion.name,
            problem = problem.name,
            ending = ending.name,
            fullText = text,
            dateCreated = SimpleDateFormat("MMM dd, yyyy", Locale.getDefault()).format(Date()),
            coverEmoji = hero.emoji
        )

        _createdStories.value = listOf(newStory) + _createdStories.value
        addXpAndStars(40, 10)
        updateProfile { current ->
            val updatedBadges = current.unlockedBadgeIds + "creative_writer"
            current.copy(
                storiesCreatedCount = current.storiesCreatedCount + 1,
                unlockedBadgeIds = updatedBadges
            )
        }

        _celebration.value = RewardCelebration(
            title = "STORY CREATED!",
            xpEarned = 40,
            starsEarned = 10,
            badgeUnlocked = achievements.find { it.id == "creative_writer" },
            message = "Your personalized fairy tale has been added to My Stories!"
        )
    }

    // Voice Story Recording Save
    fun saveRecordedStory(title: String, durationSec: Int) {
        soundManager.playSuccess()
        val rec = RecordedStory(
            id = "rec_${System.currentTimeMillis()}",
            title = title,
            date = SimpleDateFormat("MMM dd", Locale.getDefault()).format(Date()),
            durationSeconds = durationSec,
            note = "Recorded by Story Explorer"
        )
        _recordedStories.value = listOf(rec) + _recordedStories.value
        addXpAndStars(30, 5)
        updateProfile { current ->
            val updatedBadges = current.unlockedBadgeIds + "voice_star"
            current.copy(unlockedBadgeIds = updatedBadges)
        }
    }

    // Add XP & Stars & Check rank upgrade
    fun addXpAndStars(xp: Int, stars: Int) {
        updateProfile { current ->
            val newXp = current.xp + xp
            val newStars = current.stars + stars
            val updatedBadges = current.unlockedBadgeIds.toMutableSet()
            if (newXp >= 1000) updatedBadges.add("story_master")
            current.copy(
                xp = newXp,
                stars = newStars,
                unlockedBadgeIds = updatedBadges
            )
        }
    }

    fun dismissCelebration() {
        _celebration.value = null
    }

    fun updateProfileNameAndAvatar(name: String, avatarEmoji: String, avatarId: String) {
        soundManager.playClick()
        updateProfile { it.copy(name = name, avatarEmoji = avatarEmoji, avatarId = avatarId) }
    }

    fun updateSettings(newSettings: AppSettings) {
        _appSettings.value = newSettings
        prefsManager.saveSettings(newSettings)
    }

    private fun updateProfile(transform: (UserProfile) -> UserProfile) {
        val updated = transform(_userProfile.value)
        _userProfile.value = updated
        prefsManager.saveProfile(updated)
    }

    override fun onCleared() {
        super.onCleared()
        narrationManager.cleanup()
        soundManager.cleanup()
    }
}
