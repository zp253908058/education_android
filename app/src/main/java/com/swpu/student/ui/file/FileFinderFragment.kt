package com.swpu.student.ui.file

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.swpu.student.base.BaseFragment
import com.swpu.student.databinding.FragmentFileBinding
import com.swpu.student.vm.FileViewModel

/**
 * Class description:
 *
 * @author zp
 * @version 1.0
 * @see FileFinderFragment
 *
 * @since 2019/7/4
 */
class FileFinderFragment : BaseFragment() {

    val fileViewModel: FileViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding = FragmentFileBinding.inflate(inflater, container, false)
        val adapter = FileAdapter(fileViewModel)
        binding.recyclerView.adapter = adapter
        subscribeUi(adapter)
        setHasOptionsMenu(true)
        return binding.root
    }

    private fun subscribeUi(adapter: FileAdapter) {

    }
}
