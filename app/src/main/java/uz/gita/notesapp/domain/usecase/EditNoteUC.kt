package uz.gita.notesapp.domain.usecase

import kotlinx.coroutines.flow.Flow
import uz.gita.notesapp.data.model.LevelNotes
import uz.gita.notesapp.data.model.NoteData

interface EditNoteUC {

    fun noteData(noteId: Long): Flow<NoteData>

    fun editNote(
        noteId: Long,
        time: String,
        date: String,
        title: String,
        note: String,
        level: LevelNotes,
        isBookmark: Boolean
    ): Flow<Unit>
}