package com.swpu.student.datasource.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import com.swpu.student.app.DATABASE_NAME
import com.swpu.student.datasource.database.dao.AccountDao
import com.swpu.student.model.AccountEntity
import java.lang.RuntimeException

/**
 * Class description:
 *
 * @author zp
 * @version 1.0
 * @see AppDatabase
 * @since 2019-06-28
 */
@Database(entities = [AccountEntity::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun accountDao(): AccountDao

    companion object {

        // For Singleton instantiation
        @Volatile
        private var instance: AppDatabase? = null

        fun getInstance(): AppDatabase {
            if (instance == null) {
                throw RuntimeException("you must invoke initDatabase before.")
            }
            return instance as AppDatabase
        }

        fun initDatabase(context: Context) {
            instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }
        }

        // Create and pre-populate the database. See this article for more details:
        // https://medium.com/google-developers/7-pro-tips-for-room-fbadea4bfbd1#4785
        private fun buildDatabase(context: Context): AppDatabase {
            return Room.databaseBuilder(context, AppDatabase::class.java, DATABASE_NAME).build()
        }
    }
}