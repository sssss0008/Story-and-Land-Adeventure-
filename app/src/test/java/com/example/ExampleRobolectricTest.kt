package com.example

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.example.model.RankLevel
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [36])
class ExampleRobolectricTest {

  @Test
  fun `read string from context`() {
    val context = ApplicationProvider.getApplicationContext<Context>()
    val appName = context.getString(R.string.app_name)
    assertEquals("StoryLand Adventure", appName)
  }

  @Test
  fun `test rank level progression`() {
    assertEquals(RankLevel.READER, RankLevel.fromXp(50))
    assertEquals(RankLevel.STORY_EXPLORER, RankLevel.fromXp(120))
    assertEquals(RankLevel.WRITER, RankLevel.fromXp(400))
    assertEquals(RankLevel.STORY_CREATOR, RankLevel.fromXp(800))
    assertEquals(RankLevel.STORY_MASTER, RankLevel.fromXp(1500))
  }
}
