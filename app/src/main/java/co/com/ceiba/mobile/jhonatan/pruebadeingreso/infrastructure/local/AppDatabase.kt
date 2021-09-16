package co.com.ceiba.mobile.jhonatan.pruebadeingreso.infrastructure.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import co.com.ceiba.mobile.jhonatan.pruebadeingreso.infrastructure.data.Post
import co.com.ceiba.mobile.jhonatan.pruebadeingreso.infrastructure.data.User

@Database(entities = [User::class, Post::class], version = 1, exportSchema = false)
internal abstract class AppDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao

    companion object {
        @Volatile private var instance: AppDatabase? = null

        fun getDatabaseUser(context: Context): AppDatabase =
            instance ?: synchronized(this) { instance ?: buildDatabase(context, "users").also { instance = it } }

        fun getDatabasePost(context: Context): AppDatabase =
            instance ?: synchronized(this) { instance ?: buildDatabase(context, "posts").also { instance = it } }

        private fun buildDatabase(appContext: Context, name: String) =
            Room.databaseBuilder(appContext, AppDatabase::class.java, name)
                .fallbackToDestructiveMigration()
                .build()
    }
}
