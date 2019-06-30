package com.swpu.student.ui.splash

import android.os.Bundle
import android.os.Handler
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.swpu.student.ui.login.LoginActivity

/**
 * Class description:
 *
 * @author zp
 * @version 1.0
 * @see SplashActivity
 * @since 2019-06-30
 */
class SplashActivity : AppCompatActivity(), Runnable {
    private val TAG = SplashActivity::class.java.simpleName

    private val mHandler = Handler()

    override fun onCreate(savedInstanceState: Bundle?) {
        val uiFlags = (View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_FULLSCREEN //hide statusBar
                or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION) //hide navigationBar
        window.decorView.systemUiVisibility = uiFlags
        super.onCreate(savedInstanceState)
    }

    override fun onBackPressed() {

    }

    override fun run() {
        LoginActivity.go(this)
    }

    override fun onPause() {
        super.onPause()
        mHandler.removeCallbacks(this)
    }

    override fun onResume() {
        super.onResume()
        mHandler.postDelayed(this, 500)
    }
}