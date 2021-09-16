package co.com.ceiba.mobile.jhonatan.pruebadeingreso.infrastructure.remote

import co.com.ceiba.mobile.jhonatan.pruebadeingreso.infrastructure.data.Post
import co.com.ceiba.mobile.jhonatan.pruebadeingreso.infrastructure.data.User
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

internal interface UserService {

    @GET("users")
    suspend fun getAllUsers() : Response<List<User>>

    @GET("posts")
    suspend fun getPostForUser(
        @Query("userId") userId: Int
    ) : Response<List<Post>>

    companion object {
        var retrofitService: UserService? = null
        fun getInstance(): UserService {
            if (retrofitService == null) {
                val retrofit = Retrofit.Builder()
                    .baseUrl("https://jsonplaceholder.typicode.com/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                retrofitService = retrofit.create(UserService::class.java)
            }
            return retrofitService!!
        }
    }
}
