package uz.gita.notesapp.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import uz.gita.notesapp.data.local.dao.GroupsDao
import uz.gita.notesapp.data.local.dao.NoteDao
import uz.gita.notesapp.data.local.entity.GroupEntity
import uz.gita.notesapp.data.local.entity.NoteEntity

@Database(entities = [NoteEntity::class, GroupEntity::class], version = 1)
abstract class MyRoomDatabase : RoomDatabase() {
    abstract fun noteDao(): NoteDao
    abstract fun groupsDao(): GroupsDao

    companion object {
        private lateinit var instance: MyRoomDatabase

        fun getDatabase(context: Context): MyRoomDatabase {
            if (::instance.isInitialized) return instance
            instance = Room
                .databaseBuilder(context, MyRoomDatabase::class.java, context.packageName)
                .fallbackToDestructiveMigration()
                .build()
            return instance
        }
    }

}