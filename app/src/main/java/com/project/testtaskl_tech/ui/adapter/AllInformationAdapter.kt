package com.project.testtaskl_tech.ui.adapter

import androidx.recyclerview.widget.DiffUtil
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter
import com.project.testtaskl_tech.data.AllTheInformation

class AllInformationAdapter(itemClick: (allInformation: AllTheInformation) -> Unit) :
    AsyncListDifferDelegationAdapter<AllTheInformation>(AllInformationDiffUtil()) {

    init {
        delegatesManager.addDelegate(AllInformationAdapterDelegate(itemClick))
    }

    class AllInformationDiffUtil : DiffUtil.ItemCallback<AllTheInformation>() {
        override fun areItemsTheSame(
            oldItem: AllTheInformation,
            newItem: AllTheInformation
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: AllTheInformation,
            newItem: AllTheInformation
        ): Boolean {
            return oldItem == newItem
        }
    }
}