package com.androidpositive.ghubstore.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import org.kohsuke.github.GitHub
import org.kohsuke.github.GitHubBuilder
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun providesGithubClient(): GitHub = GitHubBuilder().build()
}
