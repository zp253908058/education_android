package com.swpu.student.datasource.network

import com.swpu.student.datasource.network.support.GeneralNetworkConfiguration
import com.swpu.student.datasource.network.support.RetrofitBuilder
import com.swpu.student.datasource.network.support.RetrofitStore

/**
 * Class description:
 *
 * @author zp
 * @version 1.0
 * @see Network
 * @since 2019-06-29
 */
class Network private constructor() {

    private val retrofitStore: RetrofitStore = RetrofitStore()

    init {
        retrofitStore.put(KEY_GENERAL, RetrofitBuilder.buildRetrofit(GeneralNetworkConfiguration()))
    }

    fun <T> getGeneralService(clz: Class<T>): T {
        synchronized(this) {
            return retrofitStore.getServiceWithKey(KEY_GENERAL, clz)
        }
    }


    companion object {

        private const val KEY_GENERAL = "general"

        @Volatile
        private var instance: Network? = null

        fun getInstance(): Network {
            return instance ?: synchronized(this) {
                instance ?: buildNetwork().also { instance = it }
            }
        }

        private fun buildNetwork(): Network {
            return Network()
        }
    }
}