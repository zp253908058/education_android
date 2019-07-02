package com.swpu.student.asyn

import android.util.Log
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.IO

/**
 * Class description:
 *
 * @author zp
 * @version 1.0
 * @see asynchronous
 * @since 2019-06-29
 */
fun <T> executeRequest(
    request: suspend () -> T,
    scope: CoroutineScope,
    onSuccess: (T) -> Unit = {},
    onFail: (Throwable) -> Unit = {}
): Job {
    return scope.launch {
        try {
            val res: T = withContext(IO) { request() }  // IO线程中执行网络请求，成功后返回这里继续执行
            res?.let {
                onSuccess(it)
            }
        } catch (e: CancellationException) {
            Log.e("executeRequest", "job cancelled")
        } catch (e: Exception) {
            Log.e("executeRequest", "request caused exception")
            onFail(e)
        }
    }
}

fun executeRunnable(runnable: suspend () -> Unit, scope: CoroutineScope): Job {
    return scope.launch {
        withContext(IO) {
            runnable()
            Log.d("Asynchronous", Thread.currentThread().name)
        }
    }
}