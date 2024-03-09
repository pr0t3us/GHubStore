package com.androidpositive.ghubstore.data.datasource

interface Mapper<S, T> {
    fun convertToTarget(source: S): T
    fun convertToSource(target: T): S
}
