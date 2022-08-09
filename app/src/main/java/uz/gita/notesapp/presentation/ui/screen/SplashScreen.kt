package uz.gita.notesapp.presentation.ui.screen

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import uz.gita.notesapp.R
import uz.gita.notesapp.presentation.viewmodels.SplashVM
import uz.gita.notesapp.presentation.viewmodels.impl.SplashVMImpl

@SuppressLint("CustomSplashScreen")
@AndroidEntryPoint
class SplashScreen : Fragment(R.layout.screen_splash) {

    private val viewModel: SplashVM by viewModels<SplashVMImpl>()

    override fun onCreate(savedInstanceState: Bundle?) = with(viewModel) {
        super.onCreate(savedInstanceState)
        initLD.observe(this@SplashScreen) {
            findNavController().navigate(R.id.action_splashScreen_to_mainScreen)
        }
    }
}