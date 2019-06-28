package com.swpu.student.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.swpu.student.model.AccountEntity
import com.swpu.student.repository.AccountRepository

/**
 * Class description:
 *
 * @author zp
 * @version 1.0
 * @see AccountViewModel
 * @since 2019-06-27
 */
class AccountViewModel : ViewModel() {

    val account: LiveData<AccountEntity>
    private val accountRepository: AccountRepository = AccountRepository()

    init {
        account = accountRepository.getLastAccount()
    }
}