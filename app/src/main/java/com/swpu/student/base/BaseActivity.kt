package com.swpu.student.base

import androidx.appcompat.app.AppCompatActivity
import com.swpu.student.datasource.cache.AppServiceRegistry

/**
 * Class description:
 *
 * @author zp
 * @version 1.0
 * @see BaseActivity
 * @since 2019-06-30
 */
abstract class BaseActivity : AppCompatActivity() {

    fun getAppService(name: String): Any? {
        return AppServiceRegistry.getAppService(name)
    }

    fun getAppServiceName(serviceClass: Class<*>): String? {
        return AppServiceRegistry.getAppServiceName(serviceClass)
    }

    @Suppress("UNCHECKED_CAST")
    fun <T> getAppService(serviceClass: Class<T>): T? {
        return getAppServiceName(serviceClass)?.let {
            getAppService(it) as T
        }
    }
}