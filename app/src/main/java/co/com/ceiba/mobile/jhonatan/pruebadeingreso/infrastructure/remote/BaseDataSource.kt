package co.com.ceiba.mobile.jhonatan.pruebadeingreso.infrastructure.remote

import retrofit2.Response
import co.com.ceiba.mobile.jhonatan.pruebadeingreso.util.Resource

abstract class BaseDataSource {

    /***
     * Unificar la Respuesta en un solo Objeto.
     */
    protected suspend fun <T> getResult(call: suspend () -> Response<T>): Resource<T> {
        try {
            val response = call()
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) return Resource.success(body)
            }
            return error(" ${response.code()} ${response.message()}")
        } catch (e: Exception) {
            return error(e.message ?: e.toString())
        }
    }

    private fun <T> error(message: String): Resource<T> {
        return Resource.error("Network call has failed for a following reason: $message")
    }
}
