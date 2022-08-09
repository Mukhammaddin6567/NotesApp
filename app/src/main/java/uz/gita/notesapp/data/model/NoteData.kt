package uz.gita.notesapp.data.model

data class NoteData(
    val id: Long = 0,
    val groupId: Int,
    val time: String,
    val date: String,
    val title: String,
    val note: String,
    val level: Int,
    var isBookmark: Boolean,
)
