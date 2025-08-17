package com.example.githubuserlist.data.di

import com.example.githubuserlist.data.network.ApiService
import com.example.githubuserlist.data.repository.GithubUserRepositoryImpl
import com.example.githubuserlist.domain.repository.GithubUserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object GithubUserModule {

    @Provides
    @Singleton
    fun provideGithubUserRepository(
        apiService: ApiService
    ): GithubUserRepository {
        return GithubUserRepositoryImpl(apiService)
    }
}