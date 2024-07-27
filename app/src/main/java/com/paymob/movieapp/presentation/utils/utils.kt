package com.paymob.movieapp.presentation.utils

import android.content.Context
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.paymob.movieapp.R
import com.paymob.movieapp.data.network.error.ErrorEntity
import com.paymob.movieapp.data.network.error.handleError
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


fun lifecycleScope(
    fragment: Fragment,
    block: suspend CoroutineScope.() -> Unit
){
    fragment.viewLifecycleOwner.lifecycleScope.launch {
        fragment.viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
            block()
        }
    }
}

fun handleResponseError(context: Context, errorEntity: ErrorEntity?): String? {
    return handleError(context, errorEntity)
}

fun handleError(context: Context, errorEntity: ErrorEntity?): String? {
    return context.handleError(errorEntity)
}

fun bindImage(imageView: ImageView, imgUrl: String?, placeHolderIsAppIcon: Boolean = true) {
    val placeHolderId = if (placeHolderIsAppIcon) R.mipmap.ic_launcher_foreground else R.color.light_gray2
    if (imgUrl.isNullOrEmpty()) {
        imageView.setImageResource(placeHolderId)
    } else {
        try {
            imageView.clipToOutline = true
            Glide.with(imageView.context)
                .load(imgUrl)
                .apply(
                    RequestOptions()
                        .placeholder(R.color.light_gray2)
                )
                .error(placeHolderId)
                .placeholder(placeHolderId)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imageView)
        } catch (e: Exception) {
        }
    }
}

fun bindResourceImage(imageView: ImageView, resourceId: Int) {
    Glide.with(imageView.context)
        .load(resourceId)
        .into(imageView)
}