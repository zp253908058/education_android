package com.swpu.student.datasource.network.support

import okhttp3.OkHttpClient
import retrofit2.Retrofit

/**
 * Class description:
 *
 * @author zp
 * @version 1.0
 * @see RetrofitBuilder
 *
 * @since 2019/6/17
 */
object RetrofitBuilder {

    private const val TAG = "RetrofitBuilder";

    fun buildRetrofit(adapter: NetworkConfigurationAdapter): Retrofit {
        val builder = Retrofit.Builder()
        val okBuilder = OkHttpClient.Builder()
        builder.client(adapter.configure(okBuilder).build())
        adapter.callFactory()?.let { builder.callFactory(it) }
        adapter.callbackExecutor()?.let { builder.callbackExecutor(it) }
        builder.baseUrl(adapter.baseUrl())
        adapter.callAdapterFactories(builder.callAdapterFactories())
        adapter.converterFactories(builder.converterFactories())
        builder.validateEagerly(adapter.validateEagerly())
        return builder.build()
    }
}
