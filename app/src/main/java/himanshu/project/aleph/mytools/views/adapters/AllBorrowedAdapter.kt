package himanshu.project.aleph.mytools.views.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

import himanshu.project.aleph.mytools.data.ToolsBorrowed
import himanshu.project.aleph.mytools.databinding.ToolListByFriendBinding


class AllBorrowedAdapter : ListAdapter<ToolsBorrowed, RecyclerView.ViewHolder>(ToolsBorrowedDiffCallback()) {
    lateinit var mClickListener: ClickListener

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val toolsBorrowed = getItem(position)
        (holder as ToolsBorrowedViewHolder).bind(toolsBorrowed)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ToolsBorrowedViewHolder(ToolListByFriendBinding.inflate(LayoutInflater.from(parent.context), parent, false),mClickListener)
    }
    fun setOnItemClickListener(aClickListener: ClickListener) {
        mClickListener = aClickListener
    }
    interface ClickListener {
        fun onClick(toolsBorrowed: ToolsBorrowed, aView: View)
    }
    class ToolsBorrowedViewHolder(private val binding: ToolListByFriendBinding, var mClickListener: ClickListener ) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.setClickListener { binding.item?.let { toolsBorrowed -> mClickListener.onClick(toolsBorrowed,it) } }
        }
        fun bind(toolsBorrowed: ToolsBorrowed) {
            binding.apply { item = toolsBorrowed
                executePendingBindings()
            }

        }
    }
}

private class ToolsBorrowedDiffCallback : DiffUtil.ItemCallback<ToolsBorrowed>() {
    override fun areItemsTheSame(oldItem: ToolsBorrowed, newItem: ToolsBorrowed): Boolean {
        return oldItem.toolBorrowedId == newItem.toolBorrowedId
    }
    override fun areContentsTheSame(oldItem: ToolsBorrowed, newItem: ToolsBorrowed): Boolean {
        return oldItem == newItem
    }
}