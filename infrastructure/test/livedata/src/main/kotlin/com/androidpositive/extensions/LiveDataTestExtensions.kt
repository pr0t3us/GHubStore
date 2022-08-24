package com.androidpositive.extensions

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.withTimeout
import org.junit.Assert.assertEquals

/**
 * Represents a list of capture values from a LiveData.
 *
 * This class is not threadsafe and must be used from the main thread.
 */
@VisibleForTesting(otherwise = VisibleForTesting.NONE)
class LiveDataValueCapture<T> {
    private val _values = mutableListOf<T?>()
    private val values: List<T?>
        get() = _values
    private val channel = Channel<T?>(Channel.UNLIMITED)

    fun addValue(value: T?) {
        _values += value
        channel.trySend(value).isSuccess
    }

    suspend fun assertSendsValues(vararg expected: T?, timeout: Long = 0) {
        val expectedList = expected.asList()
        if (values == expectedList) {
            return
        }
        try {
            withTimeout(timeout) {
                for (value in channel) {
                    if (values == expectedList) {
                        return@withTimeout
                    }
                }
            }
        } catch (ex: TimeoutCancellationException) {
            assertEquals(expectedList, values)
        }
    }
}

@VisibleForTesting(otherwise = VisibleForTesting.NONE)
fun <T> LiveData<T>.createCaptor(): LiveDataValueCapture<T> {
    val capture = LiveDataValueCapture<T>()
    val observer = Observer<T> { capture.addValue(it) }
    observeForever(observer)
    return capture
}

/**
 * Extension function to capture all values that are emitted to a LiveData<T> during the execution
 * of `captureBlock`.
 *
 * @param block a lambda that will capture LiveData values
 */
@VisibleForTesting(otherwise = VisibleForTesting.NONE)
inline fun <T> LiveData<T>.captureValues(block: LiveDataValueCapture<T>.() -> Unit) {
    val capture = LiveDataValueCapture<T>()
    val observer = Observer<T> {
        capture.addValue(it)
    }
    observeForever(observer)
    try {
        capture.block()
    } finally {
        removeObserver(observer)
    }
}

/**
 * Get the current value from a LiveData without needing to register an observer.
 */
@VisibleForTesting(otherwise = VisibleForTesting.NONE)
fun <T> LiveData<T>.getValueForTest(): T? {
    var value: T? = null
    val observer = Observer<T> {
        value = it
    }
    observeForever(observer)
    removeObserver(observer)
    return value
}
