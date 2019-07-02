package com.swpu.student.ui.task

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.swpu.student.R
import com.swpu.student.base.DataBindingViewHolder
import com.swpu.student.databinding.ItemTaskBinding
import com.swpu.student.model.TaskInfo
import com.swpu.student.vm.TaskItemViewModel

/**
 * Class description:
 *
 * @author zp
 * @version 1.0
 * @see TaskAdapter
 * @since 2019/7/2
 */
class TaskAdapter() : ListAdapter<TaskInfo, DataBindingViewHolder<ItemTaskBinding>>(TaskDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataBindingViewHolder<ItemTaskBinding> {
        return DataBindingViewHolder(
            DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_task, parent, false)
        )
    }

    override fun onBindViewHolder(holder: DataBindingViewHolder<ItemTaskBinding>, position: Int) {
        getItem(position).let { info ->
            with(holder) {
                binding.model = TaskItemViewModel(info)
                binding.executePendingBindings()
            }
        }
    }

}

private class TaskDiffCallback : DiffUtil.ItemCallback<TaskInfo>() {
    override fun areContentsTheSame(oldItem: TaskInfo, newItem: TaskInfo): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areItemsTheSame(oldItem: TaskInfo, newItem: TaskInfo): Boolean {
        return oldItem == newItem
    }
}