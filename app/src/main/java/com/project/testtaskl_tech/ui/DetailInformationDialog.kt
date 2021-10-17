package com.project.testtaskl_tech.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.project.testtaskl_tech.R
import com.project.testtaskl_tech.databinding.DialogDetailInformationBinding
import com.project.testtaskl_tech.remote.RemoteAllInformation
import com.project.testtaskl_tech.utility.withArguments

class DetailInformationDialog : Fragment(R.layout.dialog_detail_information) {

    private lateinit var viewBinding: DialogDetailInformationBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val allInformation = arguments?.getParcelable<RemoteAllInformation>(KEY_GET_INFO)
        viewBinding = DialogDetailInformationBinding.bind(view)
        viewBinding.twTitleOne.text = allInformation?.title
        viewBinding.twTitleTwo.text = allInformation?.title
        viewBinding.twText.text = allInformation?.text

        viewBinding.btBack.setOnClickListener {
            parentFragmentManager.popBackStack()
        }

        Glide
            .with(view)
            .load("")
            .placeholder(R.drawable.ic_more_time)
            .error(R.drawable.ic_not_load)
            .into(viewBinding.ivImage)
    }

    companion object {

        private const val KEY_GET_INFO = "getInfo"

        fun newInstance(info: RemoteAllInformation): DetailInformationDialog {
            return DetailInformationDialog().withArguments {
                putParcelable(KEY_GET_INFO, info)
            }
        }
    }
}