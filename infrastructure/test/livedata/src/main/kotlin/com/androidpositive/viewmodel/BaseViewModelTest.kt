package com.androidpositive.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import org.junit.Rule

@ExperimentalCoroutinesApi
open class BaseViewModelTest(
    dispatcher: TestDispatcher = UnconfinedTestDispatcher()
) {
    @Rule
    @JvmField
    val rule = InstantTaskExecutorRule()

    @Rule
    @JvmField
    val coroutineScope = MainCoroutineScopeRule(dispatcher)
}
