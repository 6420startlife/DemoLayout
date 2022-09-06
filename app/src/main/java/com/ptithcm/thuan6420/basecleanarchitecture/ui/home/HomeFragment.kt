package com.ptithcm.thuan6420.basecleanarchitecture.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.ptithcm.thuan6420.basecleanarchitecture.data.dto.home.ResponseListMenu
import com.ptithcm.thuan6420.basecleanarchitecture.databinding.FragmentHomeBinding
import com.ptithcm.thuan6420.basecleanarchitecture.ui.base.BaseFragment
import com.ptithcm.thuan6420.basecleanarchitecture.ui.login.LoginActivity
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import com.xwray.groupie.Section
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : BaseFragment() {
    private lateinit var binding: FragmentHomeBinding
    private val viewModel: FoodViewModel by viewModels()
    private lateinit var groupAdapter: GroupAdapter<GroupieViewHolder>
    private lateinit var section: Section
    @Inject lateinit var gsc: GoogleSignInClient

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        initRecyclerView()
        fetchingData()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.srlHome.setOnRefreshListener {
            fetchingData()
        }
        binding.btnSignOut.setOnClickListener(this)
        GoogleSignIn.getLastSignedInAccount(this.requireActivity())?.let {
            binding.tvIntro.text = "Xin chào, ${it.displayName}"
        }
    }

    private fun fetchingData() {
        viewModel.fetchFood().observe(viewLifecycleOwner){
            it?.let {
                submit(it.status, it.message, it.data)
            }
        }
    }

    override fun onSuccess(message: String?, data: Any?) {
        fetchData(data as ResponseListMenu?)
    }

    override fun onLoading() {
        showLoading()
    }

    override fun onError(message: String?) {
        hideLoading()
    }

    private fun showLoading() {
        binding.pbLoadingHome.visibility = View.VISIBLE
    }

    private fun hideLoading() {
        binding.pbLoadingHome.visibility = View.GONE
        binding.srlHome.isRefreshing = false
    }

    private fun initRecyclerView() {
        groupAdapter = GroupAdapter<GroupieViewHolder>().apply {
            spanCount = 2
        }
        binding.rvHome.apply {
            layoutManager = GridLayoutManager(this.context, groupAdapter.spanCount).apply {
                spanSizeLookup = groupAdapter.spanSizeLookup
            }
            adapter = groupAdapter
        }
    }

    override fun onSingleClick(v: View?) {
        super.onSingleClick(v)
        when(v) {
            binding.btnSignOut -> signOut()
        }
    }

    private fun signOut() {
        gsc.signOut().addOnCompleteListener{
            this.requireActivity().finishAffinity()
            this.startActivity(Intent(this.context, LoginActivity::class.java))
        }
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
}