package com.swpu.student.ui.task

import android.os.Bundle
import android.view.*
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.swpu.student.R
import com.swpu.student.base.NavigationFragment
import com.swpu.student.databinding.FragmentTaskBinding
import com.swpu.student.vm.EventViewModel
import com.swpu.student.vm.TaskViewModel

/**
 * Class description:
 *
 * @author zp
 * @version 1.0
 * @see TaskListFragment
 * @since 2019/7/2
 */
class TaskListFragment : NavigationFragment() {

    private val eventViewModel: EventViewModel by activityViewModels()
    private val taskViewModel: TaskViewModel by activityViewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding = FragmentTaskBinding.inflate(inflater, container, false)
        val adapter = TaskAdapter(taskViewModel)
        binding.recyclerView.adapter = adapter
        subscribeUi(adapter)
        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_group, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.my_group -> {
                goGroup()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun goGroup() {

    }

    private fun subscribeUi(adapter: TaskAdapter) {
        eventViewModel.taskObservable.observe(viewLifecycleOwner, Observer {
            if (it != null) adapter.submitList(it)
        })
    }
}