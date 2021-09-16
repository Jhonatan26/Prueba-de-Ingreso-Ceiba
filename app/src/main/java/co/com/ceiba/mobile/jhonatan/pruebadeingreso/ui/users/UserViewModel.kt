package co.com.ceiba.mobile.jhonatan.pruebadeingreso.ui.users

import androidx.lifecycle.ViewModel
import co.com.ceiba.mobile.jhonatan.pruebadeingreso.infrastructure.repository.UserRepository

internal class UserViewModel(repository: UserRepository): ViewModel() {

    internal val users = repository.getAllUsers()
}