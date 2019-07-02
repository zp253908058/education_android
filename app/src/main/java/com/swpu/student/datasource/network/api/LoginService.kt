package com.swpu.student.datasource.network.api

import com.swpu.student.model.StudentInfo
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

/**
 * Class description:
 *
 * @author zp
 * @version 1.0
 * @see LoginService
 * @since 2019-06-30
 */
interface LoginService {

    @POST("sign/in")
    @FormUrlEncoded
    fun login(@Field("number") number: String, @Field("password") password: String): Call<StudentInfo>
}