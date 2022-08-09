package uz.gita.notesapp.domain.repository.impl

import uz.gita.notesapp.R
import uz.gita.notesapp.data.local.MySharedPreferences
import uz.gita.notesapp.data.local.dao.GroupsDao
import uz.gita.notesapp.data.local.dao.NoteDao
import uz.gita.notesapp.data.local.entity.GroupEntity
import uz.gita.notesapp.data.local.entity.NoteEntity
import uz.gita.notesapp.data.model.AddNoteData
import uz.gita.notesapp.data.model.EditNoteData
import uz.gita.notesapp.data.model.GroupNotes
import uz.gita.notesapp.domain.repository.AppRepository
import javax.inject.Inject

class AppRepositoryImpl
@Inject constructor(
    private val preferences: MySharedPreferences,
    private val noteDao: NoteDao,
    private val groupsDao: GroupsDao
) : AppRepository {

    override suspend fun headerNote(): NoteEntity? {
        val result = noteDao.allNotesSortedByDate(preferences.currentGroup)
        return if (result.isNotEmpty()) result[0]
        else null
    }

    override suspend fun notesListByCurrentGroupId(): List<NoteEntity> {
        return noteDao.allNotesByGroupId(preferences.currentGroup)
    }

    /*override suspend fun notesResultByQuery(query: String): List<NoteEntity> {
        return noteDao.allNotesByQuery(preferences.currentGroup, query)
    }*/

    override suspend fun notesCountByGroupId(groupId: Int): Long {
        return noteDao.notesCountByGroupId(groupId)
    }

    override suspend fun groupsList(): List<GroupEntity> {
        if (!groupsDao.isAvailableGroups()) groupsDao.insertInitialGroups(insertInitialGroups())
        return groupsDao.allGroups()
    }

    override suspend fun currentGroupData(): GroupEntity {
        if (!groupsDao.isAvailableGroups()) groupsDao.insertInitialGroups(insertInitialGroups())
        return groupsDao.groupDataByGroupId(preferences.currentGroup)
    }

    override fun changeGroup(groupId: Int) {
        preferences.currentGroup = groupId
    }

    override suspend fun groupDataByGroupId(groupId: Int): GroupEntity {
        return groupsDao.groupDataByGroupId(groupId)
    }

    override suspend fun noteDataById(noteId: Long): NoteEntity {
        return noteDao.getNoteById(noteId, preferences.currentGroup)
    }

    override suspend fun addNote(addNoteData: AddNoteData): Long? {
        return noteDao.insertNote(
            NoteEntity(
                groupId = preferences.currentGroup,
                time = addNoteData.time,
                date = addNoteData.date,
                title = addNoteData.title,
                note = addNoteData.note,
                level = addNoteData.level,
                isBookmark = addNoteData.isBookmark
            )
        )
    }

    override suspend fun editNote(editNoteData: EditNoteData) {
        noteDao.updateNote(editNoteData.toNoteEntity(preferences.currentGroup))
    }

    override suspend fun bookmarkNote(noteId: Long, status: Boolean) {
        noteDao.bookmarkNote(noteId, preferences.currentGroup, status)
    }

    override suspend fun deleteNote(noteId: Long) {
        noteDao.deleteNote(noteId, preferences.currentGroup)
    }

    private fun insertInitialGroups(): List<GroupEntity> {
        return listOf(
            GroupEntity(
                id = GroupNotes.PERSONAL.value,
                icon = R.drawable.ic_personal,
                name = R.string.text_personal,
                count = 0
            ),
            GroupEntity(
                id = GroupNotes.WORK.value,
                icon = R.drawable.ic_work,
                name = R.string.text_work,
                count = 0
            ),
            GroupEntity(
                id = GroupNotes.MEETING.value,
                icon = R.drawable.ic_meeting,
                name = R.string.text_meeting,
                count = 0
            ),
            GroupEntity(
                id = GroupNotes.SHOPPING.value,
                icon = R.drawable.ic_shopping,
                name = R.string.text_shopping,
                count = 0
            )
        )
    }
}