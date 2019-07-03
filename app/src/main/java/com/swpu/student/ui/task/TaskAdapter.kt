package com.swpu.student.ui.task

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.swpu.student.R
import com.swpu.student.base.DataBindingViewHolder
import com.swpu.student.databinding.ItemTaskBinding
import com.swpu.student.model.TaskInfo
import com.swpu.student.vm.TaskItemViewModel
import com.swpu.student.vm.TaskViewModel


/**
 * Class description:
 *
 * @author zp
 * @version 1.0
 * @see TaskAdapter
 * @since 2019/7/2
 */
class TaskAdapter(private val taskViewModel: TaskViewModel) :
    ListAdapter<TaskInfo, DataBindingViewHolder<ItemTaskBinding>>(TaskDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataBindingViewHolder<ItemTaskBinding> {
        return DataBindingViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_task,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: DataBindingViewHolder<ItemTaskBinding>, position: Int) {
        with(holder) {
            val item = getItem(position)
            val model = TaskItemViewModel()
            model.item = item
            binding.model = model
            binding.clickListener = createOnClickListener(item)
            binding.executePendingBindings()
        }
    }

    private fun createOnClickListener(item: TaskInfo): View.OnClickListener {
        return View.OnClickListener {
            taskViewModel.taskObservable.postValue(item)
            val direction = TaskListFragmentDirections.actionTaskFragmentToTaskDetailFragment()
            it.findNavController().navigate(direction)
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