package co.com.ceiba.mobile.jhonatan.pruebadeingreso

import co.com.ceiba.mobile.jhonatan.pruebadeingreso.infrastructure.remote.UserRemoteDataSource
import co.com.ceiba.mobile.jhonatan.pruebadeingreso.infrastructure.remote.UserService
import kotlinx.coroutines.runBlocking
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert.assertTrue
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class UserRemoteDataSourceTest {

    private val mockWebServer = MockWebServer()

    private val client = OkHttpClient.Builder()
        .connectTimeout(1, TimeUnit.SECONDS)
        .readTimeout(1, TimeUnit.SECONDS)
        .writeTimeout(1, TimeUnit.SECONDS)
        .build()

    private val api = Retrofit.Builder()
        .baseUrl(mockWebServer.url("https://jsonplaceholder.typicode.com/"))
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(UserService::class.java)

    private val dataSource = UserRemoteDataSource(api)

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun `getAllUsers should be return list of users`() {
        enqueueResponse(Response.users, 200)

        runBlocking {
            val users = this@UserRemoteDataSourceTest.dataSource.getAllUsers()
            assertTrue(users.data!!.isNotEmpty())
        }
    }

    @Test
    fun `getPostForUser should be return list of post from user 1`() {
        enqueueResponse(Response.posts, 200)

        runBlocking {
            val posts = this@UserRemoteDataSourceTest.dataSource.getPostForUser(1)
            assertTrue(posts.data!!.isNotEmpty())
        }
    }


    private fun enqueueResponse(jsonString: String, code: Int) {

        MockResponse().setBody(jsonString).setResponseCode(code)
    }
}