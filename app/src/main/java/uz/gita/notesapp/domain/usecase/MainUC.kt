package uz.gita.notesapp.domain.usecase

import kotlinx.coroutines.flow.Flow

interface MainUC {

    fun groupName(): Flow<Int>

}
