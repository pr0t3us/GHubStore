package com.androidpositive.ghubstore.data.datasource

import androidx.room.Database
import androidx.room.RoomDatabase
import com.androidpositive.ghubstore.data.datasource.sourcerepo.SourceEntity
import com.androidpositive.ghubstore.data.datasource.sourcerepo.SourceDao

@Database(version = 1, entities = [SourceEntity::class])
abstract class AppDatabase : RoomDatabase() {
    abstract fun getSourceDao(): SourceDao
}
