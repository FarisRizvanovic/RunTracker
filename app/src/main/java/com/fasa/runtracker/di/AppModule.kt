package com.fasa.runtracker.di

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import androidx.room.Room
import androidx.room.RoomDatabase
import com.fasa.runtracker.model.RunDatabase
import com.fasa.runtracker.util.Constants.KEY_FIRST_TIME_TOGGLE
import com.fasa.runtracker.util.Constants.KEY_NAME
import com.fasa.runtracker.util.Constants.KEY_WEIGHT
import com.fasa.runtracker.util.Constants.RUN_DATABASE_NAME
import com.fasa.runtracker.util.Constants.SHARED_PREFERENCES_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {


    @Singleton
    @Provides
    fun provideRunDatabase(
        @ApplicationContext
        context: Context
    ) = Room.databaseBuilder(
        context,
        RunDatabase::class.java,
        RUN_DATABASE_NAME
    ).build()

    @Singleton
    @Provides
    fun provideRunDao(
        database: RunDatabase
    ) = database.getRunDao()

    @Singleton
    @Provides
    fun provideSharedPreferences(
        @ApplicationContext
        context: Context
    ) = context.getSharedPreferences(SHARED_PREFERENCES_NAME, MODE_PRIVATE)

    @Singleton
    @Provides
    fun provideName(sharedPreferences: SharedPreferences) =
        sharedPreferences.getString(KEY_NAME, "") ?: ""

    @Singleton
    @Provides
    fun provideWeight(sharedPreferences: SharedPreferences) =
        sharedPreferences.getFloat(KEY_WEIGHT, 0f)

    @Singleton
    @Provides
    fun provideFirstTimeToggle(sharedPreferences: SharedPreferences) = sharedPreferences.getBoolean(
        KEY_FIRST_TIME_TOGGLE, true
    )
}