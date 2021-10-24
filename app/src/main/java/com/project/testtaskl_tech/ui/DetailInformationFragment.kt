package com.project.testtaskl_tech.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.project.testtaskl_tech.R
import com.project.testtaskl_tech.data.AllTheInformation
import com.project.testtaskl_tech.databinding.FragmentDetailInformationBinding
import com.project.testtaskl_tech.utility.withArguments

class DetailInformationFragment : Fragment(R.layout.fragment_detail_information) {

    private lateinit var viewBinding: FragmentDetailInformationBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val allInformation = arguments?.getParcelable<AllTheInformation>(KEY_GET_INFO)
        viewBinding = FragmentDetailInformationBinding.bind(view)
        with(viewBinding) {
            twTitleOne.text = allInformation?.title
            twTitleTwo.text = allInformation?.title
            twText.text = allInformation?.text

            btBack.setOnClickListener {
                parentFragmentManager.popBackStack()
            }
        }
        Glide
            .with(view)
            .load("http://dev-exam.l-tech.ru${allInformation?.imageUrl}")
            .placeholder(R.drawable.ic_more_time)
            .error(R.drawable.ic_not_load)
            .into(viewBinding.ivImage)
    }

    companion object {

        private const val KEY_GET_INFO = "getInfo"

        fun newInstance(info: AllTheInformation): DetailInformationFragment {
            return DetailInformationFragment().withArguments {
                putParcelable(KEY_GET_INFO, info)
            }
        }
    }
}