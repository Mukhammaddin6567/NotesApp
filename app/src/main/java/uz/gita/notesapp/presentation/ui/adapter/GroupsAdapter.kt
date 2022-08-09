package uz.gita.notesapp.presentation.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import uz.gita.notesapp.R
import uz.gita.notesapp.data.model.GroupData
import uz.gita.notesapp.databinding.ItemGroupBinding

class GroupsAdapter : ListAdapter<GroupData, GroupsAdapter.Holder>(GroupsAdapterDiffUtils) {

    private var onClickGroupItemListener: ((id: Int) -> Unit)? = null

    private object GroupsAdapterDiffUtils : DiffUtil.ItemCallback<GroupData>() {
        override fun areItemsTheSame(oldItem: GroupData, newItem: GroupData): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: GroupData, newItem: GroupData): Boolean =
            oldItem == newItem
    }

    inner class Holder(private val viewBinding: ItemGroupBinding) :
        RecyclerView.ViewHolder(viewBinding.root) {

        init {
            viewBinding.container.setOnClickListener {
                onClickGroupItemListener?.invoke(getItem(absoluteAdapterPosition).id)
            }
        }

        fun bind(): GroupData = with(viewBinding) {
            getItem(absoluteAdapterPosition).apply {
                imageGroup.setImageResource(icon)
                textGroupName.text = itemView.context.getString(name)
                textItemsCount.text = itemView.context.getString(R.string.text_groups_count, count)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GroupsAdapter.Holder =
        Holder(
            ItemGroupBinding.bind(
                LayoutInflater.from(parent.context).inflate(R.layout.item_group, parent, false)
            )
        )

    override fun onBindViewHolder(holder: GroupsAdapter.Holder, position: Int) {
        holder.bind()
    }

    fun setOnClickGroupItemListener(block: (id: Int) -> Unit) {
        onClickGroupItemListener = block
    }
}