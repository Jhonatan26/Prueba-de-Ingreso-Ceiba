package co.com.ceiba.mobile.jhonatan.pruebadeingreso.infrastructure.repository

import co.com.ceiba.mobile.jhonatan.pruebadeingreso.infrastructure.local.UserDao
import co.com.ceiba.mobile.jhonatan.pruebadeingreso.infrastructure.remote.UserRemoteDataSource
import co.com.ceiba.mobile.jhonatan.pruebadeingreso.util.performGetOperation

internal class UserRepository(
    private val remoteDataSource: UserRemoteDataSource,
    private val localDataSource: UserDao
) {
    fun getAllUsers() = performGetOperation(
        databaseQuery = { localDataSource.getAllUsers() },
        networkCall = { remoteDataSource.getAllUsers() },
        saveCallResult = { localDataSource.insertAll(it) }
    )

    fun getPostForUser(userId: Int) = performGetOperation(
        databaseQuery = { localDataSource.getPostsForUser(userId) },
        networkCall = { remoteDataSource.getPostForUser(userId) },
        saveCallResult = { localDataSource.insertAllPosts(it) }
    )
}
