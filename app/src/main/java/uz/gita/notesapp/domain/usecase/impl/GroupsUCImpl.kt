package uz.gita.notesapp.domain.usecase.impl

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import uz.gita.notesapp.data.model.GroupData
import uz.gita.notesapp.domain.repository.AppRepository
import uz.gita.notesapp.domain.usecase.GroupsUC
import javax.inject.Inject

class GroupsUCImpl
@Inject constructor(
    private val repository: AppRepository
) : GroupsUC {

    override fun groupsList() = flow<List<GroupData>> {
        val groupsList = repository.groupsList()
        val result = ArrayList<GroupData>()
        groupsList.forEach { groupEntity ->
            result.add(
                GroupData(
                    id = groupEntity.id,
                    icon = groupEntity.icon,
                    name = groupEntity.name,
                    count = repository.notesCountByGroupId(groupEntity.id)
                )
            )
        }
        emit(result)
    }.flowOn(Dispatchers.Default)

    override fun changeGroup(groupId: Int) {
        repository.changeGroup(groupId)
    }
}