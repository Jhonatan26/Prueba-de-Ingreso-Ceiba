package co.com.ceiba.mobile.jhonatan.pruebadeingreso.ui.posts

import androidx.lifecycle.ViewModel
import co.com.ceiba.mobile.jhonatan.pruebadeingreso.infrastructure.data.User
import co.com.ceiba.mobile.jhonatan.pruebadeingreso.infrastructure.repository.UserRepository

internal class PostViewModel(repository: UserRepository, internal val user: User): ViewModel() {

    internal val posts = repository.getPostForUser(user.id)
}
