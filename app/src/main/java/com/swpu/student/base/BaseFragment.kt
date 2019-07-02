package com.swpu.student.base

import android.os.Bundle
import android.view.View
import androidx.annotation.CallSuper
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.navigation.*

/**
 * Class description:
 *
 * @author zp
 * @version 1.0
 * @see BaseFragment
 * @since 2019-06-27
 */
abstract class BaseFragment : Fragment()


//Add Navigation
abstract class NavigationFragment : BaseFragment() {
    private var mNavController: NavController? = null

    @CallSuper
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mNavController = Navigation.findNavController(view)
    }

    protected fun onBackPressed() {
        mNavController?.navigateUp()
    }

    protected fun getNavController(): NavController? {
        return mNavController
    }

    protected fun navigate(@IdRes resId: Int) {
        mNavController?.navigate(resId)
    }

    protected fun navigate(@IdRes resId: Int, args: Bundle?) {
        mNavController?.navigate(resId, args)
    }

    protected fun navigate(@IdRes resId: Int, args: Bundle?, navOptions: NavOptions?) {
        mNavController?.navigate(resId, args, navOptions)
    }

    protected fun navigate(
        @IdRes resId: Int, args: Bundle?,
        navOptions: NavOptions?,
        navigatorExtras: Navigator.Extras?
    ) {
        mNavController?.navigate(resId, args, navOptions, navigatorExtras)
    }

    protected fun navigate(directions: NavDirections) {
        mNavController?.navigate(directions)
    }

    protected fun navigate(directions: NavDirections, navOptions: NavOptions?) {
        mNavController?.navigate(directions, navOptions)
    }

    protected fun navigate(directions: NavDirections, navigatorExtras: Navigator.Extras) {
        mNavController?.navigate(directions, navigatorExtras)
    }
}