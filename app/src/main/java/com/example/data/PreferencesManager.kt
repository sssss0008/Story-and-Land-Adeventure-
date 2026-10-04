package com.example.data

import android.content.Context
import android.content.SharedPreferences
import com.example.model.*

class PreferencesManager(context: Context) {

    private val prefs: SharedPreferences =
        context.getSharedPreferences("storyland_user_prefs", Context.MODE_PRIVATE)

    fun loadProfile(): UserProfile {
        val xp = prefs.getInt("user_xp", 120)
        val stars = prefs.getInt("user_stars", 24)
        val streak = prefs.getInt("reading_streak", 5)
        val readCount = prefs.getInt("stories_read_count", 4)
        val createdCount = prefs.getInt("stories_created_count", 1)
        val quizCount = prefs.getInt("quizzes_completed_count", 3)
        val name = prefs.getString("user_name", "Story Explorer") ?: "Story Explorer"
        val avatarEmoji = prefs.getString("user_avatar_emoji", "🦁") ?: "🦁"
        val avatarId = prefs.getString("user_avatar_id", "explorer") ?: "explorer"
        val lastStoryId = prefs.getString("last_story_id", "dragon_moonlight") ?: "dragon_moonlight"
        val lastChapter = prefs.getInt("last_story_chapter", 3)
        val completedIds = prefs.getStringSet("completed_story_ids", setOf("dragon_moonlight")) ?: emptySet()
        val favIds = prefs.getStringSet("favorite_story_ids", setOf("dragon_moonlight", "secret_door")) ?: emptySet()
        val badgeIds = prefs.getStringSet("unlocked_badge_ids", setOf("first_story", "book_explorer")) ?: emptySet()
        val worldIds = prefs.getStringSet("unlocked_world_ids", setOf("magic_kingdom", "dragon_valley")) ?: emptySet()
        val totalReadingMins = prefs.getInt("total_reading_minutes", 48)

        return UserProfile(
            name = name,
            avatarEmoji = avatarEmoji,
            avatarId = avatarId,
            xp = xp,
            stars = stars,
            readingStreakDays = streak,
            storiesReadCount = readCount,
            storiesCreatedCount = createdCount,
            quizzesCompletedCount = quizCount,
            lastStoryId = lastStoryId,
            lastStoryChapter = lastChapter,
            completedStoryIds = completedIds,
            favoriteStoryIds = favIds,
            unlockedBadgeIds = badgeIds,
            unlockedWorldIds = worldIds,
            totalReadingMinutes = totalReadingMins
        )
    }

    fun saveProfile(profile: UserProfile) {
        prefs.edit().apply {
            putInt("user_xp", profile.xp)
            putInt("user_stars", profile.stars)
            putInt("reading_streak", profile.readingStreakDays)
            putInt("stories_read_count", profile.storiesReadCount)
            putInt("stories_created_count", profile.storiesCreatedCount)
            putInt("quizzes_completed_count", profile.quizzesCompletedCount)
            putString("user_name", profile.name)
            putString("user_avatar_emoji", profile.avatarEmoji)
            putString("user_avatar_id", profile.avatarId)
            putString("last_story_id", profile.lastStoryId)
            putInt("last_story_chapter", profile.lastStoryChapter)
            putStringSet("completed_story_ids", profile.completedStoryIds)
            putStringSet("favorite_story_ids", profile.favoriteStoryIds)
            putStringSet("unlocked_badge_ids", profile.unlockedBadgeIds)
            putStringSet("unlocked_world_ids", profile.unlockedWorldIds)
            putInt("total_reading_minutes", profile.totalReadingMinutes)
            apply()
        }
    }

    fun loadSettings(): AppSettings {
        return AppSettings(
            soundEffects = prefs.getBoolean("setting_sound_fx", true),
            backgroundMusic = prefs.getBoolean("setting_bg_music", true),
            narrationAudio = prefs.getBoolean("setting_narration", true),
            narrationSpeed = prefs.getFloat("setting_narration_speed", 1.0f),
            textSizeScale = prefs.getFloat("setting_text_scale", 1.0f),
            animationsEnabled = prefs.getBoolean("setting_animations", true),
            sleepTimerMinutes = prefs.getInt("setting_sleep_timer", 0),
            highContrast = prefs.getBoolean("setting_high_contrast", false)
        )
    }

    fun saveSettings(settings: AppSettings) {
        prefs.edit().apply {
            putBoolean("setting_sound_fx", settings.soundEffects)
            putBoolean("setting_bg_music", settings.backgroundMusic)
            putBoolean("setting_narration", settings.narrationAudio)
            putFloat("setting_narration_speed", settings.narrationSpeed)
            putFloat("setting_text_scale", settings.textSizeScale)
            putBoolean("setting_animations", settings.animationsEnabled)
            putInt("setting_sleep_timer", settings.sleepTimerMinutes)
            putBoolean("setting_high_contrast", settings.highContrast)
            apply()
        }
    }
}
