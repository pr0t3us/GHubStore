package com.androidpositive.ghubstore.data.datasource.sourcerepo

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class SourceEntity(
    @PrimaryKey(autoGenerate = true)
    val uid: Int,
    val name: String
)
