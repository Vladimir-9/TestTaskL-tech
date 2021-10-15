package com.project.testtaskl_tech.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hannesdorfmann.adapterdelegates4.AbsListItemAdapterDelegate
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
       return ViewHolder(ItemAllInformationBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(
        item: RemoteAllInformation,
        holder: ViewHolder,
        payloads: MutableList<Any>
    ) {
        holder.bind(item)
    }

    class ViewHolder(private val viewItem: ItemAllInformationBinding) :
        RecyclerView.ViewHolder(viewItem.root) {

        fun bind(allInformation: RemoteAllInformation) {
//            Glide
//                .with(itemView)
//                .load()
//                .placeholder(R.drawable.ic_movie)
//                .error(R.drawable.ic_not_poster)
//                .into(viewBinding.ivMovie)

            viewItem.twTitle.text = allInformation.title
            viewItem.twText.text = allInformation.text
            viewItem.twDate.text = allInformation.date
        }
    }
}