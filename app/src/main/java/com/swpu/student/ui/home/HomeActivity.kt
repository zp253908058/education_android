package com.swpu.student.ui.home

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.core.view.GravityCompat
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.swpu.student.R
import com.swpu.student.base.BaseActivity
import com.swpu.student.databinding.ActivityMainBinding
import com.swpu.student.datasource.cache.StudentService
import com.swpu.student.model.EventInfo
import com.swpu.student.vm.EventViewModel
import com.swpu.student.vm.StudentViewModel
import com.swpu.student.vm.TaskViewModel

class HomeActivity : BaseActivity() {

    companion object {
        fun go(context: Context) {
            val intent = Intent()
            intent.setClass(context, HomeActivity::class.java)
            context.startActivity(intent)
        }
    }

    private lateinit var drawerLayout: DrawerLayout
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var navController: NavController
    private lateinit var eventViewModel: EventViewModel
    private lateinit var taskViewModel: TaskViewModel
    private lateinit var nameView: TextView

    private lateinit var binding: ActivityMainBinding

    private lateinit var events: List<EventInfo>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        drawerLayout = binding.drawerLayout

        navController = Navigation.findNavController(this, R.id.main_nav_fragment)
        appBarConfiguration = AppBarConfiguration(navController.graph, drawerLayout)

        // Set up ActionBar
        setSupportActionBar(binding.toolbar)
        setupActionBarWithNavController(navController, appBarConfiguration)

        binding.navigationView.setupWithNavController(navController)

        nameView = binding.navigationView.getHeaderView(0).findViewById(R.id.name)

        eventViewModel = ViewModelProviders.of(this).get(EventViewModel::class.java)
        taskViewModel = ViewModelProviders.of(this).get(TaskViewModel::class.java)
        eventViewModel.eventObservable.observe(this, Observer {
            val navMenu = binding.navigationView.menu
            val subMenu = navMenu.addSubMenu(R.string.text_event)
            subMenu.setGroupEnabled(1, true)
            it?.forEach { item ->
                subMenu.add(
                    1,
                    item.id.toInt(),
                    item.id.toInt(),
                    item.eventName
                ).setOnMenuItemClickListener {
                    onEventClick(item)
                    true
                }
            }
            it?.apply { onEventClick(it[0]) }
            events = it
        })

        val studentViewModel: StudentViewModel = ViewModelProviders.of(this).get(StudentViewModel::class.java);
        val studentService = getAppService(StudentService::class.java)
        studentService?.apply {
            studentViewModel.studentObservable.postValue(info)
        }

        studentViewModel.studentObservable.observe(this, Observer {
            it?.apply {
                eventViewModel.startLoadEvent(it.studentNumber)
                nameView.text = it.studentName
            }
        })
    }

    private fun goTasks() {

    }

    //Event Clicked
    private fun onEventClick(item: EventInfo) {
        drawerLayout.closeDrawers()
        eventViewModel.loadTasks(item.id)
        eventViewModel.currentEventObservable.postValue(item)
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }
}
