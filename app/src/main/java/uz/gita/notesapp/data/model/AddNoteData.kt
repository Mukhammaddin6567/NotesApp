package uz.gita.notesapp.data.model

data class AddNoteData(
    val time: String,
    val date: String,
    val title: String,
    val note: String,
    val level: Int,
    val isBookmark: Boolean
)
