package uz.gita.notesapp.domain.usecase

import kotlinx.coroutines.flow.Flow
import uz.gita.notesapp.data.model.HeaderNoteData
import uz.gita.notesapp.data.model.NoteData

interface NotesUC {

    fun headerNote(): Flow<Result<HeaderNoteData>>

    fun notesList(): Flow<Result<List<NoteData>>>

    fun bookmarkNote(noteId: Long, status: Boolean): Flow<Unit>

    fun deleteNote(noteId: Long): Flow<Unit>

    /*fun notesResultByQuery(query: String): Flow<Result<List<NoteData>>>*/

}