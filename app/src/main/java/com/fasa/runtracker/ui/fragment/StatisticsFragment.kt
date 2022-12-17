package com.fasa.runtracker.ui.fragment

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.fasa.runtracker.R
import com.fasa.runtracker.databinding.FragmentStatisticsBinding
import com.fasa.runtracker.ui.viewmodel.StatisticsViewModel
import com.fasa.runtracker.util.CustomMarkerView
import com.fasa.runtracker.util.TrackingUtil
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import dagger.hilt.android.AndroidEntryPoint
import kotlin.math.round

@AndroidEntryPoint
class StatisticsFragment : Fragment() {
    private var _binding: FragmentStatisticsBinding? = null
    private val binding get() = _binding!!

    private val viewModel: StatisticsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentStatisticsBinding.inflate(inflater, container, false)
        val view = binding.root

        subscribeToObservers()
        setupBarChart()

        return view
    }

    private fun setupBarChart() {
        binding.barChart.xAxis.apply {
            position = XAxis.XAxisPosition.BOTTOM
            setDrawLabels(false)
            axisLineColor = Color.BLACK
            textColor = Color.BLACK
            setDrawGridLines(false)
        }
        binding.barChart.axisLeft.apply {
            axisLineColor = Color.BLACK
            textColor = Color.BLACK
            setDrawGridLines(false)
        }
        binding.barChart.axisRight.apply {
            axisLineColor = Color.BLACK
            textColor = Color.BLACK
            setDrawGridLines(false)
        }
        binding.barChart.apply {
            description.text = "Average Speed Over Time"
            legend.isEnabled = false
        }
    }

    private fun subscribeToObservers() {
        viewModel.totalAverageSpeed.observe(viewLifecycleOwner) {
            it?.let {
                val averageSpeed = round(it * 10f) / 10f
                val averageSpeedString = "${averageSpeed}km/h"
                binding.tvAverageSpeed.text = averageSpeedString
            }
        }
        viewModel.totalTimeRun.observe(viewLifecycleOwner) {
            it?.let {
                val totalTimeRun = TrackingUtil.getFormattedStopWatchTime(it)
                binding.tvTotalTime.text = totalTimeRun
            }
        }
        viewModel.totalDistance.observe(viewLifecycleOwner) {
            it?.let {
                val km = it / 1000f
                val totalDistance = round(it * 10f) / 10f
                val totalDistanceString = "${totalDistance}km"
                binding.tvTotalDistance.text = totalDistanceString
            }
        }
        viewModel.totalCaloriesBurned.observe(viewLifecycleOwner) {
            it?.let {
                val totalCaloriesString = "${it}kcal"
                binding.tvTotalCalories.text = totalCaloriesString
            }
        }
        viewModel.runsSortedByDate.observe(viewLifecycleOwner) {
            it?.let {
                val allAverageSpeeds = it.indices.map { i ->
                    BarEntry(i.toFloat(), it[i].distanceInMeters.toFloat())
                }
                val barDataSet= BarDataSet(allAverageSpeeds, "Distance (in meters) Over Time").apply {
                    valueTextColor = Color.BLACK
                    color = Color.GREEN
                }
                binding.barChart.data = BarData(barDataSet)
                binding.barChart.marker = CustomMarkerView(it.reversed(),requireContext(), R.layout.marker_view)
                binding.barChart.invalidate()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}