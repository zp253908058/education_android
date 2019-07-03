package com.swpu.student.vm

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.swpu.student.model.TaskInfo

/**
 * Class description:
 *
 * @author zp
 * @version 1.0
 * @see TaskViewModel
 *
 * @since 2019/7/2
 */
class TaskViewModel : ViewModel() {

    val taskObservable: MutableLiveData<TaskInfo> = MutableLiveData()


}
