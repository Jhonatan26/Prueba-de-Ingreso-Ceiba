package co.com.ceiba.mobile.jhonatan.pruebadeingreso.infrastructure.remote

internal class UserRemoteDataSource(
    private val remoteService: UserService
) : BaseDataSource() {

    internal suspend fun getAllUsers() = getResult {
        remoteService.getAllUsers()
    }

    internal suspend fun getPostForUser(userId: Int) = getResult {
        remoteService.getPostForUser(userId)
    }
}
