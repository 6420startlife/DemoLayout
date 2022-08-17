package com.ptithcm.thuan6420.basecleanarchitecture.ui.home

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.work.PeriodicWorkRequest
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.ptithcm.thuan6420.basecleanarchitecture.data.datasources.api.ApiHelper
import com.ptithcm.thuan6420.basecleanarchitecture.data.datasources.api.RetrofitBuilder
import com.ptithcm.thuan6420.basecleanarchitecture.data.datasources.api.dto.home.ResponseFood
import com.ptithcm.thuan6420.basecleanarchitecture.data.worker.FoodWorker
import com.ptithcm.thuan6420.basecleanarchitecture.databinding.ActivityHomeBinding
import com.ptithcm.thuan6420.basecleanarchitecture.util.Status
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import com.xwray.groupie.Section
import java.util.concurrent.TimeUnit

class HomeActivity : AppCompatActivity(), HomeContact.ViewInterface {
    private lateinit var binding: ActivityHomeBinding
    private lateinit var viewModel: FoodViewModel
    private lateinit var groupAdapter: GroupAdapter<GroupieViewHolder>
    private lateinit var section: Section
    private lateinit var worker: PeriodicWorkRequest

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setViewModel()
        initRecyclerView()
        fetchingData()
        initEvent()
        initWorker()
    }

    private fun setViewModel() {
        viewModel = ViewModelProvider(
            this,
            FoodViewModelFactory(
                ApiHelper(RetrofitBuilder.apiService))
        )[FoodViewModel::class.java]
    }

    private fun initWorker() {
        worker = PeriodicWorkRequestBuilder<FoodWorker>(1, TimeUnit.MINUTES).build()
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
                        fetchData(resource.data)
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

    override fun showLoading() {
        binding.pbLoadingHome.visibility = View.VISIBLE
    }

    override fun hideLoading() {
        binding.pbLoadingHome.visibility = View.GONE
        binding.srlHome.isRefreshing = false
    }

    override fun showError(message: String) {
        Toast.makeText(this@HomeActivity, message, Toast.LENGTH_SHORT).show()
    }

    override fun fetchData(data: ResponseFood?) {
        section = Section()
        data?.data?.menus?.forEach { it ->
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