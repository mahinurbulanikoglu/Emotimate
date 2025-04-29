package com.mahinurbulanikoglu.emotimate.service

import android.content.Context
import android.media.MediaPlayer
import android.os.Handler
import android.os.Looper
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class MediaManager private constructor(private val context: Context) {

    private var mediaPlayer: MediaPlayer? = null
    private var handler: Handler? = null

    private val _isPlaying = MutableLiveData<Boolean>()
    val isPlaying: LiveData<Boolean> = _isPlaying

    private val _currentPosition = MutableLiveData<Int>()
    val currentPosition: LiveData<Int> = _currentPosition

    private val _duration = MutableLiveData<Int>()
    val duration: LiveData<Int> = _duration

    companion object {
        @Volatile
        private var instance: MediaManager? = null

        fun getInstance(context: Context): MediaManager {
            return instance ?: synchronized(this) {
                instance ?: MediaManager(context).also { instance = it }
            }
        }
    }

    fun initializeMediaPlayer(resourceId: Int) {
        releaseMediaPlayer()
        mediaPlayer = MediaPlayer.create(context, resourceId)
        _duration.value = mediaPlayer?.duration ?: 0
        _isPlaying.value = false
    }

    fun togglePlayback() {
        mediaPlayer?.let {
            if (it.isPlaying) {
                pausePlayback()
            } else {
                startPlayback()
            }
        }
    }

    private fun startPlayback() {
        mediaPlayer?.start()
        _isPlaying.value = true
        startProgressUpdate()
    }

    private fun pausePlayback() {
        mediaPlayer?.pause()
        _isPlaying.value = false
        handler?.removeCallbacksAndMessages(null)
    }

    fun seekTo(position: Int) {
        mediaPlayer?.seekTo(position)
        _currentPosition.value = position
    }

    private fun startProgressUpdate() {
        handler = Handler(Looper.getMainLooper())
        handler?.post(object : Runnable {
            override fun run() {
                mediaPlayer?.let {
                    if (it.isPlaying) {
                        _currentPosition.value = it.currentPosition
                        handler?.postDelayed(this, 100)
                    }
                }
            }
        })
    }

    fun releaseMediaPlayer() {
        handler?.removeCallbacksAndMessages(null)
        mediaPlayer?.release()
        mediaPlayer = null
        _isPlaying.value = false
        _currentPosition.value = 0
        _duration.value = 0
    }
}