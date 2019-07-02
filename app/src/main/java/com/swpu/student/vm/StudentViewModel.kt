package com.swpu.student.vm

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.swpu.student.model.StudentInfo

/**
 * Class description:
 *
 * @author zp
 * @version 1.0
 * @see StudentViewModel
 *
 * @since 2019-07-02
 */
class StudentViewModel : ViewModel() {
    val studentObservable: MutableLiveData<StudentInfo> = MutableLiveData()
}
