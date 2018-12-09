package com.testtarget.ui.repo;

import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;

import com.testtarget.databinding.TopRepoListActivityBinding;

import com.testtarget.R;
import com.testtarget.network.model.TopWeekly;
import com.testtarget.network.repository.Repository;
import com.testtarget.ui.repodetail.RepoDetailActivity;
import com.testtarget.utils.schedulers.SchedulerProvider;


public class TopRepoListActivity extends AppCompatActivity {
    private TopRepoListActivityBinding binding;
    private TopRepoListPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TopRepoListViewModel viewModel = createTopRepoListViewModel();
        setupPresenter(viewModel);
        setupBinding(viewModel);
        setupToolbar();
        setupRecyclerView();
        presenter.getTopWeeklyRepo();
    }

    private void setupToolbar() {
        if (binding == null) {
            throw new IllegalStateException("Must initialize binding before calling setupToolbar!");
        }
        setSupportActionBar(binding.toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(R.string.top_weekly_repo);
    }

    private void setupRecyclerView() {
        if (presenter == null) {
            throw new IllegalStateException("Must initialize presenter before calling Journal setupRecyclerView!");
        }
        if (binding == null)
            throw new IllegalStateException("Must initialize binding before calling Journal setupRecyclerView!");
        TopRepoAdapter adapter = new TopRepoAdapter(this);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerView.setAdapter(adapter);
        presenter.viewModel.repoListData.observe(this, adapter::updateRepoListViewModels);
    }

    private void setupBinding(TopRepoListViewModel viewModel) {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_top_repo_list);
        binding.setLifecycleOwner(this);
        binding.setViewModel(viewModel);
    }

    private void setupPresenter(TopRepoListViewModel viewModel) {
        Repository repository = Repository.getRepository();
        presenter = new TopRepoListPresenter(viewModel, repository,new SchedulerProvider());
    }

    private TopRepoListViewModel createTopRepoListViewModel() {
        TopRepoListViewModel viewModel = ViewModelProviders.of(this).get(TopRepoListViewModel.class);
        return viewModel;
    }

    public void onRepoTypeClicked(TopWeekly topWeekly) {
        startActivity(RepoDetailActivity.createIntent(this, topWeekly));
    }
}
