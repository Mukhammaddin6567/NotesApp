package uz.gita.notesapp.presentation.ui.screen

import android.os.Bundle
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import uz.gita.notesapp.R
import uz.gita.notesapp.data.model.HeaderNoteData
import uz.gita.notesapp.data.model.NoteData
import uz.gita.notesapp.databinding.ScreenNotesBinding
import uz.gita.notesapp.presentation.ui.adapter.NotesAdapter
import uz.gita.notesapp.presentation.ui.dialog.ActionNoteDialog
import uz.gita.notesapp.presentation.ui.dialog.DeleteNoteDialog
import uz.gita.notesapp.presentation.viewmodels.NotesVM
import uz.gita.notesapp.presentation.viewmodels.impl.NotesVMImpl

/*SearchView.OnQueryTextListener*/

@AndroidEntryPoint
class NotesScreen : Fragment(R.layout.screen_notes) {

    private val viewBinding by viewBinding(ScreenNotesBinding::bind)
    private val viewModel: NotesVM by viewModels<NotesVMImpl>()
    private val adapter: NotesAdapter by lazy { NotesAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) = with(viewModel) {
        super.onCreate(savedInstanceState)
        navigateNoteEditScreenLD.observe(this@NotesScreen) {
            findNavController().navigate(
                MainScreenDirections.actionMainScreenToEditNoteScreen(it)
            )
        }
        showActionNoteDialogLD.observe(this@NotesScreen, showActionNoteDialogLDObserver)
        showDeleteNoteDialogLD.observe(this@NotesScreen, showDeleteNoteDialogLDObserver)
        headerNoteLD.observe(this@NotesScreen, headerNoteLDObserver)
        headerNoteStatusLD.observe(this@NotesScreen, headerNoteStatusLDObserver)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        subscribeViewBinding(viewBinding)
        subscribeViewModel(viewModel)
    }

    private fun subscribeViewBinding(viewBinding: ScreenNotesBinding) = with(viewBinding) {
        listNotes.adapter = adapter
        listNotes.layoutManager = LinearLayoutManager(requireContext())
        /*searchView.setOnQueryTextListener(this@NotesScreen)*/
        headerNote.buttonClose.setOnClickListener { viewModel.onClickDismissHeaderNote() }
        adapter.setOnClickNoteItemListener { noteId -> viewModel.onClickNoteItem(noteId) }
        adapter.setOnClickNoteBookmarkListener { noteId, status ->
            viewModel.onClickNoteBookmark(noteId, status)
        }
    }

    private fun subscribeViewModel(viewModel: NotesVM) = with(viewModel) {
        placeholderStatusLD.observe(viewLifecycleOwner, placeholderStatusLDObserver)
//        placeholderTextLD.observe(viewLifecycleOwner, placeholderTextLDObserver)
        /*searchStatusLD.observe(viewLifecycleOwner, searchStatusLDObserver)*/
        notesListLD.observe(viewLifecycleOwner, notesListLDObserver)
    }

    private val showActionNoteDialogLDObserver = Observer<Long> { noteId ->
        val dialog = ActionNoteDialog()
        dialog.setOnClickEditButtonListener { viewModel.onClickEditNote(noteId) }
        /*dialog.setOnClickArchiveButtonListener { viewModel.onClickArchiveNote(noteId) }*/
        dialog.setOnClickDeleteButtonListener { viewModel.onClickDeleteNote(noteId) }
        dialog.show(childFragmentManager, "ActionNoteDialog")
    }

    private val showDeleteNoteDialogLDObserver = Observer<Long> { noteId ->
        val dialog = DeleteNoteDialog()
        dialog.setDeleteNoteListener { viewModel.deleteNote(noteId) }
        dialog.show(childFragmentManager, "DeleteNoteDialog")
    }

    private val placeholderStatusLDObserver = Observer<Boolean> { status ->
        viewBinding.apply {
            when (status) {
                false -> {
                    containerContent.visibility = VISIBLE
                    containerPlaceholder.visibility = GONE
                }
                else -> {
                    containerPlaceholder.visibility = VISIBLE
                    containerContent.visibility = GONE
                }
            }
        }
    }

    /*private val searchStatusLDObserver = Observer<Boolean> { status ->
        viewBinding.apply {
            when (status) {
                true -> {
                    headerNote.root.visibility = VISIBLE
                    listNotes.visibility = VISIBLE
                }
                else -> {
                    headerNote.root.visibility = GONE
                    listNotes.visibility = GONE
                }
            }
        }
    }*/

    /*private val placeholderTextLDObserver = Observer<Int> {
        viewBinding.textPlaceholderDescription.text = resources.getString(it)
    }*/

    private val headerNoteStatusLDObserver = Observer<Boolean> { status ->
        viewBinding.container.apply {
            visibility = when (status) {
                true -> VISIBLE
                else -> GONE
            }
        }
    }

    /*private val groupNameLDObserver = Observer<String> {groupName->
        viewBinding.too.text = groupName
    }*/

    private val headerNoteLDObserver = Observer<HeaderNoteData> { headerNoteData ->
        Timber.d("headerNoteLDObserver: $headerNoteData")
        viewBinding.headerNote.apply {
            textGroupName.text = resources.getString(headerNoteData.groupName)
            imageGroup.setImageResource(headerNoteData.groupIcon)
            textNoteTitle.text = headerNoteData.noteTitle
            textNoteTime.text = resources.getString(
                R.string.text_time_date_header,
                headerNoteData.noteTime,
                headerNoteData.noteDate
            )
        }
    }

    private val notesListLDObserver = Observer<List<NoteData>> {
        adapter.submitList(it)
    }

    override fun onResume() {
        super.onResume()
        Timber.d("onResume")
        viewModel.loadNotesList()
    }

    override fun onPause() {
        super.onPause()
        Timber.d("onPause")
    }

    /*override fun onQueryTextSubmit(query: String?): Boolean {
        viewModel.onSearchNote(query)
        return true
    }

    override fun onQueryTextChange(query: String?): Boolean {
        viewModel.onSearchNote(query)
        return true
    }*/

}