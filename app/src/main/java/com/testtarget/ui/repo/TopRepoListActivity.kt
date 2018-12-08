package com.testtarget.ui.repo

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.testtarget.R
import com.testtarget.databinding.TopRepoListActivityBinding
import com.testtarget.network.model.TopWeekly
import com.testtarget.network.repository.Repository
import com.testtarget.ui.repodetail.RepoDetailActivity


class TopRepoListActivity : AppCompatActivity() {
    private var binding: TopRepoListActivityBinding? = null
    private var presenter: TopRepoListPresenter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewModel = createTopRepoListViewModel()
        setupPresenter(viewModel)
        setupBinding(viewModel)
        setupToolbar()
        setupRecyclerView()
        presenter!!.getTopWeeklyRepo()
    }

    private fun setupToolbar() {
        if (binding == null) {
            throw IllegalStateException("Must initialize binding before calling setupToolbar!")
        }
        setSupportActionBar(binding!!.toolbar)
        val actionBar = supportActionBar
        actionBar!!.setTitle(R.string.top_weekly_repo)
    }

    private fun setupRecyclerView() {
        if (presenter == null) {
            throw IllegalStateException("Must initialize presenter before calling Journal setupRecyclerView!")
        }
        if (binding == null)
            throw IllegalStateException("Must initialize binding before calling Journal setupRecyclerView!")
        val adapter = TopRepoAdapter(this)
        binding!!.recyclerView.layoutManager = LinearLayoutManager(this) as RecyclerView.LayoutManager?
        binding!!.recyclerView.adapter = adapter
        presenter!!.viewModel.repoListData.observe(this, Observer<List<TopWeekly>> { adapter.updateRepoListViewModels(it!!) })
    }

    private fun setupBinding(viewModel: TopRepoListViewModel) {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_top_repo_list)
        binding!!.setLifecycleOwner(this)
        binding!!.viewModel = viewModel
    }

    private fun setupPresenter(viewModel: TopRepoListViewModel) {
        val repository = Repository.getRepository()
        presenter = TopRepoListPresenter(viewModel, repository)
    }

    private fun createTopRepoListViewModel(): TopRepoListViewModel {
        return ViewModelProviders.of(this).get(TopRepoListViewModel::class.java)
    }

    fun onRepoTypeClicked(topWeekly: TopWeekly) {
        startActivity(RepoDetailActivity.createIntent(this, topWeekly))
    }
}
