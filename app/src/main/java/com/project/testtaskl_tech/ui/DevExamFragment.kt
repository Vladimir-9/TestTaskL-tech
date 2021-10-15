package com.project.testtaskl_tech.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.project.testtaskl_tech.remote.RemoteAllInformation
import com.project.testtaskl_tech.ui.adapter.AllInformationAdapter
import com.project.testtaskl_tech.R
import com.project.testtaskl_tech.databinding.FragmentDevExamBinding
import com.project.testtaskl_tech.remote.api.Network
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import timber.log.Timber

class DevExamFragment : Fragment(R.layout.fragment_dev_exam) {

    private var viewBinding: FragmentDevExamBinding? = null
    private var adapterAllInformation: AllInformationAdapter? = null
    private val dddd = listOf(
        RemoteAllInformation(id="bc618c06-ee28-4489-9bc5-f57e91b0a76e",
            title="Challenege Accepted",
            text="Ted, how many times have I told you to put the lid back on the peanut butter jar?! It’s this inconsiderate, immature jackassery that makes me feel like I’m living in The Real World House! And not the early days when they all had jobs and social consciences, I’m talking about Hawaii, and after!",
            image="/uploads/post/image/bc618c06-ee28-4489-9bc5-f57e91b0a76e/thumb_1_image_6.jpg",
            sort=10,
            date="2019-02-08T15:27:46Z"),
        RemoteAllInformation(id="bc618c06-ee28-4489-9bc5-f57e91b0a76e", title="Challenege Accepted", text="Ted, how many times have I told you to put the lid back on the peanut butter jar?! It’s this inconsiderate, immature jackassery that makes me feel like I’m living in The Real World House! And not the early days when they all had jobs and social consciences, I’m talking about Hawaii, and after!", image="/uploads/post/image/bc618c06-ee28-4489-9bc5-f57e91b0a76e/thumb_1_image_6.jpg", sort=10, date="2019-02-08T15:27:46Z"),
        RemoteAllInformation(id="bc618c06-ee28-4489-9bc5-f57e91b0a76e", title="Challenege Accepted", text="Ted, how many times have I told you to put the lid back on the peanut butter jar?! It’s this inconsiderate, immature jackassery that makes me feel like I’m living in The Real World House! And not the early days when they all had jobs and social consciences, I’m talking about Hawaii, and after!", image="/uploads/post/image/bc618c06-ee28-4489-9bc5-f57e91b0a76e/thumb_1_image_6.jpg", sort=10, date="2019-02-08T15:27:46Z"),

    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewBinding = FragmentDevExamBinding.bind(view)
        adapterAllInformation = AllInformationAdapter()
        initRecyclerView()
         adapterAllInformation!!.items = dddd


        viewBinding!!.btRefresh.setOnClickListener {
            lifecycleScope.launch(Dispatchers.IO) {

                val dd = async { Network.createRetrofit.getAllInformation() }
                val aaa = dd.await()
                aaa.forEach {
                    Timber.d("$it")
                }
                delay(1000)
                adapterAllInformation!!.items = aaa
            }
        }


    }

    private fun initRecyclerView() {
        with(viewBinding!!.recyclerView) {
            adapter = adapterAllInformation!!
            setHasFixedSize(true)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        viewBinding = null
        adapterAllInformation = null
    }
}