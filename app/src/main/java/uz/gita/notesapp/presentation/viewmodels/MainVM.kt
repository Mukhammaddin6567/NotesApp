package uz.gita.notesapp.presentation.viewmodels

import androidx.lifecycle.LiveData

interface MainVM {

    val toolbarTitleLD: LiveData<Int>
    val addNoteStatusLD: LiveData<Boolean>
    val navigateNextScreenLD: LiveData<Int>
    val addNoteScreenLD: LiveData<Unit>
    val errorMessageLD: LiveData<Int>
    val showSortingDialogLD: LiveData<Unit>

//    fun onClickSortingMenu()
    fun onClickMenuButton(position: Int)
    fun onClickAddNotes()
}