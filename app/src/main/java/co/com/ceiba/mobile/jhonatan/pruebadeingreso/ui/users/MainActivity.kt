package co.com.ceiba.mobile.jhonatan.pruebadeingreso.ui.users

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.LinearLayout
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import co.com.ceiba.mobile.jhonatan.pruebadeingreso.infrastructure.data.User
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
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = setupViewModel()
        setupRecyclerView()
        setupObservers()
        setupFilter()
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

    private fun setupFilter() {
        binding.editTextSearch.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(p0: Editable?) {
                binding.emptyView.root.visibility = View.GONE
                filter(p0.toString())
            }

        })
    }

    private fun filter(text: String) {
        val filteredListEquals = ArrayList<User>()
        val filteredListRest = ArrayList<User>()
        val list = viewModel.users.value?.data as List<User>
        list.forEach {
            if (it.name.toLowerCase().contains(text.toLowerCase())) {
                if (it.name.toLowerCase().substring(0, text.length) == text) {
                    filteredListEquals.add(it)
                } else {
                    filteredListRest.add(it)
                }
            }
        }

        val filteredList = ArrayList(filteredListEquals.sortedBy { it.name })
        filteredList.addAll(filteredListRest.sortedBy { it.name })

        if (filteredList.isNullOrEmpty()) {
            binding.emptyView.root.visibility = View.VISIBLE
        }
        adapter.setItems(filteredList)
    }

    private fun setupObservers() {
        viewModel.users.observe(this, Observer {
            binding.emptyView.root.visibility = View.GONE
            when (it.status) {
                Resource.Status.SUCCESS -> {
                    binding.progressBar.visibility = View.GONE
                    if (it.data.isNullOrEmpty()) {
                        binding.emptyView.root.visibility = View.VISIBLE
                    }
                    else {
                        adapter.setItems(ArrayList(it.data.sortedBy { user ->  user.name }))
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