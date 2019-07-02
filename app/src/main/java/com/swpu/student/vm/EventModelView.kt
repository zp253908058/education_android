package com.swpu.student.vm

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.swpu.student.asyn.executeRequest
import com.swpu.student.datasource.network.Network
import com.swpu.student.datasource.network.api.EventService
import com.swpu.student.model.EventInfo
import com.swpu.student.util.Toaster
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import retrofit2.await

/**
 * Class description:
 *
 * @author zp
 * @version 1.0
 * @see EventModelView
 *
 * @since 2019-07-02
 */
class EventModelView : ViewModel() {
    val eventObservable: MutableLiveData<List<EventInfo>> = MutableLiveData()

    private val viewModelJob = Job()

    private val viewModelScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    fun startLoadEvent(number: String) {
        val service: EventService = Network.getInstance().getGeneralService(EventService::class.java)
        executeRequest({ service.getEvents(number).await() }, viewModelScope, {
            eventObservable.postValue(it)
        }, {
            Toaster.showToast(it.message)
        })
    }
}
