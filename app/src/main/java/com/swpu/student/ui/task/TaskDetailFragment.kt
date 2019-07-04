package com.swpu.student.ui.task

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.swpu.student.R
import com.swpu.student.base.NavigationFragment
import com.swpu.student.databinding.FragmentTaskDetailBinding
import com.swpu.student.vm.TaskItemViewModel
import com.swpu.student.vm.TaskViewModel

/**
 * Class description:
 *
 * @author zp
 * @version 1.0
 * @see TaskDetailFragment
 * @since 2019-07-03
 */
class TaskDetailFragment : NavigationFragment(), View.OnClickListener {

    private val taskViewModel: TaskViewModel by activityViewModels()
    private val taskItemViewModel = TaskItemViewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding = FragmentTaskDetailBinding.inflate(inflater, container, false)
        binding.model = taskItemViewModel
        binding.clickListener = this
        taskViewModel.taskObservable.observe(this, Observer {
            taskItemViewModel.item = it
        })
        return binding.root
    }

    override fun onClick(v: View?) {
        navigate(R.id.action_task_detail_fragment_to_file_fragment)
    }
}