package com.project.testtaskl_tech.ui.dev_exam

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import com.project.testtaskl_tech.OpenNewFragment
import com.project.testtaskl_tech.R
import com.project.testtaskl_tech.databinding.FragmentDevExamBinding
import com.project.testtaskl_tech.ui.DetailInformationDialog
import com.project.testtaskl_tech.ui.adapter.AllInformationAdapter
import com.project.testtaskl_tech.utility.ItemDecoration
import timber.log.Timber

class DevExamFragment : Fragment(R.layout.fragment_dev_exam) {

    private var viewBinding: FragmentDevExamBinding? = null
    private val viewModel: DevExamViewModel by viewModels()
    private var adapterAllInformation: AllInformationAdapter? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewBinding = FragmentDevExamBinding.bind(view)
        adapterAllInformation = AllInformationAdapter { info ->
            (requireActivity() as? OpenNewFragment)?.openFragment(
                DetailInformationDialog.newInstance(info), true
            )
        }
        viewBinding!!.btRefresh.setOnClickListener {
            viewModel.getRefreshInformation()
        }
        initRecyclerView()
        observeAllInformation()
        observeRefreshInformation()
        observeLifeCycle()
    }

    private fun observeAllInformation() {
        viewModel.allInformationLiveDate.observe(viewLifecycleOwner) { listAllInformation ->
            adapterAllInformation!!.items = listAllInformation
        }
    }

    private fun observeRefreshInformation() {
        viewModel.refreshInformationLiveDate.observe(viewLifecycleOwner) { listAllInformation ->
            adapterAllInformation!!.items = listAllInformation
        }
    }

    private fun initRecyclerView() {
        with(viewBinding!!.recyclerView) {
            adapter = adapterAllInformation!!
            addItemDecoration(ItemDecoration())
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

    override fun onDestroyView() {
        super.onDestroyView()
        viewBinding = null
        adapterAllInformation = null
    }
}