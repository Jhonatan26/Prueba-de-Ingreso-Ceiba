package co.com.ceiba.mobile.jhonatan.pruebadeingreso.ui.users

import androidx.lifecycle.ViewModel
import co.com.ceiba.mobile.jhonatan.pruebadeingreso.infrastructure.repository.UserRepository

/***
 * Parametro: [UserRepository]
 */
internal class UserViewModel(repository: UserRepository): ViewModel() {

    /***
     * Obtiene todos los usuarios desde el [UserRepository]
     * return [List] de tipo [User]
     */
    internal val users = repository.getAllUsers()
}