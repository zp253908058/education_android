package com.swpu.student.vm

import android.app.Application
import android.view.View
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.swpu.student.R
import com.swpu.student.asyn.executeRequest
import com.swpu.student.asyn.executeRunnable
import com.swpu.student.datasource.network.Network
import com.swpu.student.datasource.network.api.LoginService
import com.swpu.student.model.AccountEntity
import com.swpu.student.model.StudentInfo
import com.swpu.student.repository.AccountRepository
import com.swpu.student.util.Toaster
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.Job
import retrofit2.await

/**
 * Class description:
 *
 * @author zp
 * @version 1.0
 * @see AccountViewModel
 * @since 2019-06-27
 */
class AccountViewModel(application: Application) : AndroidViewModel(application) {

    val number: MutableLiveData<String> = MutableLiveData()
    val password: MutableLiveData<String> = MutableLiveData()

    val studentInfoObservable: MutableLiveData<StudentInfo> = MutableLiveData()

    val accountObservable: LiveData<AccountEntity>
    private val mAccountRepository: AccountRepository = AccountRepository().apply {
        accountObservable = getLastAccount()
    }

    private val viewModelJob = Job()

    private val viewModelScope = CoroutineScope(Main + viewModelJob)

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    fun login(v: View) {
        val number = number.value
        val pwd = password.value
        if (number == null || number.isEmpty()) {
            Toaster.showToast(R.string.error_empty_number)
            return
        }

        if (pwd == null || pwd.isEmpty()) {
            Toaster.showToast(R.string.error_empty_password)
            return
        }

        if (pwd.length < 6) {
            Toaster.showToast(R.string.error_short_password)
            return
        }

        v.isEnabled = false
        val service = Network.getInstance().getGeneralService(LoginService::class.java)
        executeRequest({ service.login(number, pwd).await() }, viewModelScope, onSuccess = {
            studentInfoObservable.postValue(it)
            v.isEnabled = true
            executeRunnable({ mAccountRepository.addAccount(AccountEntity(number, pwd)) }, viewModelScope)
        }, onFail = {
            Toaster.showToast(it.message)
            v.isEnabled = true
        })
    }

}