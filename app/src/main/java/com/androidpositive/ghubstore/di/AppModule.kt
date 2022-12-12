package com.androidpositive.ghubstore.di

import android.content.Context
import androidx.room.Room
import com.androidpositive.ghubstore.data.datasource.AppDatabase
import com.androidpositive.ghubstore.data.datasource.sourcelist.SourceDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
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

    @Provides
    @Singleton
    fun providesSourceListDatabase(
        @ApplicationContext appContext: Context
    ): AppDatabase = Room.databaseBuilder(
        appContext,
        AppDatabase::class.java, "application-database"
    ).build()

    @Provides
    fun provideSourceDao(appDatabase: AppDatabase): SourceDao {
        return appDatabase.getSourceDao()
    }
}
