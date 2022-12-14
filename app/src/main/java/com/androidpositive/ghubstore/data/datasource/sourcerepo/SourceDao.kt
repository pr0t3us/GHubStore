package com.androidpositive.ghubstore.data.datasource.sourcerepo

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface SourceDao {
    @Query("SELECT * FROM SourceEntity")
    fun getAll(): List<SourceEntity>

    @Query("SELECT * FROM SourceEntity WHERE uid IN (:sourceIds)")
    fun loadAllByIds(sourceIds: IntArray): List<SourceEntity>

    @Insert
    fun insertAll(vararg sources: SourceEntity)

    @Delete
    fun delete(source: SourceEntity)
}
