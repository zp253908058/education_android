package com.swpu.student.datasource.cache

import android.util.Log
import java.util.*

/**
 * Class description:
 *
 * @author zp
 * @version 1.0
 * @see AppServiceRegistry
 *
 * @since 2019/6/23
 */
object AppServiceRegistry {
    private const val TAG = "AppServiceRegistry"
    const val STUDENT_SERVICE = "student_service"


    private val APP_SERVICE_NAMES = HashMap<Class<*>, String>()
    private val APP_SERVICE_FETCHERS = HashMap<String, ServiceFetcher<*>>()
    private var sServiceCacheSize: Int = 0

    init {
        registerService(STUDENT_SERVICE, StudentService::class.java, object : StaticServiceFetcher<StudentService>() {
            @Throws(ServiceNotFoundException::class)
            override fun createService(): StudentService {
                return StudentService()
            }
        })
    }

    internal interface ServiceFetcher<T> {
        val service: T
    }

    fun getAppService(name: String): Any? {
        val fetcher = APP_SERVICE_FETCHERS[name]
        return fetcher?.service
    }

    fun getAppServiceName(serviceClass: Class<*>): String? {
        return APP_SERVICE_NAMES[serviceClass]
    }

    private fun <T> registerService(serviceName: String, serviceClass: Class<T>, serviceFetcher: ServiceFetcher<T>) {
        APP_SERVICE_NAMES[serviceClass] = serviceName
        APP_SERVICE_FETCHERS[serviceName] = serviceFetcher
    }

    //    static abstract class CachedServiceFetcher<T> implements ServiceFetcher<T> {
    //        private final int mCacheIndex;
    //
    //        CachedServiceFetcher() {
    //            mCacheIndex = sServiceCacheSize++;
    //        }
    //
    //        @Override
    //        @SuppressWarnings("unchecked")
    //        public final T getService() {
    //            final Object[] cache = ctx.mServiceCache;
    //            final int[] gates = ctx.mServiceInitializationStateArray;
    //
    //            for (;;) {
    //                boolean doInitialize = false;
    //                synchronized (cache) {
    //                    // Return it if we already have a cached instance.
    //                    T service = (T) cache[mCacheIndex];
    //                    if (service != null || gates[mCacheIndex] == ContextImpl.STATE_NOT_FOUND) {
    //                        return service;
    //                    }
    //
    //                    // If we get here, there's no cached instance.
    //
    //                    // Grr... if gate is STATE_READY, then this means we initialized the service
    //                    // once but someone cleared it.
    //                    // We start over from STATE_UNINITIALIZED.
    //                    if (gates[mCacheIndex] == ContextImpl.STATE_READY) {
    //                        gates[mCacheIndex] = ContextImpl.STATE_UNINITIALIZED;
    //                    }
    //
    //                    // It's possible for multiple threads to get here at the same time, so
    //                    // use the "gate" to make sure only the first thread will call createService().
    //
    //                    // At this point, the gate must be either UNINITIALIZED or INITIALIZING.
    //                    if (gates[mCacheIndex] == ContextImpl.STATE_UNINITIALIZED) {
    //                        doInitialize = true;
    //                        gates[mCacheIndex] = ContextImpl.STATE_INITIALIZING;
    //                    }
    //                }
    //
    //                if (doInitialize) {
    //                    // Only the first thread gets here.
    //
    //                    T service = null;
    //                    @ServiceInitializationState int newState = ContextImpl.STATE_NOT_FOUND;
    //                    try {
    //                        // This thread is the first one to get here. Instantiate the service
    //                        // *without* the cache lock held.
    //                        service = createService(ctx);
    //                        newState = ContextImpl.STATE_READY;
    //
    //                    } catch (ServiceNotFoundException e) {
    //                        onServiceNotFound(e);
    //
    //                    } finally {
    //                        synchronized (cache) {
    //                            cache[mCacheIndex] = service;
    //                            gates[mCacheIndex] = newState;
    //                            cache.notifyAll();
    //                        }
    //                    }
    //                    return service;
    //                }
    //                // The other threads will wait for the first thread to call notifyAll(),
    //                // and go back to the top and retry.
    //                synchronized (cache) {
    //                    while (gates[mCacheIndex] < ContextImpl.STATE_READY) {
    //                        try {
    //                            cache.wait();
    //                        } catch (InterruptedException e) {
    //                            Log.w(TAG, "getService() interrupted");
    //                            Thread.currentThread().interrupt();
    //                            return null;
    //                        }
    //                    }
    //                }
    //            }
    //        }
    //
    //        public abstract T createService() throws ServiceNotFoundException;
    //    }

    /**
     * Override this class when the system service does not need a ContextImpl
     * and should be cached and retained process-wide.
     */
    internal abstract class StaticServiceFetcher<T> : ServiceFetcher<T> {
        private var mCachedInstance: T? = null

        override val service: T
            get() = synchronized(this@StaticServiceFetcher) {
                if (mCachedInstance == null) {
                    try {
                        mCachedInstance = createService()
                    } catch (e: ServiceNotFoundException) {
                        onServiceNotFound(e)
                    }

                }
                @Suppress("UNCHECKED_CAST")
                return mCachedInstance as T
            }

        @Throws(ServiceNotFoundException::class)
        abstract fun createService(): T
    }

    /**
     * Like StaticServiceFetcher, creates only one instance of the service per application, but when
     * creating the service for the first time, passes it the application context of the creating
     * application.
     *
     *
     * TODO: Delete this once its only user (ConnectivityManager) is known to work well in the
     * case where multiple application components each have their own ConnectivityManager object.
     */
    internal abstract class StaticApplicationContextServiceFetcher<T> : ServiceFetcher<T> {
        private var mCachedInstance: T? = null

        override val service: T
            get() = synchronized(this@StaticApplicationContextServiceFetcher) {
                if (mCachedInstance == null) {
                    try {
                        mCachedInstance = createService()
                    } catch (e: ServiceNotFoundException) {
                        onServiceNotFound(e)
                    }

                }
                @Suppress("UNCHECKED_CAST")
                return mCachedInstance as T
            }

        @Throws(ServiceNotFoundException::class)
        abstract fun createService(): T
    }

    fun onServiceNotFound(e: ServiceNotFoundException) {
        if (android.os.Process.myUid() < android.os.Process.FIRST_APPLICATION_UID) {
            Log.wtf(TAG, e.message, e)
        } else {
            Log.w(TAG, e.message)
        }
    }
}// Not instantiable.
