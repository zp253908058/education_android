package com.swpu.student.ui.login

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.swpu.student.R
import com.swpu.student.base.BaseActivity
import com.swpu.student.databinding.ActivityLoginBinding
import com.swpu.student.datasource.cache.AppServiceRegistry
import com.swpu.student.datasource.cache.StudentService
import com.swpu.student.ui.home.HomeActivity
import com.swpu.student.vm.AccountViewModel

/**
 * Class description:
 *
 * @author zp
 * @version 1.0
 * @see LoginActivity
 * @since 2019-06-30
 */
class LoginActivity : BaseActivity() {

    companion object {
        private const val TAG = "LoginActivity"
        fun go(context: Context) {
            val intent = Intent()
            intent.setClass(context, LoginActivity::class.java)
            context.startActivity(intent)
        }
    }

    private lateinit var mBinding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val window = window
        window.statusBarColor = getColor(R.color.colorPrimaryDark)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        mBinding = DataBindingUtil.setContentView<ActivityLoginBinding>(this, R.layout.activity_login).apply {
            model = ViewModelProviders.of(this@LoginActivity).get(AccountViewModel::class.java).apply {
                accountObservable.observe(this@LoginActivity, Observer {
                    number.postValue(it?.number)
                    password.postValue(it?.password)
                })

                studentInfoObservable.observe(this@LoginActivity, Observer {
                    it?.let {
                        getAppService(AppServiceRegistry.STUDENT_SERVICE).let { service ->
                            service as StudentService
                        }.apply {
                            info = it
                            navigate()
                            studentInfoObservable.postValue(null)
                        }
                    }
                })
            }
        }
        setSupportActionBar(mBinding.toolbar)
        mBinding.toolbar.navigationIcon = null
    }

    private fun navigate() {
        HomeActivity.go(this)
        finish()
    }
}