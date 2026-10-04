package com.example.audio

import android.content.Context
import android.media.AudioManager
import android.media.ToneGenerator
import android.speech.tts.TextToSpeech
import android.speech.tts.UtteranceProgressListener
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.util.Locale

class NarrationManager(private val context: Context) : TextToSpeech.OnInitListener {

    private var tts: TextToSpeech? = null
    private var isTtsReady = false

    private val _isPlaying = MutableStateFlow(false)
    val isPlaying: StateFlow<Boolean> = _isPlaying.asStateFlow()

    private val _currentSentenceIndex = MutableStateFlow(0)
    val currentSentenceIndex: StateFlow<Int> = _currentSentenceIndex.asStateFlow()

    private val _playbackSpeed = MutableStateFlow(1.0f)
    val playbackSpeed: StateFlow<Float> = _playbackSpeed.asStateFlow()

    private var sentences: List<String> = emptyList()
    private var timerJob: Job? = null
    private val scope = CoroutineScope(Dispatchers.Main + SupervisorJob())

    init {
        try {
            tts = TextToSpeech(context.applicationContext, this)
        } catch (_: Exception) {
            isTtsReady = false
        }
    }

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            tts?.let {
                val result = it.setLanguage(Locale.US)
                isTtsReady = result != TextToSpeech.LANG_MISSING_DATA && result != TextToSpeech.LANG_NOT_SUPPORTED
                it.setOnUtteranceProgressListener(object : UtteranceProgressListener() {
                    override fun onStart(utteranceId: String?) {
                        _isPlaying.value = true
                    }

                    override fun onDone(utteranceId: String?) {
                        scope.launch {
                            val nextIndex = _currentSentenceIndex.value + 1
                            if (nextIndex < sentences.size) {
                                _currentSentenceIndex.value = nextIndex
                                playSentenceAt(nextIndex)
                            } else {
                                _isPlaying.value = false
                                _currentSentenceIndex.value = 0
                            }
                        }
                    }

                    @Deprecated("Deprecated in Java")
                    override fun onError(utteranceId: String?) {
                        // Fallback to timer
                        advanceWithTimer()
                    }
                })
            }
        }
    }

    fun loadContent(paragraphs: List<String>) {
        stop()
        sentences = paragraphs.flatMap { p ->
            p.split(Regex("(?<=[.!?])\\s+")).filter { it.isNotBlank() }
        }
        _currentSentenceIndex.value = 0
    }

    fun play() {
        if (sentences.isEmpty()) return
        _isPlaying.value = true
        playSentenceAt(_currentSentenceIndex.value)
    }

    fun pause() {
        _isPlaying.value = false
        timerJob?.cancel()
        tts?.stop()
    }

    fun stop() {
        _isPlaying.value = false
        timerJob?.cancel()
        tts?.stop()
        _currentSentenceIndex.value = 0
    }

    fun nextSentence() {
        if (_currentSentenceIndex.value + 1 < sentences.size) {
            _currentSentenceIndex.value += 1
            if (_isPlaying.value) {
                playSentenceAt(_currentSentenceIndex.value)
            }
        }
    }

    fun previousSentence() {
        if (_currentSentenceIndex.value > 0) {
            _currentSentenceIndex.value -= 1
            if (_isPlaying.value) {
                playSentenceAt(_currentSentenceIndex.value)
            }
        }
    }

    fun setSpeed(speed: Float) {
        _playbackSpeed.value = speed
        tts?.setSpeechRate(speed)
    }

    private fun playSentenceAt(index: Int) {
        if (index !in sentences.indices) {
            _isPlaying.value = false
            return
        }
        val text = sentences[index]
        if (isTtsReady && tts != null) {
            tts?.setSpeechRate(_playbackSpeed.value)
            val params = android.os.Bundle()
            tts?.speak(text, TextToSpeech.QUEUE_FLUSH, params, "sentence_$index")
        } else {
            advanceWithTimer()
        }
    }

    private fun advanceWithTimer() {
        timerJob?.cancel()
        timerJob = scope.launch {
            val text = sentences.getOrNull(_currentSentenceIndex.value) ?: ""
            // Reading pace roughly ~180 words per minute / speed
            val words = text.split(" ").size.coerceAtLeast(3)
            val delayMs = ((words * 320L) / _playbackSpeed.value).toLong()
            delay(delayMs)
            if (_isPlaying.value) {
                val next = _currentSentenceIndex.value + 1
                if (next < sentences.size) {
                    _currentSentenceIndex.value = next
                    playSentenceAt(next)
                } else {
                    _isPlaying.value = false
                    _currentSentenceIndex.value = 0
                }
            }
        }
    }

    fun cleanup() {
        try {
            stop()
            tts?.shutdown()
            scope.cancel()
        } catch (_: Exception) {}
    }
}

class SoundEffectsManager(private val context: Context) {
    private var toneGen: ToneGenerator? = null

    init {
        try {
            toneGen = ToneGenerator(AudioManager.STREAM_MUSIC, 60)
        } catch (_: Exception) {}
    }

    fun playClick() {
        try {
            toneGen?.startTone(ToneGenerator.TONE_PROP_BEEP, 40)
        } catch (_: Exception) {}
    }

    fun playSuccess() {
        try {
            toneGen?.startTone(ToneGenerator.TONE_PROP_ACK, 120)
        } catch (_: Exception) {}
    }

    fun playCelebration() {
        try {
            toneGen?.startTone(ToneGenerator.TONE_CDMA_ALERT_CALL_GUARD, 200)
        } catch (_: Exception) {}
    }

    fun playPageTurn() {
        try {
            toneGen?.startTone(ToneGenerator.TONE_PROP_BEEP2, 50)
        } catch (_: Exception) {}
    }

    fun cleanup() {
        try {
            toneGen?.release()
        } catch (_: Exception) {}
    }
}
