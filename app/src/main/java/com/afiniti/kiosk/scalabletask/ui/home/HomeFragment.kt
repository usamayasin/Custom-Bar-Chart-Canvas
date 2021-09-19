package com.afiniti.kiosk.scalabletask.ui.home

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.navigation.Navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.afiniti.kiosk.scalabletask.R
import com.afiniti.kiosk.scalabletask.adapters.RepoAdapter
import com.afiniti.kiosk.scalabletask.databinding.HomeFragmentBinding
import com.afiniti.kiosk.scalabletask.di.ViewModelFactory
import com.afiniti.kiosk.scalabletask.model.RepoModel
import com.afiniti.kiosk.scalabletask.utils.AppConstants
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject
import kotlin.collections.ArrayList

class HomeFragment : Fragment(), RepoAdapter.RepoClickListener {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private val mViewModel: HomeViewModel by viewModels {
        viewModelFactory
    }

    private lateinit var mBinding: HomeFragmentBinding
    private var repoAdapter: RepoAdapter? = null
    private var reposList: ArrayList<RepoModel> = ArrayList<RepoModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        if(!::mBinding.isInitialized)
            mBinding = HomeFragmentBinding.inflate(inflater)
        return mBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        init()
        setUpObserver()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        AndroidSupportInjection.inject(this)
    }

    private fun init() {
        mViewModel.fetchRepos()

        mBinding.progressRepos.visibility = View.VISIBLE
        repoAdapter = RepoAdapter(requireContext(), reposList, this)
        mBinding.recyclerPopularRepos.run {
            mBinding.recyclerPopularRepos.adapter = repoAdapter
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun setUpObserver() {
        mViewModel.repoLiveData.observe(requireActivity()) {
            mBinding.progressRepos.visibility = View.GONE

            if (it != null && it.isNotEmpty()) {
                reposList = it as ArrayList<RepoModel>
                mBinding.recyclerPopularRepos.invalidate()
                repoAdapter!!.setDataList(it)
                repoAdapter!!.notifyDataSetChanged()
            }
        }
    }

    override fun onRepoClicked(position: Int) {
        val repo = reposList[position]
        findNavController().navigate(HomeFragmentDirections.toRepoDetailFragment(repo.name))
    }
}