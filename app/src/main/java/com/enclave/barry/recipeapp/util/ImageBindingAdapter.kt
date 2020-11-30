package com.enclave.barry.recipeapp.util

import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

object ImageBindingAdapter {
    @JvmStatic
    @BindingAdapter(value = ["bindImageRes"])
    fun bindImage(img: ImageView, bindImageRes: Drawable?) {
        bindImageRes?.let {
            Glide.with(img.context).load(it).into(img)
        }
    }
}