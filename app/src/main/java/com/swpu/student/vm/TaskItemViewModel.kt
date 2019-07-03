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
class TaskItemViewModel : ViewModel() {
    var item: TaskInfo? = null
        set(value) {
            field = value
            taskName.set(value?.taskName)
            endDate.set(value?.taskEndDate)
            taskType.set(value?.taskType)
        }

    val taskName = ObservableField<String>()
    val endDate = ObservableField<String>()
    val taskType = ObservableField<String>()
}