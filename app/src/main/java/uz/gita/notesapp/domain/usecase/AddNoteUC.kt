package uz.gita.notesapp.domain.usecase

import kotlinx.coroutines.flow.Flow
import uz.gita.notesapp.data.model.LevelNotes

interface AddNoteUC {
    fun addNote(
        time: String,
        date: String,
        title: String,
        note: String,
        level: LevelNotes,
        isBookmark: Boolean
    ): Flow<Result<Unit>>
}