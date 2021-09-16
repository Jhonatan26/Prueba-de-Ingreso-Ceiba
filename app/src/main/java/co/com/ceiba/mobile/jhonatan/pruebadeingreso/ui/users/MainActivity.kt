package co.com.ceiba.mobile.jhonatan.pruebadeingreso.ui.users

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import co.com.ceiba.mobile.jhonatan.pruebadeingreso.infrastructure.local.AppDatabase
import co.com.ceiba.mobile.jhonatan.pruebadeingreso.infrastructure.remote.UserRemoteDataSource
import co.com.ceiba.mobile.jhonatan.pruebadeingreso.infrastructure.remote.UserService
import co.com.ceiba.mobile.jhonatan.pruebadeingreso.infrastructure.repository.UserRepository
import co.com.ceiba.mobile.jhonatan.pruebadeingreso.util.Resource
import co.com.ceiba.mobile.pruebadeingreso.R
import co.com.ceiba.mobile.pruebadeingreso.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: UserViewModel
    private lateinit var adapter: UserAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        viewModel = setupViewModel()
        setupRecyclerView()
        setupObservers()
    }

    private fun setupViewModel(): UserViewModel {
        val userService = UserService.getInstance()
        val userDataSource = UserRemoteDataSource(userService)
        val database = AppDatabase.getDatabase(this)
        val userDao = database.userDao()
        val repository = UserRepository(userDataSource, userDao)

        return ViewModelProvider(this, UserViewModelFactory(repository))
            .get(UserViewModel::class.java)
    }

    private fun setupRecyclerView() {
        adapter = UserAdapter()
        binding.recyclerViewSearchResults.layoutManager = LinearLayoutManager(this)
        binding.recyclerViewSearchResults.adapter = adapter
    }

    private fun setupObservers() {
        viewModel.users.observe(this, Observer {
            when (it.status) {
                Resource.Status.SUCCESS -> {
                    binding.progressBar.visibility = View.GONE
                    if (!it.data.isNullOrEmpty()) adapter.setItems(ArrayList(it.data))
                }
                Resource.Status.ERROR ->
                    Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()

                Resource.Status.LOADING ->
                    binding.progressBar.visibility = View.VISIBLE
            }
        })
    }
}