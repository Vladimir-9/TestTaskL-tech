package com.project.testtaskl_tech.ui.dev_exam

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import com.project.testtaskl_tech.OpenNewFragment
import com.project.testtaskl_tech.R
import com.project.testtaskl_tech.databinding.FragmentDevExamBinding
import com.project.testtaskl_tech.remote.RemoteAllInformation
import com.project.testtaskl_tech.ui.DetailInformationFragment
import com.project.testtaskl_tech.ui.adapter.AllInformationAdapter
import com.project.testtaskl_tech.utility.ItemDecoration
import com.project.testtaskl_tech.utility.autoCleared
import com.project.testtaskl_tech.utility.formattedDate
import timber.log.Timber

class DevExamFragment : Fragment(R.layout.fragment_dev_exam) {

    private var viewBinding: FragmentDevExamBinding by autoCleared()
    private val viewModel: DevExamViewModel by viewModels()
    private var adapterAllInformation: AllInformationAdapter by autoCleared()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewBinding = FragmentDevExamBinding.bind(view)
        viewBinding.btRefresh.setOnClickListener {
            viewModel.getRefreshInformation()
        }
        visibleProgressBar(true)
        initAdapter()
        initRecyclerView()
        observeAllInformation()
        observeLifeCycle()
    }

    private fun observeAllInformation() {
        viewModel.allInformationLiveDate.observe(viewLifecycleOwner) { loadState ->
            when (loadState) {
                is DevExamLoadState.Success -> {
                    viewBinding.btSortServer.setOnClickListener {
                        adapterAllInformation.items = sort(loadState.listInfo, false)
                    }
                    viewBinding.btSortDate.setOnClickListener {
                        adapterAllInformation.items = sort(loadState.listInfo, true)
                    }
                    adapterAllInformation.items = loadState.listInfo
                    visibleProgressBar(false)
                }
                is DevExamLoadState.Error -> {
                    visibleProgressBar(false)
                }
                is DevExamLoadState.LoadState -> {
                }
            }
        }
    }

    private fun visibleProgressBar(isVisible: Boolean) {
        viewBinding.progressBar.isVisible = isVisible
        viewBinding.recyclerView.isVisible = isVisible.not()
        viewBinding.btSortServer.isVisible = isVisible.not()
        viewBinding.btSortDate.isVisible = isVisible.not()
    }

    private fun sort(
        listInfo: List<RemoteAllInformation>,
        isSortDay: Boolean
    ): List<RemoteAllInformation> {
        return if (isSortDay) listInfo.sortedBy { it.date.formattedDate() }
        else listInfo.sortedBy { it.sort }
    }

    private fun initRecyclerView() {
        with(viewBinding.recyclerView) {
            adapter = adapterAllInformation
            addItemDecoration(ItemDecoration())
        }
    }

    private fun initAdapter() {
        adapterAllInformation = AllInformationAdapter { info ->
            (requireActivity() as? OpenNewFragment)?.openFragment(
                DetailInformationFragment.newInstance(info), true
            )
        }
    }

    private fun observeLifeCycle() {
        viewLifecycleOwnerLiveData.observe(viewLifecycleOwner) { viewLifecycleOwner ->
            viewLifecycleOwner?.lifecycle?.addObserver(object : LifecycleEventObserver {
                override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
                    when (event) {
                        Lifecycle.Event.ON_START -> viewModel.getAllInformation()
                        Lifecycle.Event.ON_PAUSE -> viewModel.jobCancel()
                        else -> Timber.d("Lifecycle.Event - $event")
                    }
                }
            })
        }
    }
}