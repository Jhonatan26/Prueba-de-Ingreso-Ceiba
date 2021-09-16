package co.com.ceiba.mobile.jhonatan.pruebadeingreso.ui.posts

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import co.com.ceiba.mobile.jhonatan.pruebadeingreso.infrastructure.data.User
import co.com.ceiba.mobile.jhonatan.pruebadeingreso.infrastructure.local.AppDatabase
import co.com.ceiba.mobile.jhonatan.pruebadeingreso.infrastructure.remote.UserRemoteDataSource
import co.com.ceiba.mobile.jhonatan.pruebadeingreso.infrastructure.remote.UserService
import co.com.ceiba.mobile.jhonatan.pruebadeingreso.infrastructure.repository.UserRepository
import co.com.ceiba.mobile.jhonatan.pruebadeingreso.util.Resource
import co.com.ceiba.mobile.pruebadeingreso.databinding.ActivityPostBinding

internal class PostActivity : AppCompatActivity(){

    private lateinit var binding: ActivityPostBinding
    private lateinit var viewModel: PostViewModel
    private lateinit var adapter: PostAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPostBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = setupViewModel()
        setupRecyclerView()
        setupObservers()
    }

    private fun setupViewModel(): PostViewModel {
        val userService = UserService.getInstance()
        val userDataSource = UserRemoteDataSource(userService)
        val database = AppDatabase.getDatabasePost(this)
        val userDao = database.userDao()
        val repository = UserRepository(userDataSource, userDao)
        val user = intent.getParcelableExtra<User>("user")
        binding.name.text = user.name
        binding.email.text = user.email
        binding.phone.text = user.phone

        return ViewModelProvider(this, PostViewModelFactory(repository, user))
            .get(PostViewModel::class.java)
    }

    private fun setupRecyclerView() {
        adapter = PostAdapter()
        binding.recyclerViewPostsResults.layoutManager = LinearLayoutManager(this)
        binding.recyclerViewPostsResults.adapter = adapter
    }

    private fun setupObservers() {
        viewModel.posts.observe(this, Observer {
            binding.emptyView.root.visibility = View.GONE
            when (it.status) {
                Resource.Status.SUCCESS -> {
                    binding.progressBar.visibility = View.GONE
                    if (it.data.isNullOrEmpty()) {
                        binding.emptyView.root.visibility = View.VISIBLE
                    }
                    else {
                        adapter.setItems(ArrayList(it.data.sortedBy { post ->  post.title }))
                    }
                }
                Resource.Status.ERROR ->
                    Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()

                Resource.Status.LOADING ->
                    binding.progressBar.visibility = View.VISIBLE
            }
        })
    }
}