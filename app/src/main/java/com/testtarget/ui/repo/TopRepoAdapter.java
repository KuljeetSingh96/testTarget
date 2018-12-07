package com.testtarget.ui.repo;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.testtarget.network.model.TopWeekly;
import com.testtarget.databinding.RepoTypeItemBinding;
import java.util.ArrayList;
import java.util.List;

public class TopRepoAdapter extends RecyclerView.Adapter<TopRepoAdapter.ViewHolder>{
    private List<TopWeekly> repoListViewModels = new ArrayList<>();
    private TopRepoListActivity clickHandler;

    public TopRepoAdapter(TopRepoListActivity clickHandler) {
        this.clickHandler = clickHandler;
    }

    public void updateRepoListViewModels(List<TopWeekly> repoListViewModels) {
        this.repoListViewModels.clear();
        this.repoListViewModels.addAll(repoListViewModels);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        return ViewHolder.createViewHolder(viewGroup, clickHandler);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        TopWeekly repoListViewModel = repoListViewModels.get(position);
        viewHolder.bind(repoListViewModel);
    }

    @Override
    public int getItemCount() {
        return repoListViewModels.size();
    }
    @Override
    public long getItemId(int position) {
        return repoListViewModels.get(position).getName().hashCode();
    }
    static class ViewHolder extends RecyclerView.ViewHolder {

        public static ViewHolder createViewHolder(ViewGroup parent,TopRepoListActivity clickHandler) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            RepoTypeItemBinding binding = RepoTypeItemBinding.inflate(inflater, parent, false);
            return new ViewHolder(binding, clickHandler);
        }

        private RepoTypeItemBinding binding;

        public ViewHolder(RepoTypeItemBinding binding, TopRepoListActivity clickHandler) {
            super(binding.getRoot());
            this.binding = binding;
            // Set in constructor since these do not change
            binding.setEventHandler(clickHandler);
        }

        public void bind(TopWeekly repoListViewModel) {
            binding.setViewModel(repoListViewModel);
            binding.setRepoViewModel(repoListViewModel.getRepo());
        }
    }
}
