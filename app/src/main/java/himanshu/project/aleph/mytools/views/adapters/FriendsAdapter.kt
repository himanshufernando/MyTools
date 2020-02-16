package himanshu.project.aleph.mytools.views.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import himanshu.project.aleph.mytools.data.Friends
import himanshu.project.aleph.mytools.databinding.FriendListBinding


class FriendsAdapter : ListAdapter<Friends, RecyclerView.ViewHolder>(FriendsDiffCallback()) {
    lateinit var mClickListener: ClickListener

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val friends = getItem(position)
        (holder as FriendsViewHolder).bind(friends)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return FriendsViewHolder(FriendListBinding.inflate(LayoutInflater.from(parent.context), parent, false),mClickListener)
    }
    fun setOnItemClickListener(aClickListener: ClickListener) {
        mClickListener = aClickListener
    }
    interface ClickListener {
        fun onClick(friends: Friends, aView: View)
    }
    class FriendsViewHolder(private val binding: FriendListBinding, var mClickListener: ClickListener ) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.setClickListener { binding.item?.let { friends -> mClickListener.onClick(friends,it) } }
        }
        fun bind(friends: Friends) {
            binding.apply { item = friends
                executePendingBindings()
            }

        }
    }
}

private class FriendsDiffCallback : DiffUtil.ItemCallback<Friends>() {
    override fun areItemsTheSame(oldItem: Friends, newItem: Friends): Boolean {
        return oldItem.friendId == newItem.friendId
    }
    override fun areContentsTheSame(oldItem: Friends, newItem: Friends): Boolean {
        return oldItem == newItem
    }
}