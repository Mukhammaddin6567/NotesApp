package uz.gita.notesapp.presentation.viewmodels

import androidx.lifecycle.LiveData
import uz.gita.notesapp.data.model.HeaderNoteData
import uz.gita.notesapp.data.model.NoteData

interface NotesVM {

    val headerNoteLD: LiveData<HeaderNoteData>
    val notesListLD: LiveData<List<NoteData>>
    val groupNameLD: LiveData<String>
    val headerNoteStatusLD: LiveData<Boolean>
    val placeholderStatusLD: LiveData<Boolean>

    //    val searchStatusLD: LiveData<Boolean>
//    val placeholderTextLD: LiveData<Int>
    val showActionNoteDialogLD: LiveData<Long>
    val showDeleteNoteDialogLD: LiveData<Long>
    val navigateNoteEditScreenLD: LiveData<Long>

//    fun loadHeaderData()
    fun loadNotesList()
    fun onClickDismissHeaderNote()

    fun onClickNoteItem(noteId: Long)
    fun onClickNoteBookmark(noteId: Long, status: Boolean)
    fun onClickEditNote(noteId: Long)

    /*fun onClickArchiveNote(noteId: Long)*/
    fun onClickDeleteNote(noteId: Long)
    fun deleteNote(noteId: Long)
//    fun onSearchNote(query: String?)

}