package uz.gita.notesapp.domain.repository

import uz.gita.notesapp.data.local.entity.GroupEntity
import uz.gita.notesapp.data.local.entity.NoteEntity
import uz.gita.notesapp.data.model.AddNoteData
import uz.gita.notesapp.data.model.EditNoteData

interface AppRepository {

    suspend fun headerNote(): NoteEntity?

    suspend fun notesListByCurrentGroupId(): List<NoteEntity>

    /*suspend fun notesResultByQuery(query: String): List<NoteEntity>*/

    suspend fun notesCountByGroupId(groupId: Int): Long

    suspend fun groupsList(): List<GroupEntity>

    suspend fun currentGroupData(): GroupEntity

    fun changeGroup(groupId: Int)

    suspend fun groupDataByGroupId(groupId: Int): GroupEntity

    suspend fun noteDataById(noteId: Long): NoteEntity

    suspend fun addNote(addNoteData: AddNoteData): Long?

    suspend fun editNote(editNoteData: EditNoteData)

    suspend fun bookmarkNote(noteId: Long, status: Boolean)

    suspend fun deleteNote(noteId: Long)

}