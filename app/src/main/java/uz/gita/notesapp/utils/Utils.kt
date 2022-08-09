package uz.gita.notesapp.utils

import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import uz.gita.notesapp.R

fun Fragment.snackErrorMessage(message: String, duration: Int = Snackbar.LENGTH_SHORT) {
    Snackbar.make(requireView(), message, duration).apply {
        setBackgroundTint(ContextCompat.getColor(requireContext(), R.color.color_red))
            .setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
    }.show()
}