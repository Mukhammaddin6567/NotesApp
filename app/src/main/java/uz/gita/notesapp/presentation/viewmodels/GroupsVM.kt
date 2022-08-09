package uz.gita.notesapp.presentation.viewmodels

import androidx.lifecycle.LiveData
import uz.gita.notesapp.data.local.entity.GroupEntity
import uz.gita.notesapp.data.model.GroupData

interface GroupsVM {

    val groupsListLD: LiveData<List<GroupData>>

    fun loadGroupsData()
    fun onClickGroupItem(id: Int)

}