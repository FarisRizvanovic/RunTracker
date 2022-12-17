package com.fasa.runtracker.util

import android.content.Context
import android.widget.TextView
import com.fasa.runtracker.R
import com.fasa.runtracker.model.Run
import com.github.mikephil.charting.components.MarkerView
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.utils.MPPointF
import java.text.SimpleDateFormat
import java.util.*

class CustomMarkerView(
    private val runs: List<Run>,
    context: Context,
    layoutId: Int
) : MarkerView(context, layoutId) {

    override fun getOffset(): MPPointF {
        return MPPointF(-width / 2f, -height.toFloat())

    }

    override fun refreshContent(e: Entry?, highlight: Highlight?) {
        super.refreshContent(e, highlight)
        if (e == null) {
            return
        }
        val currentRunId = e.x.toInt()
        val run = runs[currentRunId]

        val calendar = Calendar.getInstance().apply {
            timeInMillis = run.timestamp
        }
        val dateFormat = SimpleDateFormat("dd.MM.yy", Locale.getDefault())
        findViewById<TextView>(R.id.tvDateMarker).text = dateFormat.format(calendar.time)
        findViewById<TextView>(R.id.tvAvgSpeedMarker).text = "${run.averageSpeedInKMH}km/h"
        findViewById<TextView>(R.id.tvDistanceMarker).text = "${run.distanceInMeters / 1000}km"
        findViewById<TextView>(R.id.tvDurationMarker).text =
            TrackingUtil.getFormattedStopWatchTime(run.timeInMilliseconds)
        findViewById<TextView>(R.id.tvCaloriesBurnedMarker).text = "${run.caloriesBurned}kcal"
    }
}