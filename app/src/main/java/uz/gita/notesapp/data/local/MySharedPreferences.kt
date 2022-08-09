package uz.gita.notesapp.data.local

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import uz.gita.notesapp.data.model.GroupNotes
import uz.gita.notesapp.utils.SharedPreference
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MySharedPreferences
@Inject constructor(@ApplicationContext context: Context) : SharedPreference(context) {

    var currentGroup: Int by IntPreference(GroupNotes.PERSONAL.value)

}