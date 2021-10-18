package com.project.testtaskl_tech.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.hannesdorfmann.adapterdelegates4.AbsListItemAdapterDelegate
import com.project.testtaskl_tech.R
import com.project.testtaskl_tech.databinding.ItemAllInformationBinding
import com.project.testtaskl_tech.remote.RemoteAllInformation
import com.project.testtaskl_tech.utility.formattedDate

class AllInformationAdapterDelegate(private val itemClick: (allInformation: RemoteAllInformation) -> Unit) :
    AbsListItemAdapterDelegate<RemoteAllInformation, RemoteAllInformation, AllInformationAdapterDelegate.ViewHolder>() {

    override fun isForViewType(
        item: RemoteAllInformation,
        items: MutableList<RemoteAllInformation>,
        position: Int
    ) = true

    override fun onCreateViewHolder(parent: ViewGroup): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(inflater.inflate(R.layout.item_all_information, parent, false), itemClick)
    }

    override fun onBindViewHolder(
        item: RemoteAllInformation,
        holder: ViewHolder,
        payloads: MutableList<Any>
    ) {
        holder.bind(item)
    }

    class ViewHolder(
        viewItem: View,
        private val itemClick: (allInformation: RemoteAllInformation) -> Unit
    ) : RecyclerView.ViewHolder(viewItem) {

        private lateinit var viewBinding: ItemAllInformationBinding

        fun bind(allInformation: RemoteAllInformation) {
            viewBinding = ItemAllInformationBinding.bind(itemView)
            Glide
                .with(itemView)
                .load("http://dev-exam.l-tech.ru${allInformation.imageUrl}")
                .placeholder(R.drawable.ic_more_time)
                .error(R.drawable.ic_not_load)
                .into(viewBinding.ivImage)

            viewBinding.twTitle.text = allInformation.title
            viewBinding.twText.text = allInformation.text
            viewBinding.twDate.text = allInformation.date.formattedDate()
            currentInformation(allInformation)
        }

        private fun currentInformation(allInformation: RemoteAllInformation) {
            itemView.setOnClickListener {
                itemClick(allInformation)
            }
        }
    }
}