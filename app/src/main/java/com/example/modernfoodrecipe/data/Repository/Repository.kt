package com.example.modernfoodrecipe.data.Repository

import dagger.hilt.android.scopes.ActivityRetainedScoped
import javax.inject.Inject

@ActivityRetainedScoped
class Repository @Inject constructor(remoteDataSource: RemoteDataSource, localDataSource: LocalDataSource) {

    val remote = remoteDataSource
    val localData = localDataSource
}