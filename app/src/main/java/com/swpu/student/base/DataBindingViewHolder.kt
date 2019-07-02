package com.swpu.student.base

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

/**
 * Class description:
 *
 * @author zp
 * @version 1.0
 * @see DataBindingViewHolder
 *
 * @since 2019/7/2
 */
class DataBindingViewHolder<D : ViewDataBinding>(val binding: D) : RecyclerView.ViewHolder(binding.root)

