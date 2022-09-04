package com.example.nasaapod.ui.home

import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.widget.DatePicker
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.NavHostFragment
import java.util.*

class DatePickerFragment : DialogFragment(), OnDateSetListener {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        // Use the current date as the default date in the picker
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(requireContext(), this, year, month, day)
        datePickerDialog.datePicker.maxDate = System.currentTimeMillis() - 1 * 24 * 60 * 60 * 1000L
        return datePickerDialog
    }

    override fun onDateSet(view: DatePicker, year: Int, month: Int, day: Int) {
        Log.i("DatePickerFragment", "inside onDateSet")
        val navHostFragment: NavHostFragment =
            activity?.supportFragmentManager?.fragments?.get(0) as NavHostFragment
        val fragmentDemo: HomeFragment = navHostFragment.childFragmentManager.fragments[0] as HomeFragment
        fragmentDemo.updateDate(year, month, day)
    }
}