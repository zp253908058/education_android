package com.swpu.student.repository

import androidx.lifecycle.LiveData
import com.swpu.student.datasource.database.AppDatabase
import com.swpu.student.datasource.database.dao.AccountDao
import com.swpu.student.model.AccountEntity

/**
 * Class description:
 *
 * @author zp
 * @version 1.0
 * @see AccountRepository
 * @since 2019-06-28
 */
class AccountRepository {

    private val mAccountDao: AccountDao = AppDatabase.getInstance().accountDao()

    fun getLastAccount(): LiveData<AccountEntity> = mAccountDao.getLastAccount()

    fun addAccount(accountEntity: AccountEntity) = mAccountDao.addAccount(accountEntity)
}