package uz.gita.notesapp.domain.usecase.impl

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import uz.gita.notesapp.domain.repository.AppRepository
import uz.gita.notesapp.domain.usecase.MainUC
import javax.inject.Inject

class MainUCImpl
@Inject constructor(
    private val repository: AppRepository
) : MainUC {

    override fun groupName() = flow<Int> {
        emit(repository.currentGroupData().name)
    }.flowOn(Dispatchers.IO)

}
