package com.swpu.student.ui.home

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.SubMenu
import androidx.appcompat.view.menu.SubMenuBuilder
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
import com.swpu.student.util.Toaster
import com.swpu.student.vm.EventModelView
import com.swpu.student.vm.StudentViewModel

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
    private lateinit var eventModelView: EventModelView

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

        binding.navigationView.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.nav_home -> goHome()
            }
            false
        }

        eventModelView = ViewModelProviders.of(this).get(EventModelView::class.java)
        eventModelView.eventObservable.observe(this, Observer {
            val navMenu = binding.navigationView.menu
            val subMenu  = navMenu.addSubMenu(R.string.text_event)
            it?.forEach { item ->
                subMenu.add(
                    1,
                    item.id.toInt(),
                    item.id.toInt(),
                    item.eventName
                ).setOnMenuItemClickListener {
                    onEventClick(item)
                    true
                }.isChecked = true
            }
            events = it
        })

        val studentViewModel: StudentViewModel = ViewModelProviders.of(this).get(StudentViewModel::class.java);
        val studentService = getAppService(StudentService::class.java)
        studentService?.apply {
            studentViewModel.studentObservable.postValue(info);
        }

        studentViewModel.studentObservable.observe(this, Observer {
            it?.apply {
                eventModelView.startLoadEvent(it.studentNumber)
            }
        })
    }

    fun goHome() {

    }

    //Event Clicked
    fun onEventClick(item: EventInfo) {
        Toaster.showToast("点击了${item.eventName}")
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
