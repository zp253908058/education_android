package com.swpu.student.ui.login

import android.content.Context
import android.content.Intent
import android.os.Bundle
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
        val viewModel = ViewModelProviders.of(this@LoginActivity).get(AccountViewModel::class.java)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        mBinding.model = viewModel
        viewModel.accountObservable.observe(this@LoginActivity, Observer {

            viewModel.number.postValue(it?.number)
            viewModel.password.postValue(it?.password)
            if (it == null) {
                mBinding.number.text = null
                mBinding.password.text = null
            } else {
                mBinding.number.setText(it.number)
                mBinding.password.setText(it.password)
            }
        })

        viewModel.studentInfoObservable.observe(this@LoginActivity, Observer {
            it?.let {
                getAppService(AppServiceRegistry.STUDENT_SERVICE).let { service ->
                    service as StudentService
                }.apply {
                    info = it
                    navigate()
                    viewModel.studentInfoObservable.postValue(null)
                }
            }
        })
        setSupportActionBar(mBinding.toolbar)
        mBinding.toolbar.navigationIcon = null
    }

    private fun navigate() {
        HomeActivity.go(this)
        finish()
    }
}