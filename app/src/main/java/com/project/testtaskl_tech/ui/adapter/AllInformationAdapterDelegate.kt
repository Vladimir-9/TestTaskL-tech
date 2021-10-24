package com.project.testtaskl_tech.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.hannesdorfmann.adapterdelegates4.AbsListItemAdapterDelegate
import com.project.testtaskl_tech.R
import com.project.testtaskl_tech.data.AllTheInformation
import com.project.testtaskl_tech.databinding.ItemAllInformationBinding
import com.project.testtaskl_tech.utility.formattedDate

class AllInformationAdapterDelegate(private val itemClick: (allInformation: AllTheInformation) -> Unit) :
    AbsListItemAdapterDelegate<AllTheInformation, AllTheInformation, AllInformationAdapterDelegate.ViewHolder>() {

    override fun isForViewType(
        item: AllTheInformation,
        items: MutableList<AllTheInformation>,
        position: Int
    ) = true

    override fun onCreateViewHolder(parent: ViewGroup): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(inflater.inflate(R.layout.item_all_information, parent, false), itemClick)
    }

    override fun onBindViewHolder(
        item: AllTheInformation,
        holder: ViewHolder,
        payloads: MutableList<Any>
    ) {
        holder.bind(item)
    }

    class ViewHolder(
        viewItem: View,
        private val itemClick: (allInformation: AllTheInformation) -> Unit
    ) : RecyclerView.ViewHolder(viewItem) {

        private lateinit var viewBinding: ItemAllInformationBinding

        fun bind(allInformation: AllTheInformation) {
            viewBinding = ItemAllInformationBinding.bind(itemView)
            Glide
                .with(itemView)
                .load("http://dev-exam.l-tech.ru${allInformation.imageUrl}")
                .placeholder(R.drawable.ic_more_time)
                .error(R.drawable.ic_not_load)
                .into(viewBinding.ivImage)

            with(viewBinding) {
                twTitle.text = allInformation.title
                twText.text = allInformation.text
                twDate.text = allInformation.date.formattedDate()
            }
            currentInformation(allInformation)
        }

        private fun currentInformation(allInformation: AllTheInformation) {
            itemView.setOnClickListener {
                itemClick(allInformation)
            }
        }
    }
}