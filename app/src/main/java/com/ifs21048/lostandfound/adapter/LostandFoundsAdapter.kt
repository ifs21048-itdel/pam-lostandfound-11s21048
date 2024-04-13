package com.ifs21048.lostandfound.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ifs21048.lostandfound.data.remote.response.LostFoundsItemResponse
import com.ifs21048.lostandfound.databinding.ItemRowLostandfoundBinding

class LostandFoundsAdapter :
    ListAdapter<LostFoundsItemResponse, LostandFoundsAdapter.MyViewHolder>(DIFF_CALLBACK) {

    private lateinit var onItemClickCallback: OnItemClickCallback
    private var originalData = mutableListOf<LostFoundsItemResponse>()
    private var filteredData = mutableListOf<LostFoundsItemResponse>()

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemRowLostandfoundBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val data = originalData[originalData.indexOf(getItem(position))]

        holder.binding.cbItemTodoIsFinished.setOnCheckedChangeListener(null)
        holder.binding.cbItemTodoIsFinished.setOnLongClickListener(null)

        holder.bind(data)

        holder.binding.cbItemTodoIsFinished.setOnCheckedChangeListener { _, isChecked ->
            data.isCompleted = if (isChecked) 1 else 0
            holder.bind(data)
            onItemClickCallback.onCheckedChangeListener(data, isChecked)
        }

        holder.binding.ivItemTodoDetail.setOnClickListener {
            onItemClickCallback.onClickDetailListener(data.id)
        }
    }

    class MyViewHolder(val binding: ItemRowLostandfoundBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(data: LostFoundsItemResponse) {
            binding.apply {
                tvItemTodoTitle.text = data.title
                cbItemTodoIsFinished.isChecked = data.isCompleted == 1
            }
        }
    }

    fun submitOriginalList(list: List<LostFoundsItemResponse>) {
        originalData = list.toMutableList()
        filteredData = list.toMutableList()
        submitList(originalData)
    }

    fun filter(query: String) {
        filteredData = if (query.isEmpty()) {
            originalData
        } else {
            originalData.filter {
                (it.title.contains(query, ignoreCase = true))
            }.toMutableList()
        }
        submitList(filteredData)
    }

    interface OnItemClickCallback {
        fun onCheckedChangeListener(todo: LostFoundsItemResponse, isChecked: Boolean)
        fun onClickDetailListener(todoId: Int)
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<LostFoundsItemResponse>() {
            override fun areItemsTheSame(
                oldItem: LostFoundsItemResponse,
                newItem: LostFoundsItemResponse
            ): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: LostFoundsItemResponse,
                newItem: LostFoundsItemResponse
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}
