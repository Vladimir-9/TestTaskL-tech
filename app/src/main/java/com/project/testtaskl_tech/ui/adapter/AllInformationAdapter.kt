package com.project.testtaskl_tech.ui.adapter

import androidx.recyclerview.widget.DiffUtil
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter
import com.project.testtaskl_tech.remote.RemoteAllInformation

class AllInformationAdapter :
    AsyncListDifferDelegationAdapter<RemoteAllInformation>(AllInformationDiffUtil()) {

    init {
        delegatesManager.addDelegate(AllInformationAdapterDelegate())
    }

    class AllInformationDiffUtil : DiffUtil.ItemCallback<RemoteAllInformation>(){
        override fun areItemsTheSame(oldItem: RemoteAllInformation, newItem: RemoteAllInformation): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: RemoteAllInformation, newItem: RemoteAllInformation): Boolean {
            return oldItem == newItem
        }
    }
}