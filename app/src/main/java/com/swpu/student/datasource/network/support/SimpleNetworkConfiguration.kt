package com.swpu.student.datasource.network.support

import androidx.annotation.CallSuper
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.Call
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.CallAdapter
import retrofit2.Converter
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.Executor
import java.util.concurrent.TimeUnit

/**
 * Class description:
 *
 * @author zp
 * @version 1.0
 * @see SimpleNetworkConfiguration
 *
 * @since 2019/6/17
 */
open class SimpleNetworkConfiguration : NetworkConfigurationAdapter {
    companion object {
        private const val WINDOWS_HOST = "192.168.1.28"
        private const val MAC_HOST = "10.29.7.138"
        private const val SERVER_HOST = "47.106.84.158"
        private const val HOST = MAC_HOST
        private const val TIMEOUT = 30
    }

    @CallSuper
    override fun configure(builder: OkHttpClient.Builder): OkHttpClient.Builder {
        builder.retryOnConnectionFailure(true)
        //        builder.authenticator(new TokenAuthenticator());
        builder.connectTimeout(TIMEOUT.toLong(), TimeUnit.SECONDS)
        val headerInterceptor = HeaderInterceptor()
        builder.addInterceptor(headerInterceptor)
        //        builder.addInterceptor(new LoggingInterceptor());
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.level = (HttpLoggingInterceptor.Level.BODY)
        builder.addInterceptor(httpLoggingInterceptor)
        return builder
    }

    override fun callFactory(): Call.Factory? {
        return null
    }

    override fun baseUrl(): HttpUrl {
        return buildUrl(HttpUrl.Builder()).build()
    }

    @CallSuper
    protected open fun buildUrl(builder: HttpUrl.Builder): HttpUrl.Builder {
        builder.scheme("http")
        builder.port(80)
        builder.host(HOST)
        builder.addEncodedPathSegment("swpu")
        builder.addPathSegment("")
        return builder
    }

    override fun callbackExecutor(): Executor? {
        return null
    }

    override fun callAdapterFactories(factories: MutableList<CallAdapter.Factory>) {
        factories.add(CoroutineCallAdapterFactory())
    }

    override fun converterFactories(factories: MutableList<Converter.Factory>) {
        factories.add(GsonConverterFactory.create())
    }

    override fun validateEagerly(): Boolean {
        return true
    }
}
