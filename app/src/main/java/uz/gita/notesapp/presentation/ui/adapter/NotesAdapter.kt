package uz.gita.notesapp.presentation.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import uz.gita.notesapp.R
import uz.gita.notesapp.data.model.LevelNotes
import uz.gita.notesapp.data.model.NoteData
import uz.gita.notesapp.databinding.ItemNoteBinding

class NotesAdapter : ListAdapter<NoteData, NotesAdapter.Holder>(NotesAdapterDiffUtils) {

    private var onClickNoteItemListener: ((noteId: Long) -> Unit)? = null
    private var onClickNoteBookmarkListener: ((noteId: Long, isBookmark: Boolean) -> Unit)? = null

    private object NotesAdapterDiffUtils : DiffUtil.ItemCallback<NoteData>() {

        override fun areItemsTheSame(oldItem: NoteData, newItem: NoteData): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: NoteData, newItem: NoteData): Boolean =
            oldItem == newItem

    }

    inner class Holder(private val viewBinding: ItemNoteBinding) :
        RecyclerView.ViewHolder(viewBinding.root) {

        init {
            itemView.setOnClickListener {
                onClickNoteItemListener?.invoke(getItem(absoluteAdapterPosition).id)
            }
            viewBinding.noteBookmark.setOnClickListener {
                val data = getItem(absoluteAdapterPosition)
                data.isBookmark = !data.isBookmark
                onClickNoteBookmarkListener?.invoke(data.id, data.isBookmark)
                val image = when (data.isBookmark) {
                    false -> R.drawable.ic_bookmark_default
                    else -> R.drawable.ic_bookmark
                }
                viewBinding.noteBookmark.setImageResource(image)
            }
        }

        fun bind(): NoteData = with(viewBinding) {
            getItem(absoluteAdapterPosition).apply {
                val color = when (level) {
                    LevelNotes.LESS_IMPORTANT.value -> R.color.color_green
                    LevelNotes.IMPORTANT.value -> R.color.color_yellow
                    else -> R.color.color_red
                }
                noteLevel.background = ContextCompat.getDrawable(itemView.context, color)
                noteTimeDate.text =
                    itemView.context.getString(R.string.text_time_date_notes, time, date)
                noteTitle.text = title
                val image = when (isBookmark) {
                    false -> R.drawable.ic_bookmark_default
                    else -> R.drawable.ic_bookmark
                }
                noteBookmark.setImageResource(image)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder = Holder(
        ItemNoteBinding.bind(
            LayoutInflater.from(parent.context).inflate(R.layout.item_note, parent, false)
        )
    )

    override fun onBindViewHolder(holder: Holder, noteId: Int) {
        holder.bind()
    }

    fun setOnClickNoteItemListener(block: (noteId: Long) -> Unit) {
        onClickNoteItemListener = block
    }

    fun setOnClickNoteBookmarkListener(block: (noteId: Long, status: Boolean) -> Unit) {
        onClickNoteBookmarkListener = block
    }

}