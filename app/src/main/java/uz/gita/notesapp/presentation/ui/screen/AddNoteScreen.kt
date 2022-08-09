package uz.gita.notesapp.presentation.ui.screen

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import org.wordpress.aztec.Aztec
import org.wordpress.aztec.ITextFormat
import org.wordpress.aztec.toolbar.IAztecToolbarClickListener
import timber.log.Timber
import uz.gita.notesapp.R
import uz.gita.notesapp.databinding.ScreenAddNoteBinding
import uz.gita.notesapp.presentation.ui.dialog.LevelDialog
import uz.gita.notesapp.presentation.viewmodels.AddNoteVM
import uz.gita.notesapp.presentation.viewmodels.impl.AddNoteVMImpl
import uz.gita.notesapp.utils.snackErrorMessage
import java.util.*

@AndroidEntryPoint
class AddNoteScreen : Fragment(R.layout.screen_add_note) {

    private val viewBinding by viewBinding(ScreenAddNoteBinding::bind)
    private val viewModel: AddNoteVM by viewModels<AddNoteVMImpl>()

    override fun onCreate(savedInstanceState: Bundle?) = with(viewModel) {
        super.onCreate(savedInstanceState)
        errorMessageLD.observe(this@AddNoteScreen) { snackErrorMessage(resources.getString(it)) }
        popBackStackLD.observe(this@AddNoteScreen) { findNavController().popBackStack() }
        setInitialTimeLD.observe(this@AddNoteScreen, setInitialTimeLDObserver)
        setInitialDateLD.observe(this@AddNoteScreen, setInitialDateLDObserver)
        showTimePickerDialogLD.observe(this@AddNoteScreen, showTimePickerDialogLDObserver)
        showDatePickerDialogLD.observe(this@AddNoteScreen, showDatePickerDialogLDObserver)
        showLevelDialogLD.observe(this@AddNoteScreen, showLevelDialogLDObserver)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        subscribeViewBinding(viewBinding)
        subscribeViewModel(viewModel)
    }

    private fun subscribeViewBinding(viewBinding: ScreenAddNoteBinding) = with(viewBinding) {
        setEditor()
        buttonBack.setOnClickListener { viewModel.onClickBackButton() }
        buttonTime.setOnClickListener { viewModel.onClickTimeButton() }
        buttonDate.setOnClickListener { viewModel.onClickDateButton() }
        buttonLevel.setOnClickListener { viewModel.onClickLevelButton() }
        buttonBookmark.setOnClickListener { viewModel.onClickBookmarkButton() }
        buttonSave.setOnClickListener {
            viewModel.onClickSaveNoteButton(
                buttonTime.text.toString(),
                buttonDate.text.toString(),
                editTextTitle.text.toString().trim(),
                visualEditor.toPlainHtml().trim()
            )
        }
    }

    private fun subscribeViewModel(viewModel: AddNoteVM) = with(viewModel) {
        setLevelLD.observe(viewLifecycleOwner, setLevelLDObserver)
        setBookmarkLD.observe(viewLifecycleOwner, setBookmarkLDObserver)
    }

    private val setInitialTimeLDObserver = Observer<Unit> {
        val currentHour: Int
        val currentMinute: Int
        val calendar = Calendar.getInstance()
        currentHour = calendar.get(Calendar.HOUR_OF_DAY)
        currentMinute = calendar.get(Calendar.MINUTE)
        setTime(currentHour, currentMinute)
    }

    private val setInitialDateLDObserver = Observer<Unit> {
        val currentYear: Int
        val currentMonth: Int
        val currentDayOfMonth: Int
        val calendar = Calendar.getInstance()
        currentYear = calendar.get(Calendar.YEAR)
        currentMonth = calendar.get(Calendar.MONTH)
        currentDayOfMonth = calendar.get(Calendar.DAY_OF_MONTH)
        setDate(currentYear, currentMonth, currentDayOfMonth)
    }

    private val showTimePickerDialogLDObserver = Observer<Unit> {
        val currentHour: Int
        val currentMinute: Int
        val calendar = Calendar.getInstance()
        currentHour = calendar.get(Calendar.HOUR_OF_DAY)
        currentMinute = calendar.get(Calendar.MINUTE)
        val timePickerDialogListener =
            TimePickerDialog.OnTimeSetListener { _, hourOfDay, minute ->
                Timber.d("timePickerDialogListener -> hourOfDay: $hourOfDay, minute: $minute")
                setTime(hourOfDay, minute)
            }
        val timePickerDialog = TimePickerDialog(
            requireContext(),
            R.style.TimePickerDialog,
            timePickerDialogListener,
            currentHour,
            currentMinute,
            false
        )
        timePickerDialog.window?.setBackgroundDrawable(
            AppCompatResources.getDrawable(
                requireContext(),
                R.color.white
            )
        )
        timePickerDialog.show()
    }

    private val showDatePickerDialogLDObserver = Observer<Unit> {
        val currentYear: Int
        val currentMonth: Int
        val currentDayOfMonth: Int
        val calendar = Calendar.getInstance()
        currentYear = calendar.get(Calendar.YEAR)
        currentMonth = calendar.get(Calendar.MONTH)
        currentDayOfMonth = calendar.get(Calendar.DAY_OF_MONTH)
        val datePickerDialogListener =
            DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
                setDate(year, month, dayOfMonth)
            }
        val datePickerDialog = DatePickerDialog(
            requireContext(),
            R.style.DatePickerDialog,
            datePickerDialogListener,
            currentYear,
            currentMonth - 1,
            currentDayOfMonth,
        )
        datePickerDialog.datePicker.minDate = System.currentTimeMillis() - 1000
        datePickerDialog.window?.setBackgroundDrawable(
            AppCompatResources.getDrawable(
                requireContext(),
                R.color.white
            )
        )
        datePickerDialog.show()
    }

    private val showLevelDialogLDObserver = Observer<Unit> {
        val dialog = LevelDialog()
        dialog.setOnClickLevelButtonListener { level -> viewModel.onClickLevelDialog(level) }
        dialog.show(childFragmentManager, "LevelDialog")
    }

    private val setLevelLDObserver = Observer<Pair<Int, Int>> { level ->
        viewBinding.buttonLevel.apply {
            setCompoundDrawablesWithIntrinsicBounds(level.first, 0, 0, 0)
            text = resources.getString(level.second)
        }
    }

    private val setBookmarkLDObserver = Observer<Pair<Int, Int>> { bookmark ->
        viewBinding.buttonBookmark.apply {
            setCompoundDrawablesWithIntrinsicBounds(bookmark.first, 0, 0, 0)
            text = resources.getString(bookmark.second)
        }
    }

    private fun setTime(currentHour: Int, currentMinute: Int) {
        var hour: String
        val timeSet: String
        if (currentHour > 12) {
            hour = "0${currentHour - 12}"
            timeSet = "PM"
        } else if (currentHour == 0) {
            hour = "${currentHour + 12}"
            timeSet = "AM"
        } else if (currentHour == 12) {
            hour = "$currentHour"
            timeSet = "PM"
        } else {
            hour = "0$currentHour"
            timeSet = "AM"
        }
        if (hour.length == 3) hour = hour.substring(1, hour.length)
        val minute: String = if (currentMinute < 10) "0$currentMinute"
        else "$currentMinute"

        viewBinding.buttonTime.text =
            resources.getString(R.string.text_current_time, hour, minute, timeSet)
    }

    private fun setDate(currentYear: Int, currentMonth: Int, currentDayOfMonth: Int) {
        val month = if (currentMonth < 9) "0${currentMonth + 1}"
        else "${currentMonth + 1}"

        val day = if (currentDayOfMonth < 10) "0$currentDayOfMonth"
        else currentDayOfMonth

        viewBinding.buttonDate.text = resources.getString(
            R.string.text_current_date,
            currentYear,
            month,
            day
        )
    }

    private fun setEditor() = with(viewBinding) {
        val editor = visualEditor
        Aztec.with(visualEditor, editorToolbar, object : IAztecToolbarClickListener {
            override fun onToolbarCollapseButtonClicked() {}

            override fun onToolbarExpandButtonClicked() {}

            override fun onToolbarFormatButtonClicked(
                format: ITextFormat,
                isKeyboardShortcut: Boolean
            ) {
            }

            override fun onToolbarHeadingButtonClicked() {}

            override fun onToolbarHtmlButtonClicked() {}

            override fun onToolbarListButtonClicked() {}

            override fun onToolbarMediaButtonClicked(): Boolean = false

        })
        editor.setBackgroundResource(R.drawable.background_add_note)
        editor.gravity = View.TEXT_ALIGNMENT_TEXT_START
        editor.setTextColor(Color.BLACK)
        editor.highlightColor =
            ContextCompat.getColor(requireContext(), R.color.color_highlight_color)
    }

}