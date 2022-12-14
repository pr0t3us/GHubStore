package com.androidpositive.ghubstore.di

import android.content.Context
import androidx.room.Room
import com.androidpositive.ghubstore.data.datasource.AppDatabase
import com.androidpositive.ghubstore.data.datasource.sourcerepo.DefaultSourcesProvider
import com.androidpositive.ghubstore.data.datasource.sourcerepo.SourceDao
import com.androidpositive.ghubstore.data.datasource.sourcerepo.SourceMapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import org.kohsuke.github.GitHub
import org.kohsuke.github.GitHubBuilder
import org.mapstruct.factory.Mappers
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
    fun provideDefaultSources(@ApplicationContext context: Context): DefaultSourcesProvider {
        return DefaultSourcesProvider(context)
    }

    @Provides
    fun provideSourceMapper(): SourceMapper {
        return Mappers.getMapper(SourceMapper::class.java)
    }
}
