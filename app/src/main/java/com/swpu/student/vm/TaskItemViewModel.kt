package com.swpu.student.vm

import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import com.swpu.student.model.TaskInfo

/**
 * Class description:
 *
 * @author zp
 * @version 1.0
 * @see TaskItemViewModel
 * @since 2019/7/3
 */
class TaskItemViewModel(item: TaskInfo? = null) : ViewModel() {
    val taskName = ObservableField(item?.taskName)
    val endDate = ObservableField(item?.taskEndDate)
    val taskType = ObservableField(item?.taskType)
}