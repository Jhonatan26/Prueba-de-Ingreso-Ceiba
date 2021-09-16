package co.com.ceiba.mobile.jhonatan.pruebadeingreso.infrastructure.data

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "users")
@Parcelize
internal data class User(
    @PrimaryKey
    val id: Int,
    val name: String,
    val email: String,
    val phone: String
) : Parcelable
