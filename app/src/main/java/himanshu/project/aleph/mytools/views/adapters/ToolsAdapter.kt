package himanshu.project.aleph.mytools.views.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import himanshu.project.aleph.mytools.data.Tools
import himanshu.project.aleph.mytools.databinding.ToolListBinding

class ToolsAdapter : ListAdapter<Tools, RecyclerView.ViewHolder>(ToolsDiffCallback()) {
    lateinit var mClickListener: ClickListener
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val tools = getItem(position)
        (holder as ToolsViewHolder).bind(tools)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ToolsViewHolder(ToolListBinding.inflate(LayoutInflater.from(parent.context), parent, false),mClickListener)
    }
    fun setOnItemClickListener(aClickListener: ClickListener) {
        mClickListener = aClickListener
    }
    interface ClickListener {
        fun onClick(tool: Tools, aView: View)
    }
    class ToolsViewHolder(private val binding: ToolListBinding ,var mClickListener: ClickListener ) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.setClickListener { binding.item?.let { tools -> mClickListener.onClick(tools,it) } }
        }
        fun bind(tool: Tools) {
            binding.apply { item = tool
                executePendingBindings()
            }

        }
    }
}

private class ToolsDiffCallback : DiffUtil.ItemCallback<Tools>() {
    override fun areItemsTheSame(oldItem: Tools, newItem: Tools): Boolean {
        return oldItem.toolId == newItem.toolId
    }
    override fun areContentsTheSame(oldItem: Tools, newItem: Tools): Boolean {
        return oldItem == newItem
    }
}