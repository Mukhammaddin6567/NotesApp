package uz.gita.notesapp.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import uz.gita.notesapp.domain.usecase.GroupsUC
import uz.gita.notesapp.domain.usecase.MainUC
import uz.gita.notesapp.domain.usecase.NotesUC
import uz.gita.notesapp.domain.usecase.impl.GroupsUCImpl
import uz.gita.notesapp.domain.usecase.impl.MainUCImpl
import uz.gita.notesapp.domain.usecase.impl.NotesUCImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface UseCaseModuleSingleton {

    @[Binds Singleton]
    fun bindMainUseCase(impl: MainUCImpl): MainUC

    @[Binds Singleton]
    fun bindGroupsUseCase(impl: GroupsUCImpl): GroupsUC

    @[Binds Singleton]
    fun bindNotesUseCase(impl: NotesUCImpl): NotesUC

}