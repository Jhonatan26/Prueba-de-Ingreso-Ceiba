package co.com.ceiba.mobile.jhonatan.pruebadeingreso.infrastructure.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import co.com.ceiba.mobile.jhonatan.pruebadeingreso.infrastructure.data.Post
import co.com.ceiba.mobile.jhonatan.pruebadeingreso.infrastructure.data.User

@Dao
internal interface UserDao {

    @Query("SELECT * FROM users")
    fun getAllUsers() : List<User>

    @Query("SELECT * FROM users WHERE id = :id")
    fun getUser(id: Int) : User

    @Query("SELECT * FROM posts WHERE userId = :userId")
    fun getPostsForUser(userId: Int) : List<Post>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllPosts(posts: List<Post>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(users: List<User>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(user: User)
}
