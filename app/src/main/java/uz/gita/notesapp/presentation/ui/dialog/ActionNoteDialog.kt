package uz.gita.notesapp.presentation.ui.dialog

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import uz.gita.notesapp.R
import uz.gita.notesapp.databinding.DialogActionNoteBinding

class ActionNoteDialog() : BottomSheetDialogFragment() {

    private var onClickEditButtonListener: (() -> Unit)? = null
    private var onClickArchiveButtonListener: (() -> Unit)? = null
    private var onClickDeleteButtonListener: (() -> Unit)? = null
    private val viewBinding by viewBinding(DialogActionNoteBinding::bind)

    override fun onStart() {
        super.onStart()
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.dialog_action_note, container)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) = with(viewBinding) {
        buttonEditNote.setOnClickListener {
            onClickEditButtonListener?.invoke()
            dismiss()
        }
        /*buttonArchiveNote.setOnClickListener {
            onClickArchiveButtonListener?.invoke()
            dismiss()
        }*/
        buttonDeleteNote.setOnClickListener {
            onClickDeleteButtonListener?.invoke()
            dismiss()
        }
    }

    fun setOnClickEditButtonListener(block: () -> Unit) {
        onClickEditButtonListener = block
    }

    /*fun setOnClickArchiveButtonListener(block: () -> Unit) {
        onClickArchiveButtonListener = block
    }*/

    fun setOnClickDeleteButtonListener(block: () -> Unit) {
        onClickDeleteButtonListener = block
    }

}