package com.fasa.runtracker.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.fasa.runtracker.repository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class StatisticsViewModel @Inject constructor(
    mainRepository: MainRepository
) : ViewModel() {

    val totalTimeRun = mainRepository.getTotalTimeInMillis()
    val totalDistance = mainRepository.getTotalDistance()
    val totalCaloriesBurned = mainRepository.getTotalCaloriesBurned()
    val totalAverageSpeed = mainRepository.getTotalAverageSpeed()

    val runsSortedByDate = mainRepository.getAllRunsSortedByDate()
}