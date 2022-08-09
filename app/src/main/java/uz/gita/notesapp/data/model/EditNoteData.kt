package uz.gita.notesapp.data.model

import uz.gita.notesapp.data.local.entity.NoteEntity

data class EditNoteData(
    val id:Long,
    val time: String,
    val date: String,
    val title: String,
    val note: String,
    val level: Int,
    val isBookmark: Boolean
) {
    fun toNoteEntity(groupId: Int): NoteEntity = NoteEntity(
        id = id,
        groupId = groupId,
        time = time,
        date = date,
        title = title,
        note = note,
        level = level,
        isBookmark = isBookmark
    )
}
