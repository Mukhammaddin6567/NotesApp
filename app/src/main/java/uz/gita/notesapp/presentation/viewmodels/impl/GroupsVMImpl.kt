package uz.gita.notesapp.presentation.viewmodels.impl

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import uz.gita.notesapp.data.model.GroupData
import uz.gita.notesapp.domain.usecase.GroupsUC
import uz.gita.notesapp.presentation.viewmodels.GroupsVM
import uz.gita.notesapp.utils.Helper.onSelectGroupItemLD
import javax.inject.Inject

@HiltViewModel
class GroupsVMImpl
@Inject constructor(
    private val groupsUC: GroupsUC
) : ViewModel(), GroupsVM {

    override val groupsListLD: MutableLiveData<List<GroupData>> by lazy { MutableLiveData<List<GroupData>>() }

    override fun loadGroupsData() {
        groupsUC
            .groupsList()
            .onEach { groupsListLD.value = it }
            .launchIn(viewModelScope)
    }

    override fun onClickGroupItem(id: Int) {
        groupsUC.changeGroup(id)
        onSelectGroupItemLD.value = Unit
    }
}