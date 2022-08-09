package uz.gita.notesapp.presentation.viewmodels.impl

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import uz.gita.notesapp.presentation.viewmodels.SplashVM
import javax.inject.Inject

@HiltViewModel
class SplashVMImpl
@Inject constructor(

) : ViewModel(), SplashVM {
    override val initLD: MutableLiveData<Unit> by lazy { MutableLiveData<Unit>() }

    init {
        viewModelScope.launch(Dispatchers.IO) {
            delay(2000)
            initLD.postValue(Unit)
        }
    }

}