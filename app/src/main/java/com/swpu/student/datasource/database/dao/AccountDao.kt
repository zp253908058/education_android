package com.swpu.student.datasource.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import com.swpu.student.model.AccountEntity

/**
 * Class description:
 *
 * @author zp
 * @version 1.0
 * @see AccountDao
 * @since 2019-06-28
 */
@Dao
interface AccountDao {

    @Query("SELECT * FROM account order by last_login_date limit 1")
    fun getLastAccount(): LiveData<AccountEntity>
}