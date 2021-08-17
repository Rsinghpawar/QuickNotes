package com.rscorp.quicknotes.di

import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
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
        val database =  Room.databaseBuilder(context, NotesDatabase::class.java, "image_db")
            .addMigrations(object : Migration(1,2){
                override fun migrate(database: SupportSQLiteDatabase) {
                    database.execSQL("ALTER TABLE 'current_notes_table' ADD COLUMN 'iconPosition' Int NOT NULL DEFAULT 0")
                }

            })
            .build()
        return database
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