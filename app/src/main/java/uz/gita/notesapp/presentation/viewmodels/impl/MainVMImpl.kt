package uz.gita.notesapp.presentation.viewmodels.impl

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import uz.gita.notesapp.R
import uz.gita.notesapp.domain.usecase.MainUC
import uz.gita.notesapp.presentation.viewmodels.MainVM
import javax.inject.Inject

@HiltViewModel
class MainVMImpl
@Inject constructor(
    private val mainUC: MainUC
) : ViewModel(), MainVM {

    init {
        loadGroupName()
    }

    override val toolbarTitleLD: MutableLiveData<Int> by lazy { MutableLiveData<Int>() }
    override val addNoteStatusLD: MutableLiveData<Boolean> by lazy { MutableLiveData<Boolean>() }
    override val navigateNextScreenLD: MutableLiveData<Int> by lazy { MutableLiveData<Int>() }
    override val addNoteScreenLD: MutableLiveData<Unit> by lazy { MutableLiveData<Unit>() }
    override val errorMessageLD: MutableLiveData<Int> by lazy { MutableLiveData<Int>() }
    override val showSortingDialogLD: MutableLiveData<Unit> by lazy { MutableLiveData<Unit>() }


    override fun onClickMenuButton(position: Int) {
        when (position) {
            0 -> {
                loadGroupName()
                addNoteStatusLD.value = true
            }
            else -> {
                toolbarTitleLD.value = R.string.text_groups
                addNoteStatusLD.value = false
            }
        }
        navigateNextScreenLD.value = position
    }

    override fun onClickAddNotes() {
        addNoteScreenLD.value = Unit
    }

    /*override fun onClickSortingMenu() {
       showSortingDialogLD.value =Unit
    }*/

    private fun loadGroupName() {
        mainUC
            .groupName()
            .onEach { toolbarTitleLD.value = it }
            .launchIn(viewModelScope)
    }

}