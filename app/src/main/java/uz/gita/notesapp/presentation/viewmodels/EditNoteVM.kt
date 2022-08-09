package uz.gita.notesapp.presentation.viewmodels

import androidx.lifecycle.LiveData
import uz.gita.notesapp.data.model.LevelNotes

interface EditNoteVM {

    val popBackStackLD: LiveData<Unit>
    val errorMessageLD: LiveData<Int>
    val setInitialTimeLD: LiveData<String>
    val setInitialDateLD: LiveData<String>
    val setInitialTitleLD: LiveData<String>
    val setInitialNoteLD: LiveData<String>
    val setInitialLevelLD: LiveData<Pair<Int, Int>>
    val setInitialBookmarkLD: LiveData<Pair<Int, Int>>
    val showTimePickerDialogLD: LiveData<Unit>
    val showDatePickerDialogLD: LiveData<Unit>
    val showLevelDialogLD: LiveData<Unit>
    val setLevelLD: LiveData<Pair<Int, Int>>
    val bookmarkStatusLD: LiveData<Boolean>
    val setBookmarkLD: LiveData<Pair<Int, Int>>

    fun onClickBackButton()
    fun onClickEditNoteButton(
        noteId: Long,
        time: String,
        date: String,
        title: String,
        note: String
    )

    fun init(noteId: Long)
    fun onClickTimeButton()
    fun onClickDateButton()
    fun onClickLevelButton()
    fun onClickLevelDialog(level: LevelNotes)
    fun onClickBookmarkButton()

}