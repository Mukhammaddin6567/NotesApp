package uz.gita.notesapp.presentation.ui.screen

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import uz.gita.notesapp.R
import uz.gita.notesapp.data.model.GroupData
import uz.gita.notesapp.databinding.ScreenGroupsBinding
import uz.gita.notesapp.presentation.ui.adapter.GroupsAdapter
import uz.gita.notesapp.presentation.viewmodels.GroupsVM
import uz.gita.notesapp.presentation.viewmodels.impl.GroupsVMImpl

@AndroidEntryPoint
class GroupsScreen : Fragment(R.layout.screen_groups) {

    private val viewBinding by viewBinding(ScreenGroupsBinding::bind)
    private val viewModel: GroupsVM by viewModels<GroupsVMImpl>()
    private val adapter: GroupsAdapter by lazy { GroupsAdapter() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        subscribeViewBinding(viewBinding)
        subscribeViewModel(viewModel)
    }

    private fun subscribeViewBinding(viewBinding: ScreenGroupsBinding) = with(viewBinding) {
        groupsList.adapter = adapter
        groupsList.layoutManager = GridLayoutManager(requireContext(), 2)
        adapter.setOnClickGroupItemListener { id ->
            viewModel.onClickGroupItem(id)
        }
    }

    private fun subscribeViewModel(viewModel: GroupsVM) = with(viewModel) {
        groupsListLD.observe(viewLifecycleOwner, groupsListLDObserver)
    }

    private val groupsListLDObserver = Observer<List<GroupData>> {
        Timber.d("groupsListLDObserver: $it")
        adapter.submitList(it)
    }

    override fun onResume() {
        super.onResume()
        viewModel.loadGroupsData()
    }

}