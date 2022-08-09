package uz.gita.notesapp.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import uz.gita.notesapp.data.local.entity.NoteEntity

@Dao
interface NoteDao {

    @Query("select * from notes where groupId=:groupId order by date")
    suspend fun allNotesSortedByDate(groupId: Int): List<NoteEntity>

    @Query("select * from notes where notes.groupId =:groupId order by notes.date asc")
    suspend fun allNotesByGroupId(groupId: Int): List<NoteEntity>

    /*@Query("select * from notes where notes.groupId=:groupId like :query")
    suspend fun allNotesByQuery(groupId: Int, query: String): List<NoteEntity>*/

    @Insert
    suspend fun insertNote(note: NoteEntity): Long?

    @Update
    suspend fun updateNote(note: NoteEntity)

    @Query("select count(:groupId) from notes where notes.groupId=:groupId")
    suspend fun notesCountByGroupId(groupId: Int): Long

    @Query("update notes set isBookmark=:status where id=:noteId and groupId=:currentGroupId")
    suspend fun bookmarkNote(noteId: Long, currentGroupId: Int, status: Boolean)

    @Query("select * from notes where id=:noteId and groupId=:groupId")
    suspend fun getNoteById(noteId: Long, groupId: Int): NoteEntity

    @Query("delete from notes where notes.id=:noteId and notes.groupId=:groupId")
    suspend fun deleteNote(noteId: Long, groupId: Int)

}