package co.com.ceiba.mobile.jhonatan.pruebadeingreso.util

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import kotlinx.coroutines.Dispatchers
import co.com.ceiba.mobile.jhonatan.pruebadeingreso.util.Resource.Status.*

/***
 * Logica para la persistencia de Datos.
 * Si la Bases de Datos esta Vacia, llama al Service y guarda en la BD.
 */
fun <T> performGetOperation(
    databaseQuery: () -> T,
    networkCall: suspend () -> Resource<T>,
    saveCallResult: suspend (T) -> Unit
): LiveData<Resource<T>> =
    liveData(Dispatchers.IO) {
        emit(Resource.loading())
        val source = databaseQuery.invoke()
        val list = source as List<T>
        emit(Resource.success(source))

        // Validar lista consultada de DB
        if (list.isNullOrEmpty()) {
            emit(Resource.loading())
            val responseStatus = networkCall.invoke()
            if (responseStatus.status == SUCCESS) {
                saveCallResult(responseStatus.data!!)
                emit(responseStatus)
            } else if (responseStatus.status == ERROR) {
                emit(Resource.error(responseStatus.message!!))
            }
        }
    }