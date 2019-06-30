package com.swpu.student.datasource.network.support


import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

/**
 * Class description:
 *
 * @author zp
 * @version 1.0
 * @see HeaderInterceptor
 *
 * @since 2019/6/17
 */
class HeaderInterceptor : Interceptor {


    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val builder = originalRequest.newBuilder()
        builder.addHeader("Content-Type", "application/json;charset=UTF-8")

        //        AccountPreference entity = mUserManager.get();
        //        if (!entity.isEmpty()) {
        //            builder.header("Authorization", entity.getTokenType() + " " + entity.getAccessToken());
        //        }
        //        builder.header("sId", String.valueOf(mWarehouseId));
        val newRequest = builder.build()

        return chain.proceed(newRequest)
    }
}