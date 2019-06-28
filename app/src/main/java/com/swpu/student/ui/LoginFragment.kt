package com.swpu.student.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.swpu.student.R
import com.swpu.student.base.AbstractFragment
import com.swpu.student.databinding.FragmentLoginBinding
import com.swpu.student.vm.AccountViewModel

/**
 * Class description:
 *
 * @author zp
 * @version 1.0
 * @see LoginFragment
 * @since 2019-06-27
 */
class LoginFragment : AbstractFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding = DataBindingUtil.inflate<FragmentLoginBinding>(inflater, R.layout.fragment_login, container, false)
            .apply {
                lifecycleOwner = this@LoginFragment
                model = ViewModelProviders.of(this@LoginFragment).get(AccountViewModel::class.java)
            }
        return binding.root
    }
}