package uz.gita.notesapp.presentation.ui.dialog

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import androidx.fragment.app.DialogFragment
import by.kirich1409.viewbindingdelegate.viewBinding
import uz.gita.notesapp.R
import uz.gita.notesapp.databinding.DialogDeleteNoteBinding

class DeleteNoteDialog : DialogFragment(R.layout.dialog_delete_note) {

    private val viewBinding by viewBinding(DialogDeleteNoteBinding::bind)
    private var deleteNoteListener: (() -> Unit)? = null

    override fun onStart() {
        super.onStart()
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        isCancelable = false
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) = with(viewBinding) {
        buttonCancel.setOnClickListener { dismiss() }
        buttonOk.setOnClickListener {
            deleteNoteListener?.invoke()
            dismiss()
        }
    }

    fun setDeleteNoteListener(block: () -> Unit) {
        deleteNoteListener = block
    }
}


