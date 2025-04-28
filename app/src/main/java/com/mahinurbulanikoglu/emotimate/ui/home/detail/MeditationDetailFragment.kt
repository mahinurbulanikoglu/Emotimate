package com.mahinurbulanikoglu.emotimate.ui.home.detail

import android.media.MediaPlayer
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
import com.mahinurbulanikoglu.emotimate.ui.home.detail.MeditationDetailFragmentArgs
import com.mahinurbulanikoglu.emotimate.ui.home.MeditationViewModel

class MeditationDetailFragment : Fragment() {

    private var _binding: FragmentMeditationDetailBinding? = null
    private val binding get() = _binding!!

    private val viewModel: MeditationViewModel by activityViewModels()

    private var mediaPlayer: MediaPlayer? = null
    private var handler: Handler? = null
    private var isPlaying = false

    // SafeArgs kullanarak gelen argümanları almak
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

        // SafeArgs ile gelen verileri alıyoruz
        val meditationItem = args.meditationItem
        binding.imageMeditation.setImageResource(meditationItem.imageResId)
        binding.textMeditationTitle.text = meditationItem.title

        meditationItem.audioResId?.let {
            mediaPlayer = MediaPlayer.create(requireContext(), it)
        }

        binding.btnPlayPause.setOnClickListener {
            togglePlayback()
        }

        setupSeekBar()
    }

    private fun togglePlayback() {
        if (mediaPlayer == null) return

        if (mediaPlayer!!.isPlaying) {
            mediaPlayer!!.pause()
            isPlaying = false
            binding.btnPlayPause.setImageResource(R.drawable.ic_play)
        } else {
            mediaPlayer!!.start()
            isPlaying = true
            binding.btnPlayPause.setImageResource(R.drawable.pause)
            updateSeekBar()
        }
    }

    private fun updateSeekBar() {
        handler = Handler(Looper.getMainLooper())
        handler?.postDelayed(object : Runnable {
            override fun run() {
                mediaPlayer?.let {
                    binding.seekBar.max = it.duration
                    binding.seekBar.progress = it.currentPosition
                    if (it.isPlaying) {
                        handler?.postDelayed(this, 250)
                    }
                }
            }
        }, 0)
    }

    private fun setupSeekBar() {
        binding.seekBar.setOnSeekBarChangeListener(object : android.widget.SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: android.widget.SeekBar?, progress: Int, fromUser: Boolean) {
                if (fromUser && mediaPlayer != null) {
                    mediaPlayer!!.seekTo(progress)
                }
            }
            override fun onStartTrackingTouch(seekBar: android.widget.SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: android.widget.SeekBar?) {}
        })
    }

    private fun stopOtherSounds() {
        // Bu projede tek ses oynatılıyor, eğer başka servisler varsa burada durdurulur.
    }

    override fun onPause() {
        super.onPause()
        mediaPlayer?.pause()
        binding.btnPlayPause.setImageResource(R.drawable.ic_play)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        handler?.removeCallbacksAndMessages(null)
        mediaPlayer?.release()
        mediaPlayer = null
        _binding = null
    }
}
