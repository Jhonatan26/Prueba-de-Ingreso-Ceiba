package co.com.ceiba.mobile.jhonatan.pruebadeingreso.infrastructure.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
internal data class User(
    @PrimaryKey
    val id: Int,
    val name: String,
    val email: String,
    val phone: String,
    var posts: List<Post>?
)
