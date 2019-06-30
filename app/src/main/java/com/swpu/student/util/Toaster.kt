package com.swpu.student.util

import android.content.Context
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.core.util.Preconditions

/**
 * Class description:
 *
 * @author zp
 * @version 1.0
 * @see Toaster
 *
 * @since 2019-06-21
 */
object Toaster {

    /**
     * 显示文本的Toast
     */
    @Volatile
    private lateinit var sToast: Toast

    /**
     * 初始化工具类
     *
     * @param context application的Context，Toast的上下文与Activity无关
     */
    fun initialize(context: Context) {
        synchronized(Toaster::class.java) {
            sToast = createToast(context)
        }
    }

    private fun createToast(context: Context): Toast {
        return Toast.makeText(context, "", Toast.LENGTH_SHORT)
    }

    /**
     * 取消Toast显示
     */
    fun cancel() {
        Preconditions.checkNotNull(sToast, "Toaster must initialize before use.")
        sToast.cancel()
    }

    /**
     * 显示提示消息
     *
     * @param message  提示文本
     * @param duration 提示时长
     * @param margins  margins
     * @param gravity  显示位置
     */
    fun showToast(message: CharSequence, duration: Int, margins: Margins?, gravity: Gravity?) {
        Preconditions.checkNotNull(sToast, "Toaster must initialize before use.")
        sToast.setText(message)
        sToast.duration = duration
        if (margins != null) {
            sToast.setMargin(margins.horizontalMargin, margins.verticalMargin)
        }
        if (gravity != null) {
            sToast.setGravity(gravity.gravity, gravity.xOffset, gravity.yOffset)
        }
        sToast.show()
    }

    /**
     * 显示提示消息
     *
     * @param message  提示文本
     * @param duration 提示时长
     */
    @JvmOverloads
    fun showToast(message: CharSequence, duration: Int = Toast.LENGTH_SHORT) {
        Preconditions.checkNotNull(sToast, "Toaster must initialize before use.")
        sToast.setText(message)
        sToast.duration = duration
        sToast.show()
    }

    /**
     * 显示提示消息
     *
     * @param resId    提示文本资源id
     * @param duration 提示时长
     * @param margins  margins
     * @param gravity  显示位置
     */
    fun showToast(@StringRes resId: Int, duration: Int, margins: Margins, gravity: Gravity) {
        val context = sToast.view.context.applicationContext
        showToast(context.resources.getText(resId), duration, margins, gravity)
    }

    /**
     * 显示提示消息
     *
     * @param resId    提示文本资源id
     * @param duration 提示时长
     */
    @JvmOverloads
    fun showToast(@StringRes resId: Int, duration: Int = Toast.LENGTH_SHORT) {
        val context = sToast.view.context.applicationContext
        showToast(context.resources.getText(resId), duration)
    }

    class Margins {
        var horizontalMargin: Float = 0.toFloat()
        var verticalMargin: Float = 0.toFloat()
    }

    class Gravity {
        var gravity: Int = 0
        var xOffset: Int = 0
        var yOffset: Int = 0
    }
}
