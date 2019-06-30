package com.swpu.student.util

import android.text.Spanned
import android.widget.TextView
import androidx.databinding.BindingAdapter

/**
 * Class description:
 *
 * @author zp
 * @version 1.0
 * @see DataBindingAdapter
 * @since 2019-06-29
 */

@BindingAdapter("error")
fun setError(view: TextView, error: CharSequence?) {
    val oldError = view.error
    if (error.isNullOrEmpty() && !oldError.isNullOrEmpty()) {
        view.error = null
        return
    }
    if (oldError.isNullOrEmpty() && !error.isNullOrEmpty()) {
        view.error = error
        return
    }
    if (error.isNullOrEmpty() && oldError.isNullOrEmpty()) {
        return
    }
    if (oldError === error) {
        return
    }
    if (error is Spanned) {
        if (error == oldError) {
            return  // No change in the spans, so don't set anything.
        }
    } else if (!haveContentsChanged(error, oldError)) {
        return  // No content changes, so don't set anything.
    }
    view.error = error
}

