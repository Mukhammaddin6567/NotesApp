package uz.gita.notesapp.domain.usecase.impl

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import uz.gita.notesapp.data.model.HeaderNoteData
import uz.gita.notesapp.data.model.NoteData
import uz.gita.notesapp.domain.repository.AppRepository
import uz.gita.notesapp.domain.usecase.NotesUC
import javax.inject.Inject

class NotesUCImpl
@Inject constructor(
    private val repository: AppRepository
) : NotesUC {

    override fun headerNote() = flow<Result<HeaderNoteData>> {
        val noteData = repository.headerNote()
        if (noteData == null) emit(Result.failure(Exception()))
        else {
            val groupData = repository.groupDataByGroupId(noteData.groupId)
            val result = HeaderNoteData(
                groupName = groupData.name,
                groupIcon = groupData.icon,
                noteTitle = noteData.title,
                noteTime = noteData.time,
                noteDate = noteData.date
            )
            emit(Result.success(result))
        }
    }.flowOn(Dispatchers.IO)

    override fun notesList() = flow<Result<List<NoteData>>> {
        val result = repository.notesListByCurrentGroupId().map { it.noteEntityToNoteData() }
        if (result.isNotEmpty()) {
            result.sortedBy { it.time }
            emit(Result.success(result))
        } else emit(Result.failure(Exception()))
    }.flowOn(Dispatchers.Default)

    override fun bookmarkNote(noteId: Long, status: Boolean) = flow<Unit> {
        emit(repository.bookmarkNote(noteId, status))
    }.flowOn(Dispatchers.IO)

    override fun deleteNote(noteId: Long) = flow<Unit> {
        emit(repository.deleteNote(noteId))
    }.flowOn(Dispatchers.IO)

    /*override fun notesResultByQuery(query: String) = flow<Result<List<NoteData>>> {
        delay(500)
        val result = repository.notesResultByQuery(query).map { it.noteEntityToNoteData() }
        if (result.isNotEmpty()) {
            result.sortedBy { it.time }
            emit(Result.success(result))
        } else emit(Result.failure(Exception()))
    }.flowOn(Dispatchers.Default)*/

}