package co.com.ceiba.mobile.jhonatan.pruebadeingreso.infrastructure.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import co.com.ceiba.mobile.jhonatan.pruebadeingreso.infrastructure.data.Post
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
        databaseQuery = { /*localDataSource.getPostsForUser(userId)*/ MutableLiveData<List<Post>>() },
        networkCall = { remoteDataSource.getPostForUser(userId) },
        saveCallResult = {
            /*val user = localDataSource.getUser(userId)
            user.value?.let { userIt ->
                userIt.posts = it
                localDataSource.insert(userIt)
            }*/
        }
    )
}
