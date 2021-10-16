package com.project.testtaskl_tech.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.hannesdorfmann.adapterdelegates4.AbsListItemAdapterDelegate
import com.project.testtaskl_tech.R
import com.project.testtaskl_tech.remote.RemoteAllInformation
import com.project.testtaskl_tech.databinding.ItemAllInformationBinding

class AllInformationAdapterDelegate :
    AbsListItemAdapterDelegate<RemoteAllInformation, RemoteAllInformation, AllInformationAdapterDelegate.ViewHolder>() {

    override fun isForViewType(
        item: RemoteAllInformation,
        items: MutableList<RemoteAllInformation>,
        position: Int
    ) = true

    override fun onCreateViewHolder(parent: ViewGroup): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(inflater.inflate(R.layout.item_all_information, parent, false))
    }

    override fun onBindViewHolder(
        item: RemoteAllInformation,
        holder: ViewHolder,
        payloads: MutableList<Any>
    ) {
        holder.bind(item)
    }

    class ViewHolder(viewItem: View) : RecyclerView.ViewHolder(viewItem) {

        private lateinit var viewBinding: ItemAllInformationBinding

        fun bind(allInformation: RemoteAllInformation) {
            viewBinding = ItemAllInformationBinding.bind(itemView)
            Glide
                .with(itemView)
                .load("")
                .placeholder(R.drawable.ic_more_time)
                .error(R.drawable.ic_not_load)
                .into(viewBinding.ivImage)

            viewBinding.twTitle.text = allInformation.title
            viewBinding.twText.text = allInformation.text
            viewBinding.twDate.text = allInformation.date
        }
    }
}