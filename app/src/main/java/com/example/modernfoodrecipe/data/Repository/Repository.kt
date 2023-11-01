package com.example.modernfoodrecipe.data.Repository

import com.example.modernfoodrecipe.data.LocalDataSource
import com.example.modernfoodrecipe.data.RemoteDataSource
import dagger.hilt.android.scopes.ActivityRetainedScoped
import javax.inject.Inject
import javax.inject.Singleton

@ActivityRetainedScoped
class Repository @Inject constructor( remoteDataSource: RemoteDataSource,localDataSource: LocalDataSource) {

    val remote = remoteDataSource
    val localData = localDataSource
}