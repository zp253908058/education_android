package com.swpu.student.datasource.network.support

import okhttp3.Call
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import retrofit2.CallAdapter
import retrofit2.Converter

import java.util.concurrent.Executor

/**
 * Class description:
 *
 * @author zp
 * @version 1.0
 * @see NetworkConfigurationAdapter
 * @since 2019-05-30
 */
interface NetworkConfigurationAdapter {

    fun configure(builder: OkHttpClient.Builder): OkHttpClient.Builder

    fun callFactory(): Call.Factory?

    fun baseUrl(): HttpUrl

    fun callbackExecutor(): Executor?

    fun callAdapterFactories(factories: MutableList<CallAdapter.Factory>)

    fun converterFactories(factories: MutableList<Converter.Factory>)

    fun validateEagerly(): Boolean
}
