package com.swpu.student.datasource.network.api

import com.swpu.student.model.EventInfo
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Class description:
 *
 * @author zp
 * @version 1.0
 * @see EventService
 * @since 2019-07-02
 */
interface EventService {

    @GET("user/event")
    fun getEvents(@Query("number") number: String): Call<List<EventInfo>>
}