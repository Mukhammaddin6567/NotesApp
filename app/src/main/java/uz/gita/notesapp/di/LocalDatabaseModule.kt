package uz.gita.notesapp.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import uz.gita.notesapp.data.local.MyRoomDatabase
import uz.gita.notesapp.data.local.dao.GroupsDao
import uz.gita.notesapp.data.local.dao.NoteDao
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class LocalDatabaseModule {

    @[Provides Singleton]
    fun provideMyRoomDatabase(@ApplicationContext context: Context): MyRoomDatabase =
        MyRoomDatabase.getDatabase(context)

    @[Provides Singleton]
    fun provideNoteDao(database: MyRoomDatabase): NoteDao = database.noteDao()

    @[Provides Singleton]
    fun provideGroupsDao(database: MyRoomDatabase): GroupsDao = database.groupsDao()

}