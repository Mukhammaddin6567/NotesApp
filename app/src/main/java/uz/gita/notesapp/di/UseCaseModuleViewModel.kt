package uz.gita.notesapp.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import uz.gita.notesapp.domain.usecase.AddNoteUC
import uz.gita.notesapp.domain.usecase.EditNoteUC
import uz.gita.notesapp.domain.usecase.impl.AddNoteUCImpl
import uz.gita.notesapp.domain.usecase.impl.EditNoteUCImpl

@Module
@InstallIn(ViewModelComponent::class)
interface UseCaseModuleViewModel {

    @Binds
    fun bindAddNoteUseCase(impl: AddNoteUCImpl): AddNoteUC

    @Binds
    fun bindEditNoteUseCase(impl: EditNoteUCImpl): EditNoteUC
}