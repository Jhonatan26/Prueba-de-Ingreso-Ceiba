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
        @Volatile
        private var instance: AppDatabase? = null

        /***
         * Bases de Datos de User
         */
        fun getDatabaseUser(context: Context): AppDatabase =
            instance ?: synchronized(this) {
                instance ?: buildDatabase(
                    context,
                    "users"
                ).also { instance = it }
            }

        /***
         * Bases de Datos de Post
         */
        fun getDatabasePost(context: Context): AppDatabase =
            instance ?: synchronized(this) {
                instance ?: buildDatabase(
                    context,
                    "posts"
                ).also { instance = it }
            }

        /***
         * Build de la Database
         * [appContext] -> Contexto de aplicaciones
         * [name] -> Nombre de la vases de Datos
         */
        private fun buildDatabase(appContext: Context, name: String) =
            Room.databaseBuilder(appContext, AppDatabase::class.java, name)
                .fallbackToDestructiveMigration()
                .build()
    }
}
