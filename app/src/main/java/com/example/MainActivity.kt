package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.*
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ui.components.*
import com.example.ui.screens.*
import com.example.ui.theme.StoryLandTheme
import com.example.viewmodel.*
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val viewModel: StoryLandViewModel = viewModel()
            val userProfile by viewModel.userProfile.collectAsState()
            val appSettings by viewModel.appSettings.collectAsState()
            val currentTab by viewModel.currentBottomTab.collectAsState()
            val currentScreen by viewModel.currentScreen.collectAsState()
            val celebration by viewModel.celebration.collectAsState()

            var showParentGate by remember { mutableStateOf(false) }

            val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
            val scope = rememberCoroutineScope()

            BackHandler(enabled = currentScreen !is AppScreen.MainTabs || drawerState.isOpen) {
                if (drawerState.isOpen) {
                    scope.launch { drawerState.close() }
                } else {
                    viewModel.navigateBack()
                }
            }

            StoryLandTheme(
                textScale = appSettings.textSizeScale
            ) {
                ModalNavigationDrawer(
                    drawerState = drawerState,
                    gesturesEnabled = currentScreen is AppScreen.MainTabs,
                    drawerContent = {
                        StoryLandDrawerContent(
                            userProfile = userProfile,
                            onNavigate = { screen -> viewModel.navigateTo(screen) },
                            onCloseDrawer = { scope.launch { drawerState.close() } },
                            onOpenParentGate = { showParentGate = true }
                        )
                    }
                ) {
                    Scaffold(
                        modifier = Modifier.fillMaxSize(),
                        topBar = {
                            if (currentScreen is AppScreen.MainTabs) {
                                StoryLandTopBar(
                                    userProfile = userProfile,
                                    onMenuClick = { scope.launch { drawerState.open() } },
                                    onProfileClick = { viewModel.navigateTo(AppScreen.ProfileScreen) }
                                )
                            }
                        },
                        bottomBar = {
                            if (currentScreen is AppScreen.MainTabs) {
                                StoryLandBottomNav(
                                    selectedTab = currentTab,
                                    onTabSelected = { tab -> viewModel.selectTab(tab) }
                                )
                            }
                        }
                    ) { innerPadding ->
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(innerPadding)
                        ) {
                            when (val screen = currentScreen) {
                                is AppScreen.MainTabs -> {
                                    when (currentTab) {
                                        BottomTab.HOME -> HomeScreen(
                                            viewModel = viewModel,
                                            userProfile = userProfile,
                                            onNavigate = { viewModel.navigateTo(it) }
                                        )
                                        BottomTab.PRACTICE -> PracticeScreen(
                                            viewModel = viewModel,
                                            userProfile = userProfile,
                                            onNavigate = { viewModel.navigateTo(it) }
                                        )
                                        BottomTab.QUIZ -> QuizScreen(
                                            viewModel = viewModel,
                                            userProfile = userProfile,
                                            onNavigate = { viewModel.navigateTo(it) }
                                        )
                                        BottomTab.ABOUT_US -> AboutUsScreen()
                                    }
                                }
                                is AppScreen.Reader -> StoryReaderScreen(
                                    viewModel = viewModel,
                                    onBack = { viewModel.navigateBack() }
                                )
                                is AppScreen.FairyTaleBuilder -> FairyTaleBuilderScreen(
                                    viewModel = viewModel,
                                    onBack = { viewModel.navigateBack() }
                                )
                                is AppScreen.CreateStory -> CreateStoryScreen(
                                    viewModel = viewModel,
                                    onBack = { viewModel.navigateBack() }
                                )
                                is AppScreen.CharacterCreator -> CharacterCreatorScreen(
                                    viewModel = viewModel,
                                    onBack = { viewModel.navigateBack() }
                                )
                                is AppScreen.StorySequencing -> StorySequencingScreen(
                                    viewModel = viewModel,
                                    onBack = { viewModel.navigateBack() }
                                )
                                is AppScreen.DragonRescue -> DragonRescueAdventure(
                                    viewModel = viewModel,
                                    onBack = { viewModel.navigateBack() }
                                )
                                is AppScreen.HeroAdventure,
                                is AppScreen.PirateStory,
                                is AppScreen.SuperheroMission -> HeroAdventureScreen(
                                    viewModel = viewModel,
                                    onBack = { viewModel.navigateBack() }
                                )
                                is AppScreen.DetectiveKids,
                                is AppScreen.MysteryIsland -> DetectiveKidsScreen(
                                    viewModel = viewModel,
                                    onBack = { viewModel.navigateBack() }
                                )
                                is AppScreen.VoiceRecording -> VoiceRecordingScreen(
                                    viewModel = viewModel,
                                    onBack = { viewModel.navigateBack() }
                                )
                                is AppScreen.StoryLibrary -> StoryLibraryScreen(
                                    viewModel = viewModel,
                                    onNavigate = { viewModel.navigateTo(it) },
                                    onBack = { viewModel.navigateBack() }
                                )
                                is AppScreen.BedtimeLibrary -> BedtimeLibraryScreen(
                                    viewModel = viewModel,
                                    onNavigate = { viewModel.navigateTo(it) },
                                    onBack = { viewModel.navigateBack() }
                                )
                                is AppScreen.MyCollection -> MyCollectionScreen(
                                    viewModel = viewModel,
                                    onNavigate = { viewModel.navigateTo(it) },
                                    onBack = { viewModel.navigateBack() }
                                )
                                is AppScreen.ProgressOverview -> ProgressOverviewScreen(
                                    viewModel = viewModel,
                                    onBack = { viewModel.navigateBack() }
                                )
                                is AppScreen.ProfileScreen -> ProfileScreen(
                                    viewModel = viewModel,
                                    onBack = { viewModel.navigateBack() }
                                )
                                is AppScreen.SettingsScreen -> SettingsScreen(
                                    viewModel = viewModel,
                                    onBack = { viewModel.navigateBack() },
                                    onOpenParentGate = { showParentGate = true }
                                )
                                is AppScreen.ParentArea -> ParentAreaScreen(
                                    viewModel = viewModel,
                                    onBack = { viewModel.navigateBack() }
                                )
                                is AppScreen.QuizGame -> QuizGameScreen(
                                    viewModel = viewModel,
                                    quizId = screen.quizId,
                                    onBack = { viewModel.navigateBack() }
                                )
                            }
                        }
                    }
                }

                // Reward & Celebration Dialog
                celebration?.let { reward ->
                    CelebrationDialog(
                        celebration = reward,
                        onDismiss = { viewModel.dismissCelebration() }
                    )
                }

                // Grown-Up Math Verification Gate
                if (showParentGate) {
                    ParentGateDialog(
                        onGatePassed = {
                            showParentGate = false
                            viewModel.navigateTo(AppScreen.ParentArea)
                        },
                        onDismiss = { showParentGate = false }
                    )
                }
            }
        }
    }
}
