package com.swpu.student.datasource.network.support

import androidx.core.util.Pair
import retrofit2.Retrofit

/**
 * Class description:
 *
 * @author zp
 * @version 1.0
 * @see RetrofitStore
 *
 * @since 2019/6/17
 */
class RetrofitStore {
    private val mMap = hashMapOf<String, Pair<Retrofit, HashMap<Class<*>, Any>>>()

    fun put(key: String, retrofit: Retrofit) {
        val newPair = Pair.create(retrofit, hashMapOf<Class<*>, Any>())
        val old = mMap.put(key, newPair)
        old?.second?.clear()
    }

    operator fun get(key: String): Retrofit? {
        return mMap[key]?.first
    }

    @Suppress("UNCHECKED_CAST")
    fun <I> getServiceWithKey(key: String, clz: Class<I>): I {
        val pair = mMap[key]
        val retrofit = pair!!.first
        val holder = pair.second
        var service = holder!![clz]
        if (service == null) {
            service = retrofit!!.create(clz).let { it as Any }
            holder[clz] = service
        }
        return service as I
    }

    fun keys(): Set<String> {
        return HashSet(mMap.keys)
    }

    fun clear() {
        for (pair in mMap.values) {
            val holder = pair.second
            holder?.clear()
        }
        mMap.clear()
    }
}
