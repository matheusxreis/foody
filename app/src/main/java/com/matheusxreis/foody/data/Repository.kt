package com.matheusxreis.foody.data

import dagger.hilt.android.scopes.ActivityRetainedScoped
import javax.inject.Inject

// @ActivityRetainedScope -> it allows the class hava a lifecycle of a Activity and
// survive the configurations changes, as a ViewModel does.

@ActivityRetainedScoped
class Repository @Inject constructor(
    remoteDataSource: RemoteDataSource
) {
    val remote = remoteDataSource
}