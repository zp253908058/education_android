package com.swpu.student.vm

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.swpu.student.asyn.executeRequest
import com.swpu.student.datasource.network.Network
import com.swpu.student.datasource.network.api.EventService
import com.swpu.student.model.EventInfo
import com.swpu.student.model.TaskInfo
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
 * @see EventViewModel
 *
 * @since 2019-07-02
 */
class EventViewModel : ViewModel() {
    val eventObservable: MutableLiveData<List<EventInfo>> = MutableLiveData()
    val currentEventObservable: MutableLiveData<EventInfo> = MutableLiveData()
    val taskObservable: MutableLiveData<List<TaskInfo>> = MutableLiveData()

    private val service: EventService = Network.getInstance().getGeneralService(EventService::class.java)

    private val viewModelJob = Job()

    private val viewModelScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    fun startLoadEvent(number: String) {
        executeRequest({ service.getEvents(number).await() }, viewModelScope, {
            eventObservable.postValue(it)
        }, {
            Toaster.showToast(it.message)
        })
    }

    //加载任务
    fun loadTasks(eventId: Long) {
        executeRequest({ service.getTasks(eventId).await() }, viewModelScope, {
            taskObservable.postValue(it)
        }, {
            Toaster.showToast(it.message)
        })
    }
}
