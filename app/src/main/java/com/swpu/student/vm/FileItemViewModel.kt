package com.swpu.student.vm

import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import com.swpu.student.model.FileInfo

/**
 * Class description:
 *
 * @author zp
 * @version 1.0
 * @see FileItemViewModel
 *
 * @since 2019/7/5
 */
class FileItemViewModel : ViewModel() {
    var item: FileInfo? = null
        set(value) {
            field = value
            fileName.set(value?.fileName)
        }

    val fileName = ObservableField<String>()
}
