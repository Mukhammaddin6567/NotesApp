package uz.gita.notesapp.presentation.ui.screen

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.drawerlayout.widget.DrawerLayout.LOCK_MODE_LOCKED_CLOSED
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import uz.gita.notesapp.R
import uz.gita.notesapp.databinding.ScreenMainBinding
import uz.gita.notesapp.presentation.ui.adapter.MainAdapter
import uz.gita.notesapp.presentation.viewmodels.MainVM
import uz.gita.notesapp.presentation.viewmodels.impl.MainVMImpl
import uz.gita.notesapp.utils.Helper.onSelectGroupItemLD

@AndroidEntryPoint
class MainScreen : Fragment(R.layout.screen_main) {

    private val viewBinding by viewBinding(ScreenMainBinding::bind)
    private val viewModel: MainVM by viewModels<MainVMImpl>()
    private lateinit var adapter: MainAdapter
    private lateinit var actionBarDrawerToggle: ActionBarDrawerToggle
    private val navController by lazy(LazyThreadSafetyMode.NONE) { findNavController() }

    override fun onCreate(savedInstanceState: Bundle?) = with(viewModel) {
        super.onCreate(savedInstanceState)
        /*setHasOptionsMenu(true)*/
        addNoteScreenLD.observe(this@MainScreen) {
            navController.navigate(R.id.action_mainScreen_to_addNoteScreen)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        adapter = MainAdapter(childFragmentManager, lifecycle)
        subscribeViewBinding(viewBinding)
        subscribeViewModel(viewModel)
    }

    private fun subscribeViewBinding(viewBinding: ScreenMainBinding) = with(viewBinding) {
        drawerLayout.setDrawerLockMode(LOCK_MODE_LOCKED_CLOSED)
        (activity as AppCompatActivity?)?.setSupportActionBar(toolbar)
        /*actionBarDrawerToggle =
            ActionBarDrawerToggle(
                requireActivity(),
                drawerLayout,
                toolbar,
                R.string.text_open,
                R.string.text_close,
            )
        drawerLayout.addDrawerListener(actionBarDrawerToggle)
        actionBarDrawerToggle.syncState()*/
        bottomNavigationView.setOnItemReselectedListener { }
        bottomNavigationView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.menuNotes -> {
                    viewModel.onClickMenuButton(0)
                    return@setOnItemSelectedListener true
                }
                else -> {
                    viewModel.onClickMenuButton(1)
                    return@setOnItemSelectedListener true
                }
            }
        }
        viewPager.adapter = adapter
        viewPager.isUserInputEnabled = false
        buttonAdd.setOnClickListener { viewModel.onClickAddNotes() }
    }

    private fun subscribeViewModel(viewModel: MainVM) = with(viewModel) {
        toolbarTitleLD.observe(viewLifecycleOwner, toolbarTitleLDObserver)
        onSelectGroupItemLD.observe(viewLifecycleOwner, onSelectGroupItemLDObserver)
        addNoteStatusLD.observe(viewLifecycleOwner, addNoteStatusLDObserver)
        navigateNextScreenLD.observe(viewLifecycleOwner, navigateNextScreenLDObserver)
    }

    private val toolbarTitleLDObserver = Observer<Int> { title ->
        viewBinding.toolbar.title = resources.getString(title)
    }

    private val onSelectGroupItemLDObserver = Observer<Unit> {
        viewModel.onClickMenuButton(0)
        viewBinding.bottomNavigationView.selectedItemId = R.id.menuNotes
    }


    private val addNoteStatusLDObserver = Observer<Boolean> { status ->
        viewBinding.buttonAdd.isVisible = status
    }

    private val navigateNextScreenLDObserver = Observer<Int> { position ->
        viewBinding.viewPager.setCurrentItem(position, true)
    }

    /*override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_toolbar, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menuSorting -> {
                viewModel.onClickSortingMenu()
                true
            }
            else -> false
        }
    }*/

    override fun onResume() {
        super.onResume()
        Timber.d("onResume")
    }

    override fun onPause() {
        super.onPause()
        Timber.d("onPause")
    }
}