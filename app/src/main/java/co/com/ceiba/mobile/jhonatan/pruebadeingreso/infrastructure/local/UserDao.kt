package co.com.ceiba.mobile.jhonatan.pruebadeingreso.infrastructure.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import co.com.ceiba.mobile.jhonatan.pruebadeingreso.infrastructure.data.Post
import co.com.ceiba.mobile.jhonatan.pruebadeingreso.infrastructure.data.User

@Dao
internal interface UserDao {

    @Query("SELECT * FROM users")
    fun getAllUsers() : LiveData<List<User>>

    @Query("SELECT * FROM users WHERE id = :id")
    fun getUser(id: Int) : LiveData<User>

    /*@Query("SELECT posts FROM users WHERE id = :id")
    fun getPostsForUser(id: Int) : LiveData<List<Post>>*/

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(users: List<User>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(user: User)
}
