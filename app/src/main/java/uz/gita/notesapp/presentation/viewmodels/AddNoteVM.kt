package uz.gita.notesapp.presentation.viewmodels

import androidx.lifecycle.LiveData
import uz.gita.notesapp.data.model.LevelNotes

interface AddNoteVM {

    val popBackStackLD: LiveData<Unit>
    val errorMessageLD: LiveData<Int>
    val setInitialTimeLD: LiveData<Unit>
    val setInitialDateLD: LiveData<Unit>
    val showTimePickerDialogLD: LiveData<Unit>
    val showDatePickerDialogLD: LiveData<Unit>
    val showLevelDialogLD: LiveData<Unit>
    val setLevelLD: LiveData<Pair<Int, Int>>
    val bookmarkStatusLD: LiveData<Boolean>
    val setBookmarkLD: LiveData<Pair<Int, Int>>

    fun onClickBackButton()
    fun onClickSaveNoteButton(
        time: String,
        date: String,
        title: String,
        note: String
    )
    fun onClickTimeButton()
    fun onClickDateButton()
    fun onClickLevelButton()
    fun onClickLevelDialog(level: LevelNotes)
    fun onClickBookmarkButton()

}