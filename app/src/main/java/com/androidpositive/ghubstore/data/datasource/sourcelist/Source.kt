package com.androidpositive.ghubstore.data.datasource.sourcelist

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Source(
    @PrimaryKey
    val uid: Int,
    val name: String
)
