package co.com.ceiba.mobile.jhonatan.pruebadeingreso.ui.users

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import co.com.ceiba.mobile.jhonatan.pruebadeingreso.infrastructure.data.User
import co.com.ceiba.mobile.pruebadeingreso.databinding.UserListItemBinding

internal class UserAdapter : RecyclerView.Adapter<UserViewHolder>() {

    private val items = ArrayList<User>()

    fun setItems(items: ArrayList<User>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val binding: UserListItemBinding = UserListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UserViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) = holder.bindUser(items[position])

    override fun getItemCount(): Int = items.size
}

internal class UserViewHolder(private val itemBinding: UserListItemBinding): RecyclerView.ViewHolder(itemBinding.root as View) {

    private lateinit var user: User

    internal fun bindUser(item: User) {
        this.user = item
        itemBinding.name.text = item.name
        itemBinding.email.text = item.email
        itemBinding.phone.text = item.phone
        itemBinding.btnViewPost.setOnClickListener {
            print("publicaciones")
        }
    }
}