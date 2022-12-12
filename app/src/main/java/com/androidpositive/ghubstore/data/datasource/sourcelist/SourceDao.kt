package com.androidpositive.ghubstore.data.datasource.sourcelist

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface SourceDao {
    @Query("SELECT * FROM Source")
    fun getAll(): List<Source>

    @Query("SELECT * FROM Source WHERE uid IN (:sourceIds)")
    fun loadAllByIds(sourceIds: IntArray): List<Source>

    @Insert
    fun insertAll(vararg sources: Source)

    @Delete
    fun delete(source: Source)
}
