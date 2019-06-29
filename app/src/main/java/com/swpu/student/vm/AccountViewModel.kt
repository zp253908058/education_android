package com.swpu.student.vm

import android.util.Log
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
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

    val accountLiveData: LiveData<AccountEntity>
    private val accountRepository: AccountRepository = AccountRepository().apply {
        val lastAccount = getLastAccount()
        accountLiveData = lastAccount ?: MutableLiveData()
    }



    fun login(v: View) {
        Log.d("", accountLiveData.value?.toString())
    }
}