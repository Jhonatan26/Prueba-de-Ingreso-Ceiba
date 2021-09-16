package co.com.ceiba.mobile.jhonatan.pruebadeingreso.ui.users

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import co.com.ceiba.mobile.jhonatan.pruebadeingreso.infrastructure.data.User
import co.com.ceiba.mobile.pruebadeingreso.databinding.UserListItemBinding

internal class UserAdapter(private val listener: BtnViewPostItemListener) :
    RecyclerView.Adapter<UserViewHolder>() {

    internal interface BtnViewPostItemListener {
        fun onViewPost(user: User)
    }

    private val items = ArrayList<User>()

    fun setItems(items: ArrayList<User>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val binding: UserListItemBinding =
            UserListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UserViewHolder(binding, listener)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) =
        holder.bindUser(items[position])

    override fun getItemCount(): Int = items.size
}

internal class UserViewHolder(
    private val itemBinding: UserListItemBinding,
    private val listener: UserAdapter.BtnViewPostItemListener
) : RecyclerView.ViewHolder(itemBinding.root as View), View.OnClickListener {

    private lateinit var user: User

    init {
        itemBinding.root.setOnClickListener(this)
    }

    internal fun bindUser(item: User) {
        this.user = item
        itemBinding.name.text = item.name
        itemBinding.email.text = item.email
        itemBinding.phone.text = item.phone
    }

    override fun onClick(p0: View?) {
        listener.onViewPost(user)
    }
}