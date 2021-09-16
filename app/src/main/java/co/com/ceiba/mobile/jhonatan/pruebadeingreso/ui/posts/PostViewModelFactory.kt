package co.com.ceiba.mobile.jhonatan.pruebadeingreso.ui.posts

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import co.com.ceiba.mobile.jhonatan.pruebadeingreso.infrastructure.data.User
import co.com.ceiba.mobile.jhonatan.pruebadeingreso.infrastructure.repository.UserRepository

internal class PostViewModelFactory(
    private val repository: UserRepository,
    private val user: User
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return PostViewModel(repository, user) as T
    }
}