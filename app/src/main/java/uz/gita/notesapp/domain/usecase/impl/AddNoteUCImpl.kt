package uz.gita.notesapp.domain.usecase.impl

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import uz.gita.notesapp.data.model.AddNoteData
import uz.gita.notesapp.data.model.LevelNotes
import uz.gita.notesapp.domain.repository.AppRepository
import uz.gita.notesapp.domain.usecase.AddNoteUC
import javax.inject.Inject

class AddNoteUCImpl
@Inject constructor(
    private val repository: AppRepository
) : AddNoteUC {

    override fun addNote(
        time: String,
        date: String,
        title: String,
        note: String,
        level: LevelNotes,
        isBookmark: Boolean
    ) = flow<Result<Unit>> {
        val result = repository.addNote(
            AddNoteData(
                time = time,
                date = date,
                title = title,
                note = note,
                level = level.value,
                isBookmark = isBookmark
            )
        )
        if (result != null) emit(Result.success(Unit))
        else emit(Result.failure(Exception()))
    }.flowOn(Dispatchers.IO)
}