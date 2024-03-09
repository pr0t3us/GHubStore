package com.androidpositive.ghubstore.di

import android.app.DownloadManager
import android.content.Context
import androidx.room.Room
import com.androidpositive.ghubstore.data.datasource.AppDatabase
import com.androidpositive.ghubstore.data.datasource.Mapper
import com.androidpositive.ghubstore.data.datasource.sourcerepo.DefaultRepositoriesDataSource
import com.androidpositive.ghubstore.data.datasource.sourcerepo.SourceDao
import com.androidpositive.ghubstore.data.datasource.sourcerepo.SourceDto
import com.androidpositive.ghubstore.data.datasource.sourcerepo.SourceEntity
import com.androidpositive.ghubstore.data.datasource.sourcerepo.SourceMapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
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
        @ApplicationContext context: Context
    ): AppDatabase = Room.databaseBuilder(
        context,
        AppDatabase::class.java, "application-database"
    ).build()

    @Provides
    fun provideSourceDao(appDatabase: AppDatabase): SourceDao {
        return appDatabase.getSourceDao()
    }

    @Provides
    fun provideDefaultSources(
        @ApplicationContext context: Context,
        @MainDispatcher dispatcher: CoroutineDispatcher
    ): DefaultRepositoriesDataSource {
        return DefaultRepositoriesDataSource(context, dispatcher)
    }

    @Provides
    fun provideSourceMapper(): Mapper<SourceEntity, SourceDto> {
        return SourceMapper()
    }

    @Provides
    @Singleton
    fun providesDownloadManager(
        @ApplicationContext context: Context
    ): DownloadManager {
        return context.getSystemService(DownloadManager::class.java)
    }
}
