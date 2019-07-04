package com.swpu.student.vm

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.swpu.student.model.FileInfo

/**
 * Class description:
 *
 * @author zp
 * @version 1.0
 * @see FileViewModel
 *
 * @since 2019/7/4
 */
class FileViewModel : ViewModel() {

    val fileObservable: MutableLiveData<FileInfo> = MutableLiveData()

}
