package com.project.testtaskl_tech.ui.deve_xam

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.project.testtaskl_tech.remote.RemoteAllInformation
import com.project.testtaskl_tech.ui.adapter.AllInformationAdapter
import com.project.testtaskl_tech.R
import com.project.testtaskl_tech.databinding.FragmentDevExamBinding
import com.project.testtaskl_tech.remote.api.Network
import com.project.testtaskl_tech.utility.ItemDecoration
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import timber.log.Timber

class DevExamFragment : Fragment(R.layout.fragment_dev_exam) {

    private var viewBinding: FragmentDevExamBinding? = null
    private val viewModel: DevExamViewModel by viewModels()
    private var adapterAllInformation: AllInformationAdapter? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewBinding = FragmentDevExamBinding.bind(view)
        adapterAllInformation = AllInformationAdapter()
        initRecyclerView()
        observeAllInformation()
        observeRefreshInformation()

        viewBinding!!.btRefresh.setOnClickListener {
            viewModel.getRefreshInformation()
        }
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

    override fun onDestroyView() {
        super.onDestroyView()
        viewBinding = null
        adapterAllInformation = null
    }
}