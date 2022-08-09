package uz.gita.notesapp.domain.usecase.impl

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import uz.gita.notesapp.data.model.EditNoteData
import uz.gita.notesapp.data.model.LevelNotes
import uz.gita.notesapp.data.model.NoteData
import uz.gita.notesapp.domain.repository.AppRepository
import uz.gita.notesapp.domain.usecase.EditNoteUC
import javax.inject.Inject

class EditNoteUCImpl
@Inject constructor(
    private val repository: AppRepository
) : EditNoteUC {

    override fun noteData(noteId: Long) = flow<NoteData> {
        emit(repository.noteDataById(noteId).noteEntityToNoteData())
    }.flowOn(Dispatchers.Main)

    override fun editNote(
        noteId: Long,
        time: String,
        date: String,
        title: String,
        note: String,
        level: LevelNotes,
        isBookmark: Boolean
    ) = flow<Unit> {
        val result = repository.editNote(
            EditNoteData(
                id = noteId,
                time = time,
                date = date,
                title = title,
                note = note,
                level = level.value,
                isBookmark = isBookmark
            )
        )
        emit(Unit)
    }.flowOn(Dispatchers.IO)
}