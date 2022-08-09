package uz.gita.notesapp.presentation.viewmodels.impl

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import uz.gita.notesapp.data.model.HeaderNoteData
import uz.gita.notesapp.data.model.NoteData
import uz.gita.notesapp.domain.usecase.NotesUC
import uz.gita.notesapp.presentation.viewmodels.NotesVM
import javax.inject.Inject

@HiltViewModel
class NotesVMImpl
@Inject constructor(
    private val notesUC: NotesUC
) : ViewModel(), NotesVM {

    override val headerNoteLD: MutableLiveData<HeaderNoteData> by lazy { MutableLiveData<HeaderNoteData>() }
    override val notesListLD: MutableLiveData<List<NoteData>> by lazy { MutableLiveData<List<NoteData>>() }
    override val groupNameLD: MutableLiveData<String> by lazy { MutableLiveData<String>() }
    override val headerNoteStatusLD: MutableLiveData<Boolean> by lazy { MutableLiveData<Boolean>() }
    override val placeholderStatusLD: MutableLiveData<Boolean> by lazy { MutableLiveData<Boolean>() }

    //    override val searchStatusLD: MutableLiveData<Boolean> by lazy { MutableLiveData<Boolean>() }
//    override val placeholderTextLD: MutableLiveData<Int> by lazy { MutableLiveData<Int>() }
    override val showActionNoteDialogLD: MutableLiveData<Long> by lazy { MutableLiveData<Long>() }
    override val showDeleteNoteDialogLD: MutableLiveData<Long> by lazy { MutableLiveData<Long>() }
    override val navigateNoteEditScreenLD: MutableLiveData<Long> by lazy { MutableLiveData<Long>() }

    /*private var job: Job? = null*/

    init {
        loadHeaderData()
    }

    private fun loadHeaderData() {
        notesUC
            .headerNote()
            .onEach { result ->
                result.onSuccess { headerNoteData ->
                    headerNoteStatusLD.value = true
                    headerNoteLD.value = headerNoteData
                    placeholderStatusLD.value = false
                }
                result.onFailure { placeholderStatusLD.value = true }
            }
            .launchIn(viewModelScope)
    }

    override fun loadNotesList() {
        notesUC
            .notesList()
            .onEach { result ->
                result.onSuccess { list ->
                    notesListLD.value = list.toMutableList()
                    placeholderStatusLD.value = false
                }
                result.onFailure {
//                    placeholderTextLD.value = R.string.text_notes_empty_description
                    placeholderStatusLD.value = true
                }
            }
            .launchIn(viewModelScope)
    }

    override fun onClickDismissHeaderNote() {
        headerNoteStatusLD.value = false
    }

    override fun onClickNoteItem(noteId: Long) {
        showActionNoteDialogLD.value =noteId
    }

    override fun onClickNoteBookmark(noteId: Long, status: Boolean) {
        notesUC
            .bookmarkNote(noteId, status)
            .launchIn(viewModelScope)
    }

    override fun onClickEditNote(noteId: Long) {
        navigateNoteEditScreenLD.value = noteId
    }

    /*override fun onClickArchiveNote(noteId: Long) {

    }*/

    override fun onClickDeleteNote(noteId: Long) {
        showDeleteNoteDialogLD.value = noteId
    }

    override fun deleteNote(noteId: Long) {
        notesUC
            .deleteNote(noteId)
            .onEach {
                loadNotesList()
            }
            .launchIn(viewModelScope)
    }

    /*override fun onSearchNote(query: String?) {
        if (query.isNullOrEmpty()) {
            searchStatusLD.value = true
            placeholderTextLD.value = R.string.text_notes_search_result
            return
        }
        job?.cancel()
        job = notesUC
            .notesResultByQuery(query)
            .onEach { result ->
                searchStatusLD.value = false
                result.onSuccess { data ->
                    searchStatusLD.value = false
                    notesListLD.value = data
                }
                result.onFailure {
                    searchStatusLD.value = true
                    placeholderTextLD.value = R.string.text_notes_search_result
                }
            }.launchIn(viewModelScope)
    }*/

}