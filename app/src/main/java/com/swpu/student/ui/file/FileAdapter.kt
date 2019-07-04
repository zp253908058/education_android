package com.swpu.student.ui.file

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.swpu.student.R
import com.swpu.student.base.DataBindingViewHolder
import com.swpu.student.databinding.ItemFileBinding
import com.swpu.student.model.FileInfo
import com.swpu.student.vm.FileItemViewModel
import com.swpu.student.vm.FileViewModel

/**
 * Class description:
 *
 * @author zp
 * @version 1.0
 * @see FileAdapter
 * @since 2019/7/4
 */
class FileAdapter(private val fileViewModel: FileViewModel) :
    ListAdapter<FileInfo, DataBindingViewHolder<ItemFileBinding>>(FileDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataBindingViewHolder<ItemFileBinding> {
        return DataBindingViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_file,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: DataBindingViewHolder<ItemFileBinding>, position: Int) {
        with(holder) {
            val item = getItem(position)
            val model = FileItemViewModel()
            model.item = item
            binding.model = model
//            binding.clickListener = createOnClickListener(item)
            binding.executePendingBindings()
        }
    }

    private fun createOnClickListener(item: FileInfo): View.OnClickListener {
        return View.OnClickListener {
            fileViewModel.fileObservable.postValue(item)
            it.findNavController().navigateUp()
        }
    }

}

private class FileDiffCallback : DiffUtil.ItemCallback<FileInfo>() {
    override fun areContentsTheSame(oldItem: FileInfo, newItem: FileInfo): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areItemsTheSame(oldItem: FileInfo, newItem: FileInfo): Boolean {
        return oldItem == newItem
    }
}