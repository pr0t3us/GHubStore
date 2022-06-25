package com.androidpositive.viewmodel

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.savedstate.SavedStateRegistryOwner
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

inline fun <reified T : ViewModel> FragmentActivity.viewModel(
    noinline provider: ((state: SavedStateHandle) -> T)? = null
) = ActivityViewModelArgumentDelegate(T::class.java, { this }, provider)

inline fun <reified T : ViewModel> Fragment.fragmentViewModel(
    noinline provider: ((state: SavedStateHandle) -> T)? = null
) = FragmentViewModelArgumentDelegate(T::class.java, this, provider)

inline fun <reified T : ViewModel> Fragment.activityViewModel(
    noinline provider: ((state: SavedStateHandle) -> T)? = null
) = ActivityViewModelArgumentDelegate(T::class.java, ::requireActivity, provider)

private class SavedStateHandleViewModelFactory<T>(
    private val viewModelClass: Class<T>,
    private val provider: (state: SavedStateHandle) -> T,
    owner: SavedStateRegistryOwner,
    defaultArgs: Bundle? = null
) : AbstractSavedStateViewModelFactory(owner, defaultArgs) {
    override fun <T : ViewModel> create(
        key: String,
        modelClass: Class<T>,
        handle: SavedStateHandle
    ): T {
        require(modelClass.isAssignableFrom(viewModelClass)) { "Unknown ViewModel $modelClass" }
        @Suppress("UNCHECKED_CAST")
        return provider.invoke(handle) as T
    }
}

class ActivityViewModelArgumentDelegate<T : ViewModel>(
    private val valueClass: Class<T>,
    private val activityProvider: () -> FragmentActivity,
    private val provider: ((state: SavedStateHandle) -> T)? = null
) : ReadOnlyProperty<Any, T> {
    private var value: T? = null

    override fun getValue(thisRef: Any, property: KProperty<*>): T {
        if (value == null) {
            value = activityProvider().retrieveViewModel(valueClass, provider)
        }
        return value ?: throw IllegalStateException("Property ${property.name} could not be read")
    }
}

class FragmentViewModelArgumentDelegate<T : ViewModel>(
    private val valueClass: Class<T>,
    private val fragment: Fragment,
    private val provider: ((state: SavedStateHandle) -> T)? = null
) : ReadOnlyProperty<Any, T> {
    private var value: T? = null

    override fun getValue(thisRef: Any, property: KProperty<*>): T {
        if (value == null) {
            val viewModelFactory =
                provider?.let { SavedStateHandleViewModelFactory(valueClass, provider, fragment) }
            value = ViewModelProvider(
                fragment, viewModelFactory ?: fragment.defaultViewModelProviderFactory
            ).get(valueClass)
        }
        return value ?: throw IllegalStateException("Property ${property.name} could not be read")
    }
}

fun <T : ViewModel> FragmentActivity.retrieveViewModel(
    valueClass: Class<T>,
    provider: ((state: SavedStateHandle) -> T)? = null
): T {
    val viewModelFactory =
        provider?.let { SavedStateHandleViewModelFactory(valueClass, provider, this) }
    return ViewModelProvider(
        this,
        viewModelFactory ?: this.defaultViewModelProviderFactory
    ).get(valueClass)
}
