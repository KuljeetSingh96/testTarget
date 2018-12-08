package com.testtarget.ui.repodetail

import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.testtarget.R
import com.testtarget.databinding.RepoDetailActivityBinding
import com.testtarget.network.model.TopWeekly

class RepoDetailActivity : AppCompatActivity() {
    private var binding: RepoDetailActivityBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewModel = createRepoDetailViewModel()
        setupBinding(viewModel)
        setupToolbar()
    }

    private fun setupBinding(viewModel: RepoDetailViewModel) {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_repo_detail)
        binding!!.setLifecycleOwner(this)
        binding!!.viewModel = viewModel
    }

    private fun createRepoDetailViewModel(): RepoDetailViewModel {
        val viewModel = ViewModelProviders.of(this).get(RepoDetailViewModel::class.java)
        val (username, name, url, avatar, repo) = intent.getParcelableExtra<TopWeekly>(EXTRA_REPO_ITEM)
        viewModel.avatar = avatar
        viewModel.repoDescription.value = (name + "\n\n"
                + url + "\n\n" + username + "\n\n"
                + repo!!.description + "\n\n"
                + repo.name + "\n\n"
                + repo.url + "\n")

        return viewModel
    }

    private fun setupToolbar() {
        if (binding == null) {
            throw IllegalStateException("Must initialize binding before calling setupToolbar!")
        }

        setSupportActionBar(binding!!.toolbar)
        val actionBar = supportActionBar
        actionBar!!.setDisplayShowHomeEnabled(true)
        actionBar.setDisplayHomeAsUpEnabled(true)
        actionBar.title = getString(R.string.repository_description)
        binding!!.toolbar.setNavigationOnClickListener { v -> onBackPressed() }
    }

    companion object {
        val EXTRA_REPO_ITEM = "com.testtarget.ui.repodetail.REPO_ITEM"

        fun createIntent(context: Context, topWeekly: TopWeekly): Intent {
            val intent = Intent(context, RepoDetailActivity::class.java)
            intent.putExtra(EXTRA_REPO_ITEM, topWeekly)
            return intent
        }
    }
}
