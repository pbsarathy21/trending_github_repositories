package com.pbsarathy21.trendingrepos.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.pbsarathy21.trendingrepos.data.models.Repository
import com.pbsarathy21.trendingrepos.databinding.AdapterRepositoryBinding

class RepositoryAdapter : RecyclerView.Adapter<RepositoryAdapter.RepositoryViewHolder>() {

    private var repositories = emptyList<Repository>()

    fun updateRepositories(repositories: List<Repository>) {
        this.repositories = repositories
        notifyDataSetChanged()
    }

    inner class RepositoryViewHolder(val binding: AdapterRepositoryBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepositoryViewHolder {
        return RepositoryViewHolder(
            AdapterRepositoryBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: RepositoryViewHolder, position: Int) {
        holder.binding.repository = repositories[0]
    }

    override fun getItemCount(): Int {
        return repositories.size
    }
}
