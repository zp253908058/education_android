package com.swpu.student.ui.task

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.swpu.student.base.BaseFragment
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
class TaskDetailFragment : BaseFragment() {

    private val taskViewModel: TaskViewModel by activityViewModels()
    private val taskItemViewModel = TaskItemViewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding = FragmentTaskDetailBinding.inflate(inflater, container, false)
        binding.model = taskItemViewModel

        return binding.root
    }

}