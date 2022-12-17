package com.fasa.runtracker.di

import android.app.PendingIntent
import android.app.PendingIntent.FLAG_MUTABLE
import android.app.PendingIntent.FLAG_UPDATE_CURRENT
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import com.fasa.runtracker.R
import com.fasa.runtracker.ui.MainActivity
import com.fasa.runtracker.util.Constants
import com.google.android.gms.location.FusedLocationProviderClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ServiceComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ServiceScoped

@Module
@InstallIn(ServiceComponent::class)
object ServiceModule {

    @ServiceScoped
    @Provides
    fun provideFusedLocationProviderClient(
        @ApplicationContext
        context: Context
    ) = FusedLocationProviderClient(context)

    @ServiceScoped
    @Provides
    fun provideMainActivityPendingIntent(
        @ApplicationContext
        context: Context
    ) = PendingIntent.getActivity(
        context,
        0,
        Intent(
            context,
            MainActivity::class.java
        ).also {
            it.action = Constants.ACTION_SHOW_TRACKING_FRAGMENT
        },
        FLAG_UPDATE_CURRENT or FLAG_MUTABLE
    )

    @ServiceScoped
    @Provides
    fun provideBaseNotificationBuilder(
        @ApplicationContext
        context: Context,
        pendingIntent: PendingIntent
    ) = NotificationCompat.Builder(
        context,
        Constants.NOTIFICATION_CHANNEL_ID
    ).setAutoCancel(false)
        .setOngoing(true)
        .setSmallIcon(R.drawable.ic_directions_run_black_24dp)
        .setContentTitle("Run Tracker")
        .setContentText("00:00:00")
        .setContentIntent(pendingIntent)
}