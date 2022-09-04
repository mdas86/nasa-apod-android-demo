package com.example.nasaapod.ui.home.details

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import coil.load
import com.example.nasaapod.base.BaseFragment
import com.example.nasaapod.databinding.FragmentPictureOfDayBinding
import com.example.nasaapod.ui.home.HomeFragment
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PictureOfDayFragment : BaseFragment<FragmentPictureOfDayBinding>() {

    private val viewModel: PictureOfDayModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val prefSelectedDate: SharedPreferences =
            requireActivity().getSharedPreferences(HomeFragment.SELECTED_DATE, Context.MODE_PRIVATE)
        if(prefSelectedDate.getString(HomeFragment.SELECTED_DATE, null) != null) {
            val dateString = prefSelectedDate.getString(HomeFragment.SELECTED_DATE, null).toString()
            viewModel.getPhotoBasedOnDate(dateString)
        }
        observePhoto()
        observeUiEvent()
    }

    private fun observePhoto() {
        viewModel.photoBasedOnDate.observe(viewLifecycleOwner) { photo ->
            binding.tvTitle.text = photo.title
            binding.tvContent.text = photo.explanation
            binding.tvDate.text = photo.date
            binding.imgApod.load(photo.url)
            binding.btnAddFavoritesFromToday.setOnClickListener {
                viewModel.onEvent(PictureOfDayEvent.AddFavoritesClicked(photo))
            }
        }
    }

    private fun observeUiEvent() {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.todayPhotoEvent.collect { event ->
                when (event) {
                    is PictureOfDayModel.UiTodayPhotoEvent.ShowSnackbar -> {
                        Snackbar.make(requireView(), event.message, Snackbar.LENGTH_LONG).show()
                    }
                    is PictureOfDayModel.UiTodayPhotoEvent.ShowProgressBar -> {
                        binding.pbLoading.isVisible = event.isLoading
                    }
                }
            }
        }
    }

    override fun initBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentPictureOfDayBinding.inflate(inflater, container, false)
}