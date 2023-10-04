package com.androidpositive.ghubstore.domain

import dagger.hilt.android.components.ViewModelComponent
import it.czerwinski.android.hilt.annotations.BoundTo
import javax.inject.Inject

interface ValidateRepositoryNameUseCase {
    operator fun invoke(name: String): Boolean
}

@BoundTo(supertype = ValidateRepositoryNameUseCase::class, component = ViewModelComponent::class)
private class ValidateRepositoryNameUseCaseImpl @Inject constructor(
) : ValidateRepositoryNameUseCase {
    override operator fun invoke(name: String): Boolean {
        return name.contains("/")
    }
}
