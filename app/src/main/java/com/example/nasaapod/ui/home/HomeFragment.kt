package com.example.nasaapod.ui.home

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.*
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import coil.load
import com.example.nasaapod.R
import com.example.nasaapod.base.BaseFragment
import com.example.nasaapod.databinding.FragmentHomeBinding
import com.example.nasaapod.ui.home.details.PictureOfDayModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>() {

    companion object {
        const val SELECTED_DATE = "selectedDate"
    }

    private val viewModel: PictureOfDayModel by viewModels()
    private lateinit var selectedDatePrefEdit: SharedPreferences.Editor

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)

        val prefSelectedDate: SharedPreferences =
            requireActivity().getSharedPreferences(SELECTED_DATE, Context.MODE_PRIVATE)
        selectedDatePrefEdit = prefSelectedDate.edit()

        binding.apply {
            imgTodayApod.setImageResource(R.drawable.default_image)
            if(prefSelectedDate.getString(SELECTED_DATE, null) != null) {
                selectedDateTextView.text = prefSelectedDate.getString(SELECTED_DATE, null)
            } else {
                selectedDatePrefEdit.putString(SELECTED_DATE, selectedDateTextView.text.trim().toString()).apply()
            }
            cvToday.setOnClickListener {
                findNavController().navigate(R.id.navigateToApodTodayFragment)
            }
            btnSelectDate.setOnClickListener {
                val newFragment = DatePickerFragment()
                newFragment.show(activity?.supportFragmentManager!!, "datePicker")
            }
        }
        viewModel.getPhotoBasedOnDate(binding.selectedDateTextView.text.trim().toString())
        observePhoto()
        observeUiEvent()
    }

    private fun observePhoto() {
        viewModel.photoBasedOnDate.observe(viewLifecycleOwner) { photo ->
            binding.imgTodayTitle.text = photo.title
            binding.imgTodayApod.load(photo.url)
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

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.refresh_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.refresh -> {
                viewModel.getPhotoBasedOnDate(binding.selectedDateTextView.text.trim().toString())
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun initBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentHomeBinding.inflate(inflater, container, false)

    fun updateDate(year: Int, month: Int, dayOfMonth: Int) {
        val c = Calendar.getInstance()
        c[Calendar.YEAR] = year
        c[Calendar.MONTH] = month
        c[Calendar.DAY_OF_MONTH] = dayOfMonth

        val monthString =
            if ((month + 1) / 10 >= 1) Integer.toString(month + 1) else "0" + (month + 1)
        val dayOfMonthString =
            if (dayOfMonth / 10 >= 1) Integer.toString(dayOfMonth) else "0$dayOfMonth"
        val mCurrentDateFormatInput = "$year-$monthString-$dayOfMonthString"
        binding.apply {
            selectedDateTextView.text = mCurrentDateFormatInput
        }
        selectedDatePrefEdit.putString(SELECTED_DATE, mCurrentDateFormatInput).apply()
        viewModel.getPhotoBasedOnDate(binding.selectedDateTextView.text.trim().toString())
    }
}