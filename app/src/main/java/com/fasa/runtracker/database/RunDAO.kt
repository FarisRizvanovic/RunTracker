package com.fasa.runtracker.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.fasa.runtracker.model.Run

@Dao
interface RunDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRun(run: Run)

    @Delete
    suspend fun deleteRun(run: Run)

    @Query("SELECT * FROM run_table ORDER BY timestamp DESC ")
    fun getAllRunsSortedByDate() : LiveData<List<Run>>

    @Query("SELECT * FROM run_table ORDER BY timeInMilliseconds DESC ")
    fun getAllRunsSortedByTimeInMillis() : LiveData<List<Run>>

    @Query("SELECT * FROM run_table ORDER BY caloriesBurned DESC ")
    fun getAllRunsSortedByCaloriesBurned() : LiveData<List<Run>>

    @Query("SELECT * FROM run_table ORDER BY averageSpeedInKMH DESC ")
    fun getAllRunsSortedByAverageSpeed() : LiveData<List<Run>>

    @Query("SELECT * FROM run_table ORDER BY distanceInMeters DESC ")
    fun getAllRunsSortedByDistance() : LiveData<List<Run>>

    @Query("SELECT SUM(timeInMilliseconds) FROM run_table")
    fun getTotalTimeInMillis() : LiveData<Long>

    @Query("SELECT SUM(caloriesBurned) FROM run_table")
    fun getTotalCaloriesBurned() : LiveData<Int>

    @Query("SELECT SUM(distanceInMeters) FROM run_table")
    fun getTotalDistance() : LiveData<Int>

    @Query("SELECT AVG(averageSpeedInKMH) FROM run_table")
    fun getTotalAverageSpeed() : LiveData<Float>
}