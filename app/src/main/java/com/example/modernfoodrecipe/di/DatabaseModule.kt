package com.example.modernfoodrecipe.di

import android.content.Context
import androidx.room.Room
import com.example.modernfoodrecipe.data.Database.RecipeDao
import com.example.modernfoodrecipe.data.Database.RecipeDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context) = Room.databaseBuilder(context,RecipeDatabase::class.java,"Recipe_Database").build()

    @Provides
    @Singleton
    fun provideDao(database: RecipeDatabase) = database.RecipeDao()

}