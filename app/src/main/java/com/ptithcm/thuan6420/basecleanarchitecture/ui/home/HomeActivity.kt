package com.ptithcm.thuan6420.basecleanarchitecture.ui.home

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import com.ptithcm.thuan6420.basecleanarchitecture.data.dto.home.ResponseListMenu
import com.ptithcm.thuan6420.basecleanarchitecture.databinding.ActivityHomeBinding
import com.ptithcm.thuan6420.basecleanarchitecture.util.Status
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import com.xwray.groupie.Section
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    private val viewModel: FoodViewModel by viewModels()
    @Inject lateinit var worker: PeriodicWorkRequest
    private lateinit var groupAdapter: GroupAdapter<GroupieViewHolder>
    private lateinit var section: Section

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initRecyclerView()
        fetchingData()
        initEvent()
        initWorker()
    }

    private fun initWorker() {
        WorkManager.getInstance(this).enqueue(worker)
    }

    private fun initEvent() {
        binding.srlHome.setOnRefreshListener {
            fetchingData()
        }
    }

    private fun fetchingData() {
        viewModel.fetchData().observe(this){
            it?.let { resource ->
                when(resource.status) {
                    Status.SUCCESS -> {
                        hideLoading()
                        fetchData(resource.data as ResponseListMenu?)
                    }
                    Status.FAILED, Status.ERROR -> {
                        hideLoading()
                        showError(resource.message.toString())
                    }
                    Status.LOADING -> {
                        showLoading()
                    }
                }
            }
        }
    }

    private fun initRecyclerView() {
        groupAdapter = GroupAdapter<GroupieViewHolder>().apply {
            spanCount = 2
        }
        binding.rvHome.apply {
            layoutManager = GridLayoutManager(this@HomeActivity, groupAdapter.spanCount).apply {
                spanSizeLookup = groupAdapter.spanSizeLookup
            }
            adapter = groupAdapter
        }
    }

    private fun showLoading() {
        binding.pbLoadingHome.visibility = View.VISIBLE
    }

    private fun hideLoading() {
        binding.pbLoadingHome.visibility = View.GONE
        binding.srlHome.isRefreshing = false
    }

    private fun showError(message: String) {
        Toast.makeText(this@HomeActivity, message, Toast.LENGTH_SHORT).show()
    }

    private fun fetchData(data: ResponseListMenu?) {
        section = Section()
        data?.menus?.forEach { it ->
            section.setHeader(HeaderFoodItem(it.userName))
            it.products.forEach {
                section.add(FoodItem(it))
            }
        }
        groupAdapter.clear()
        groupAdapter.add(section)
    }

    override fun onResume() {
        super.onResume()
        WorkManager.getInstance(this).cancelWorkById(worker.id)
    }

    override fun onStop() {
        super.onStop()
        WorkManager.getInstance(this).enqueue(worker)
    }
}