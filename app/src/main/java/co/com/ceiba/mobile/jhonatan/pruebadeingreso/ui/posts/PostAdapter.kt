package co.com.ceiba.mobile.jhonatan.pruebadeingreso.ui.posts

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import co.com.ceiba.mobile.jhonatan.pruebadeingreso.infrastructure.data.Post
import co.com.ceiba.mobile.pruebadeingreso.databinding.PostListItemBinding

internal class PostAdapter : RecyclerView.Adapter<PostViewHolder>() {

    private val items = ArrayList<Post>()

    fun setItems(items: ArrayList<Post>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val binding: PostListItemBinding =
            PostListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PostViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) =
        holder.bindUser(items[position])

    override fun getItemCount(): Int = items.size
}

internal class PostViewHolder(private val itemBinding: PostListItemBinding) :
    RecyclerView.ViewHolder(itemBinding.root as View) {

    private lateinit var post: Post

    internal fun bindUser(item: Post) {
        this.post = item
        itemBinding.title.text = item.title
        itemBinding.body.text = item.body
    }
}