package com.androidpositive.ghubstore.data.datasource

import androidx.room.Database
import androidx.room.RoomDatabase
import com.androidpositive.ghubstore.data.datasource.sourcelist.Source
import com.androidpositive.ghubstore.data.datasource.sourcelist.SourceDao

@Database(version = 1, entities = [Source::class])
abstract class AppDatabase : RoomDatabase() {
    abstract fun getSourceDao(): SourceDao
}
