package co.com.ceiba.mobile.jhonatan.pruebadeingreso.infrastructure.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "posts")
internal data class Post (
    @PrimaryKey
    val id: Int,
    val userId: Int,
    val title: String,
    val body: String
)