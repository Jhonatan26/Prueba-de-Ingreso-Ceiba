package co.com.ceiba.mobile.jhonatan.pruebadeingreso.ui.users

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import co.com.ceiba.mobile.jhonatan.pruebadeingreso.infrastructure.repository.UserRepository

internal class UserViewModelFactory(private val repository: UserRepository): ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return UserViewModel(repository) as T
    }
}