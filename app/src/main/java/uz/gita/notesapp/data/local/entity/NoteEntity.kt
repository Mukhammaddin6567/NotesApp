package uz.gita.notesapp.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import uz.gita.notesapp.data.model.NoteData

@Entity(tableName = "notes")
data class NoteEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val groupId: Int,
    val time: String,
    val date: String,
    val title: String,
    val note: String,
    val level: Int,
    val isBookmark: Boolean,
) {
    fun noteEntityToNoteData(): NoteData = NoteData(
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