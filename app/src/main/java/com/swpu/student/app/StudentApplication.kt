package com.swpu.student.app

import android.app.Application
import com.swpu.student.datasource.database.AppDatabase
import com.swpu.student.util.Toaster

/**
 * Class description:
 *
 * @author zp
 * @version 1.0
 * @see StudentApplication
 * @since 2019-06-28
 */
class StudentApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        AppDatabase.initDatabase(this)
        Toaster.initialize(this)
    }
}