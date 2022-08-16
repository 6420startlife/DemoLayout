package com.ptithcm.thuan6420.basecleanarchitecture.ui.home

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.work.PeriodicWorkRequest
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.ptithcm.thuan6420.basecleanarchitecture.data.datasources.UserLocalDataSource
import com.ptithcm.thuan6420.basecleanarchitecture.data.datasources.api.ApiHelper
import com.ptithcm.thuan6420.basecleanarchitecture.data.datasources.api.RetrofitBuilder
import com.ptithcm.thuan6420.basecleanarchitecture.data.datasources.api.home.ResponseFood
import com.ptithcm.thuan6420.basecleanarchitecture.data.repositories.FoodRepository
import com.ptithcm.thuan6420.basecleanarchitecture.databinding.ActivityHomeBinding
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import com.xwray.groupie.Section
import java.util.concurrent.TimeUnit

class HomeActivity : AppCompatActivity(), HomeContact.ViewInterface {
    private lateinit var binding: ActivityHomeBinding
    private lateinit var presenter: HomePresenter
    private lateinit var groupAdapter: GroupAdapter<GroupieViewHolder>
    private lateinit var section: Section
    private lateinit var worker: PeriodicWorkRequest

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initPresenter()
        initRecyclerView()
        fetchingData()
        initEvent()
        initWorker()
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

    private fun fetchingData() = lifecycleScope.launchWhenResumed {
        val id = UserLocalDataSource().getUserFromLocal().id
        presenter.onFetchingData(id)
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

    private fun initPresenter() {
        presenter = HomePresenter(FoodRepository(ApiHelper(RetrofitBuilder.apiService)), this)
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

    override fun onDestroy() {
        super.onDestroy()
        WorkManager.getInstance(this).cancelAllWork()
    }
}