package com.mahinurbulanikoglu.emotimate.ui.home.detail

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import com.mahinurbulanikoglu.emotimate.databinding.FragmentMeditationDetailBinding
import com.mahinurbulanikoglu.emotimate.R
import com.mahinurbulanikoglu.emotimate.service.MediaManager
import com.mahinurbulanikoglu.emotimate.ui.home.HomeViewModel
import java.util.concurrent.TimeUnit

class MeditationDetailFragment : Fragment() {

    private var _binding: FragmentMeditationDetailBinding? = null
    private val binding get() = _binding!!

    private val viewModel: HomeViewModel by activityViewModels()
    private lateinit var mediaManager: MediaManager

    private val args: MeditationDetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMeditationDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mediaManager = MediaManager.getInstance(requireContext())

        val meditationItem = args.meditationItem
        binding.imageMeditation.setImageResource(meditationItem.imageResId)
        binding.textMeditationTitle.text = meditationItem.title

        meditationItem.audioResId?.let { audioResId ->
            mediaManager.initializeMediaPlayer(audioResId)
            setupMediaPlayerControls()
            observeMediaPlayerState()
        }

        binding.btnPlayPause.setOnClickListener {
            mediaManager.togglePlayback()
        }

        setupSeekBar()
    }

    private fun setupMediaPlayerControls() {
        mediaManager.duration.observe(viewLifecycleOwner) { duration ->
            binding.seekBar.max = duration
            binding.tvDuration.text = formatTime(duration.toLong())
        }
    }

    private fun observeMediaPlayerState() {
        mediaManager.isPlaying.observe(viewLifecycleOwner) { isPlaying ->
            binding.btnPlayPause.setImageResource(
                if (isPlaying) R.drawable.pause else R.drawable.ic_play
            )
        }

        mediaManager.currentPosition.observe(viewLifecycleOwner) { position ->
            binding.seekBar.progress = position
            binding.tvCurrentTime.text = formatTime(position.toLong())
        }
    }

    private fun setupSeekBar() {
        binding.seekBar.setOnSeekBarChangeListener(object : android.widget.SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: android.widget.SeekBar?, progress: Int, fromUser: Boolean) {
                if (fromUser) {
                    mediaManager.seekTo(progress)
                }
            }
            override fun onStartTrackingTouch(seekBar: android.widget.SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: android.widget.SeekBar?) {}
        })
    }

    private fun formatTime(milliseconds: Long): String {
        val minutes = TimeUnit.MILLISECONDS.toMinutes(milliseconds)
        val seconds = TimeUnit.MILLISECONDS.toSeconds(milliseconds) -
                TimeUnit.MINUTES.toSeconds(minutes)
        return String.format("%02d:%02d", minutes, seconds)
    }

    override fun onPause() {
        super.onPause()
        mediaManager.releaseMediaPlayer()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mediaManager.releaseMediaPlayer()
        _binding = null
    }
}
