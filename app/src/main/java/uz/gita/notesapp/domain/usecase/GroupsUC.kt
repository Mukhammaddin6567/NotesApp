package uz.gita.notesapp.domain.usecase

import kotlinx.coroutines.flow.Flow
import uz.gita.notesapp.data.local.entity.GroupEntity
import uz.gita.notesapp.data.model.GroupData

interface GroupsUC {

    fun groupsList(): Flow<List<GroupData>>

    fun changeGroup(groupId: Int)

}