package com.androidpositive.ghubstore.data.datasource.sourcerepo

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface SourceDao {
    @Query("SELECT * FROM SourceEntity")
    suspend fun getAll(): List<SourceEntity>

    @Query("SELECT * FROM SourceEntity WHERE uid IN (:sourceIds)")
    suspend fun loadAllByIds(sourceIds: IntArray): List<SourceEntity>

    @Insert
    suspend fun insertAll(vararg sources: SourceEntity)

    @Delete
    suspend fun delete(source: SourceEntity)
}
