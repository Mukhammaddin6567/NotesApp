package uz.gita.notesapp.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import uz.gita.notesapp.data.local.entity.GroupEntity

@Dao
interface GroupsDao {

    @Query("select exists (select * from groups)")
    fun isAvailableGroups(): Boolean

    @Query("select * from groups")
    suspend fun allGroups(): List<GroupEntity>

    @Query("select * from groups where groups.id=:groupId")
    suspend fun groupDataByGroupId(groupId: Int): GroupEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertInitialGroups(groupsList: List<GroupEntity>)

}