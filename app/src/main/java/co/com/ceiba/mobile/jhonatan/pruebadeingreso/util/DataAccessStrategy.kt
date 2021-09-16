package co.com.ceiba.mobile.jhonatan.pruebadeingreso.util

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.map
import kotlinx.coroutines.Dispatchers
import co.com.ceiba.mobile.jhonatan.pruebadeingreso.util.Resource.Status.*

fun <T> performGetOperation(databaseQuery: () -> T,
                               networkCall: suspend () -> Resource<T>,
                               saveCallResult: suspend (T) -> Unit): LiveData<Resource<T>> =
    liveData(Dispatchers.IO) {
        emit(Resource.loading())
        val source = databaseQuery.invoke()
        val list = source as List<T>
        emit(Resource.success(source))


        if (list.isNullOrEmpty()) {
            emit(Resource.loading())
            val responseStatus = networkCall.invoke()
            if (responseStatus.status == SUCCESS) {
                saveCallResult(responseStatus.data!!)
                emit(responseStatus)
            } else if (responseStatus.status == ERROR) {
                emit(Resource.error(responseStatus.message!!))
                emit(Resource.success(source))
            }
        }
    }