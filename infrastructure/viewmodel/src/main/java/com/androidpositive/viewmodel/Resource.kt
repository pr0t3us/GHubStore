package com.androidpositive.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.androidpositive.viewmodel.Resource.Failure
import com.androidpositive.viewmodel.Resource.Loading
import com.androidpositive.viewmodel.Resource.Success

sealed class Resource<out T> {
    data class Success<out T>(val data: T) : Resource<T>()
    data class Loading<out T>(val data: T? = null) : Resource<T>()
    data class Failure<out T>(val error: Throwable, val data: T? = null) : Resource<T>()

    fun asSuccess(): Success<T>? {
        return this as? Success<T>
    }

    fun asFailure(): Failure<T>? {
        return this as? Failure<T>
    }

    fun success(onSuccess: (T) -> Unit) {
        if (this is Success) onSuccess(data)
    }

    fun loading(onLoading: (T?) -> Unit) {
        if (this is Loading) onLoading(data)
    }

    fun failure(onFailure: (Throwable) -> Unit) {
        if (this is Failure) onFailure(error)
    }

    fun <R> map(transform: (T) -> R): Resource<R> = when (this) {
        is Success -> Success(transform(data))
        is Loading -> Loading(data?.let { transform(data) })
        is Failure -> Failure(error, data?.let { transform(data) })
    }
}

fun <T> MutableLiveData<Resource<T>>.onSuccess(): (T) -> Unit {
    return { this.value = Success(it) }
}

fun <T> MutableLiveData<Resource<T>>.onFailure(data: T? = null): (Throwable) -> Unit {
    return { this.value = Failure(it, data) }
}

fun <T> MutableLiveData<Resource<T>>.setLoading(data: T? = null) {
    this.value = Loading(data)
}

fun <T, R> LiveData<Resource<T>>.map(transform: (T) -> R): LiveData<Resource<R>> {
    return Transformations.map(this) {
        it.map(transform)
    }
}

fun <T> Result<T>.toResource(): Resource<T> {
    return fold(onSuccess = { Success(it) }, onFailure = { Failure(it) })
}
