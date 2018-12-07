package com.testtarget.ui.repodetail;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import com.testtarget.databinding.RepoDetailActivityBinding;
import com.testtarget.R;
import com.testtarget.network.model.TopWeekly;

public class RepoDetailActivity extends AppCompatActivity {
    public static final String EXTRA_REPO_ITEM = "com.testtarget.ui.repodetail.REPO_ITEM";
    private RepoDetailActivityBinding binding;

    public static Intent createIntent(Context context, TopWeekly topWeekly) {
        Intent intent = new Intent(context, RepoDetailActivity.class);
        intent.putExtra(EXTRA_REPO_ITEM, topWeekly);
        return intent;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        RepoDetailViewModel viewModel = createRepoDetailViewModel();
        setupBinding(viewModel);
        setupToolbar();
    }

    private void setupBinding(RepoDetailViewModel viewModel) {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_repo_detail);
        binding.setLifecycleOwner(this);
        binding.setViewModel(viewModel);
    }

    private RepoDetailViewModel createRepoDetailViewModel() {
        RepoDetailViewModel viewModel = ViewModelProviders.of(this).get(RepoDetailViewModel.class);
        TopWeekly topWeekly = getIntent().getParcelableExtra(EXTRA_REPO_ITEM);
        viewModel.avatar=topWeekly.getAvatar();
        viewModel.repoDescription.setValue(topWeekly.getName()+"\n\n"
        +topWeekly.getUrl()+"\n\n"+topWeekly.getUsername()+"\n\n"
                +topWeekly.getRepo().getDescription()+"\n\n"
                +topWeekly.getRepo().getName()+"\n\n"
                +topWeekly.getRepo().getUrl()+"\n");

        return viewModel;
    }

    private void setupToolbar() {
        if (binding == null) {
            throw new IllegalStateException("Must initialize binding before calling setupToolbar!");
        }

        setSupportActionBar(binding.toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle(getString(R.string.repository_description));
        binding.toolbar.setNavigationOnClickListener(v -> onBackPressed());
    }
}
