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
import uz.gita.notesapp.data.model.LevelNotes
import uz.gita.notesapp.databinding.DialogLevelBinding

class LevelDialog() : BottomSheetDialogFragment() {

    private var onClickLevelButtonListener: ((level: LevelNotes) -> Unit)? = null
    private val viewBinding by viewBinding(DialogLevelBinding::bind)

    override fun onStart() {
        super.onStart()
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.dialog_level, container)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) = with(viewBinding) {
        buttonLessImportant.setOnClickListener {
            onClickLevelButtonListener?.invoke(LevelNotes.LESS_IMPORTANT)
            dismiss()
        }
        buttonImportant.setOnClickListener {
            onClickLevelButtonListener?.invoke(LevelNotes.IMPORTANT)
            dismiss()
        }
        buttonMoreImportant.setOnClickListener {
            onClickLevelButtonListener?.invoke(LevelNotes.MORE_IMPORTANT)
            dismiss()
        }
    }

    fun setOnClickLevelButtonListener(block: (level: LevelNotes) -> Unit) {
        onClickLevelButtonListener = block
    }

}