package com.afiniti.kiosk.scalabletask.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.afiniti.kiosk.scalabletask.databinding.RepoItemLayoutBinding
import com.afiniti.kiosk.scalabletask.model.RepoModel
import java.util.ArrayList

class RepoAdapter(
    var context: Context, listModels: List<RepoModel>,
    private val listener: RepoClickListener
) :
    RecyclerView.Adapter<RepoAdapter.ReposViewHolder>() {

    interface RepoClickListener {
        fun onRepoClicked(position: Int)
    }

    var listModels: ArrayList<RepoModel>

    init {
        this.listModels = listModels as ArrayList<RepoModel>
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setDataList(listModels: List<RepoModel>) {
        this.listModels.clear()
        this.listModels = listModels as ArrayList<RepoModel>
        this.notifyDataSetChanged()
    }

    fun clearDataList() {
        this.listModels.clear()
        this.notifyDataSetChanged()
    }

    fun updateList(dataList: List<RepoModel>) {
        listModels = dataList as ArrayList<RepoModel>
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ReposViewHolder {
        val repoItemLayoutBinding = RepoItemLayoutBinding.inflate(
            LayoutInflater.from(parent.context),parent,false
        )

        return ReposViewHolder(repoItemLayoutBinding, listModels, context)
    }

    override fun onBindViewHolder(holder: ReposViewHolder, position: Int) {
        val data: RepoModel = listModels[position]
        holder.bind(data)
    }

    override fun getItemCount(): Int {
        return listModels.size
    }

    inner class ReposViewHolder(
        private val itemViewDataBinding: RepoItemLayoutBinding,
        imagesList: List<RepoModel>,
        var context: Context
    ) :
        RecyclerView.ViewHolder(itemViewDataBinding.root), View.OnClickListener {
        var dataList: List<RepoModel>

        override fun onClick(v: View) {
            if (adapterPosition < dataList.size) listener.onRepoClicked(adapterPosition)
        }

        init {
            dataList = imagesList
            try {
                itemViewDataBinding.root.setOnClickListener(this)
            } catch (e: Exception) {
                Toast.makeText(context, "Error in View Holder " + e.message, Toast.LENGTH_LONG).show()
            }
        }
        fun bind(item: RepoModel) {
            itemViewDataBinding.data = item
        }
    }
}