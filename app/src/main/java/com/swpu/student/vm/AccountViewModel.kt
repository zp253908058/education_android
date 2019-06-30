package com.swpu.student.vm

import android.app.Application
import android.util.Log
import android.view.View
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.swpu.student.R
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
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

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

    val numberError: MutableLiveData<String> = MutableLiveData()
    val passwordError: MutableLiveData<String> = MutableLiveData()

    val studentInfoObservable: MutableLiveData<StudentInfo> = MutableLiveData()

    private val emptyNumberError: String = getApplication<Application>().getString(R.string.error_empty_number)
    private val emptyPasswordError: String = getApplication<Application>().getString(R.string.error_empty_password)
    private val shortPasswordError: String = getApplication<Application>().getString(R.string.error_short_password)

    val accountObservable: LiveData<AccountEntity>
    private val mAccountRepository: AccountRepository = AccountRepository().apply {
        Log.d("AccountViewModel", "new。。。")
        accountObservable = getLastAccount().apply {
            Log.d("AccountViewModel", "开始执行数据库查询")
        }
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
            numberError.postValue(emptyNumberError)
            return
        }

        if (pwd == null || pwd.isEmpty()) {
            passwordError.postValue(emptyPasswordError)
            return
        }

        if (pwd.length < 6) {
            passwordError.postValue(shortPasswordError)
            return
        }

        v.isEnabled = false
        val service = Network.getInstance().getGeneralService(LoginService::class.java)
//        executeRequest({ service.login(number, pwd).await() }, viewModelScope, onSuccess = {
//            studentInfoObservable.postValue(it)
//        executeRunnable({ mAccountRepository.addAccount(AccountEntity(number, pwd)) }, viewModelScope)
//        }, onFail = {
//            it.message?.let {
//                    it1 -> Toaster.showToast(it1)
//                Log.d("AccountViewModel", it1)
//            }
//        })
        service.login(number, pwd).also {
            it.enqueue(object : Callback<StudentInfo> {
                override fun onResponse(call: Call<StudentInfo>, response: Response<StudentInfo>) {
                    val student = response.body()
                    studentInfoObservable.postValue(student)
                    v.isEnabled = true
                    executeRunnable({ mAccountRepository.addAccount(AccountEntity(number, pwd)) }, viewModelScope)
                }

                override fun onFailure(call: Call<StudentInfo>, t: Throwable) {
                    t.message?.let { message -> Toaster.showToast(message) }
                    v.isEnabled = true
                }

            })
        }
    }

}