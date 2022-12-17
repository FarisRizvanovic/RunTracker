package com.fasa.runtracker.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.fasa.runtracker.R
import com.fasa.runtracker.model.Run
import com.fasa.runtracker.util.TrackingUtil
import java.text.SimpleDateFormat
import java.util.*

class RunAdapter : RecyclerView.Adapter<RunAdapter.RunViewHolder>() {

    inner class RunViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    val diffCallback = object : DiffUtil.ItemCallback<Run>() {
        override fun areItemsTheSame(oldItem: Run, newItem: Run): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Run, newItem: Run): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }
    }

    val differ = AsyncListDiffer(this, diffCallback)

    fun submitList(list: List<Run>) = differ.submitList(list)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RunViewHolder {
        return RunViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_run, parent, false)
        )
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: RunViewHolder, position: Int) {
        val run = differ.currentList[position]
        holder.itemView.apply {
            Glide.with(this)
                .load(run.img)
                .into(findViewById(R.id.ivRunImage))

            val calendar = Calendar.getInstance().apply {
                timeInMillis = run.timestamp
            }
            val dateFormat = SimpleDateFormat("dd.MM.yy.", Locale.getDefault())
            findViewById<TextView>(R.id.tvDate).text = dateFormat.format(calendar.time)
            findViewById<TextView>(R.id.tvAvgSpeed).text = "${run.averageSpeedInKMH}km/h"
            findViewById<TextView>(R.id.tvDistance).text = "${run.distanceInMeters/1000}km"
            findViewById<TextView>(R.id.tvTime).text = TrackingUtil.getFormattedStopWatchTime(run.timeInMilliseconds)
            findViewById<TextView>(R.id.tvCalories).text = "${run.caloriesBurned}kcal"
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }
}