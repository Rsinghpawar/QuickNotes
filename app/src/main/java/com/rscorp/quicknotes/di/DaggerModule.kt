package com.rscorp.quicknotes.di

import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import androidx.room.RoomDatabase
import com.rscorp.quicknotes.db.CurrentNotesDao
import com.rscorp.quicknotes.db.NotesDao
import com.rscorp.quicknotes.db.NotesDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton


@Module
@InstallIn(ApplicationComponent::class)
object DaggerModule {


    @Provides
    @Singleton
    fun provideNotesDatabase(@ApplicationContext context: Context): NotesDatabase {
        return Room.databaseBuilder(context, NotesDatabase::class.java, "image_db").build()
    }


    @Provides
    @Singleton
    fun provideNotesDao(notesDatabase: NotesDatabase): NotesDao {
        return notesDatabase.getNotesDao()
    }

    @Provides
    @Singleton
    fun provideCurrentNotesDao(notesDatabase: NotesDatabase): CurrentNotesDao {
        return notesDatabase.getCurrentNotesDao()
    }


    @Provides
    @Singleton
    fun provideSharedPref(@ApplicationContext context: Context) : SharedPreferences{
        return context.getSharedPreferences( "MyPreference", Context.MODE_PRIVATE)
    }


}