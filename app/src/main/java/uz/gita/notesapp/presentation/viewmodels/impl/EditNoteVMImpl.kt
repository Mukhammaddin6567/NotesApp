package uz.gita.notesapp.presentation.viewmodels.impl

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import uz.gita.notesapp.R
import uz.gita.notesapp.data.model.LevelNotes
import uz.gita.notesapp.domain.usecase.EditNoteUC
import uz.gita.notesapp.presentation.viewmodels.EditNoteVM
import javax.inject.Inject

@HiltViewModel
class EditNoteVMImpl
@Inject constructor(
    private val editNoteUC: EditNoteUC
) : ViewModel(), EditNoteVM {

    override val popBackStackLD: MutableLiveData<Unit> by lazy { MutableLiveData<Unit>() }
    override val errorMessageLD: MutableLiveData<Int> by lazy { MutableLiveData<Int>() }
    override val setInitialTimeLD: MutableLiveData<String> by lazy { MutableLiveData<String>() }
    override val setInitialDateLD: MutableLiveData<String> by lazy { MutableLiveData<String>() }
    override val setInitialTitleLD: MutableLiveData<String> by lazy { MutableLiveData<String>() }
    override val setInitialNoteLD: MutableLiveData<String> by lazy { MutableLiveData<String>() }
    override val setInitialLevelLD: MutableLiveData<Pair<Int, Int>> by lazy { MutableLiveData<Pair<Int, Int>>() }
    override val setInitialBookmarkLD: MutableLiveData<Pair<Int, Int>> by lazy { MutableLiveData<Pair<Int, Int>>() }
    override val showTimePickerDialogLD: MutableLiveData<Unit> by lazy { MutableLiveData<Unit>() }
    override val showDatePickerDialogLD: MutableLiveData<Unit> by lazy { MutableLiveData<Unit>() }
    override val showLevelDialogLD: MutableLiveData<Unit> by lazy { MutableLiveData<Unit>() }
    override val setLevelLD: MutableLiveData<Pair<Int, Int>> by lazy { MutableLiveData<Pair<Int, Int>>() }
    override val bookmarkStatusLD: MutableLiveData<Boolean> by lazy { MutableLiveData<Boolean>() }
    override val setBookmarkLD: MutableLiveData<Pair<Int, Int>> by lazy { MutableLiveData<Pair<Int, Int>>() }

    private var currentLevel = LevelNotes.LESS_IMPORTANT
    private var currentBookmarkStatus = false

    override fun init(noteId: Long) {
        editNoteUC
            .noteData(noteId)
            .onEach { noteData ->
                setInitialTimeLD.value = noteData.time
                setInitialDateLD.value = noteData.date
                setInitialTitleLD.value = noteData.title
                setInitialNoteLD.value = noteData.note
                when (noteData.level) {
                    LevelNotes.LESS_IMPORTANT.value -> setInitialLevelLD.value =
                        Pair(R.drawable.ic_circle_green, R.string.text_less_important)
                    LevelNotes.IMPORTANT.value -> setInitialLevelLD.value =
                        Pair(R.drawable.ic_circle_yellow, R.string.text_important)
                    else -> setInitialLevelLD.value =
                        Pair(R.drawable.ic_circle_red, R.string.text_more_important)
                }
                when (noteData.isBookmark) {
                    false -> setInitialBookmarkLD.value =
                        Pair(R.drawable.ic_bookmark_default, R.string.text_bookmark_add)
                    else -> setInitialBookmarkLD.value =
                        Pair(R.drawable.ic_bookmark, R.string.text_bookmark_remove)
                }
                currentLevel = when (noteData.level) {
                    LevelNotes.LESS_IMPORTANT.value -> LevelNotes.LESS_IMPORTANT
                    LevelNotes.IMPORTANT.value -> LevelNotes.IMPORTANT
                    else -> LevelNotes.MORE_IMPORTANT
                }
                currentBookmarkStatus = noteData.isBookmark
            }
            .launchIn(viewModelScope)
    }

    override fun onClickBackButton() {
        popBackStackLD.value = Unit
    }

    override fun onClickEditNoteButton(
        noteId: Long,
        time: String,
        date: String,
        title: String,
        note: String
    ) {
        if (title.isEmpty()) {
            errorMessageLD.value = R.string.text_error_title
            return
        }
        if (note.isEmpty()) {
            errorMessageLD.value = R.string.text_error_note
            return
        }
        editNoteUC
            .editNote(noteId, time, date, title, note, currentLevel, currentBookmarkStatus)
            .onEach { popBackStackLD.value = it }
            .launchIn(viewModelScope)
    }

    override fun onClickTimeButton() {
        showTimePickerDialogLD.value = Unit
    }

    override fun onClickDateButton() {
        showDatePickerDialogLD.value = Unit
    }

    override fun onClickLevelButton() {
        showLevelDialogLD.value = Unit
    }

    override fun onClickLevelDialog(level: LevelNotes) {
        if (currentLevel == level) return
        currentLevel = level
        when (currentLevel) {
            LevelNotes.LESS_IMPORTANT -> setLevelLD.value =
                Pair(R.drawable.ic_circle_green, R.string.text_less_important)
            LevelNotes.IMPORTANT -> setLevelLD.value =
                Pair(R.drawable.ic_circle_yellow, R.string.text_important)
            else -> setLevelLD.value =
                Pair(R.drawable.ic_circle_red, R.string.text_more_important)
        }
    }

    override fun onClickBookmarkButton() {
        currentBookmarkStatus = !currentBookmarkStatus
        when (currentBookmarkStatus) {
            true -> setBookmarkLD.value =
                Pair(R.drawable.ic_bookmark, R.string.text_bookmark_remove)
            else -> setBookmarkLD.value =
                Pair(R.drawable.ic_bookmark_default, R.string.text_bookmark_add)
        }
    }
}