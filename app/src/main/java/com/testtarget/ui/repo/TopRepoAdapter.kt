package com.testtarget.ui.repo

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.testtarget.databinding.RepoTypeItemBinding
import com.testtarget.network.model.TopWeekly
import java.util.*

class TopRepoAdapter(private val clickHandler: TopRepoListActivity) : RecyclerView.Adapter<TopRepoAdapter.ViewHolder>() {
    private val repoListViewModels = ArrayList<TopWeekly>()

    fun updateRepoListViewModels(repoListViewModels: List<TopWeekly>) {
        this.repoListViewModels.clear()
        this.repoListViewModels.addAll(repoListViewModels)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.createViewHolder(viewGroup, clickHandler)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val repoListViewModel = repoListViewModels[position]
        viewHolder.bind(repoListViewModel)
    }

    override fun getItemCount(): Int {
        return repoListViewModels.size
    }

    override fun getItemId(position: Int): Long {
        return repoListViewModels[position].name!!.hashCode().toLong()
    }

    class ViewHolder(private val binding: RepoTypeItemBinding, clickHandler: TopRepoListActivity) : RecyclerView.ViewHolder(binding.root) {

        init {
            // Set in constructor since these do not change
            binding.eventHandler = clickHandler
        }

        fun bind(repoListViewModel: TopWeekly) {
            binding.viewModel = repoListViewModel
            binding.repoViewModel = repoListViewModel.repo
        }

        companion object {

            fun createViewHolder(parent: ViewGroup, clickHandler: TopRepoListActivity): ViewHolder {
                val inflater = LayoutInflater.from(parent.context)
                val binding = RepoTypeItemBinding.inflate(inflater, parent, false)
                return ViewHolder(binding, clickHandler)
            }
        }
    }
}
