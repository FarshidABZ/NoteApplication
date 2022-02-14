package com.task.noteapp.util

import android.graphics.drawable.Drawable
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter
import com.airbnb.lottie.LottieAnimationView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target

@BindingAdapter("app:isVisible")
fun View.setVisibility(isVisible: Boolean) {
    this.isVisible = isVisible
}

@BindingAdapter("app:setLastEditedDate")
fun TextView.setLastEditedDate(timeStamp: Long?) {
    if (timeStamp != null && timeStamp > 0) {
        this.isVisible = true
        this.text = timeStamp.toMMM_DD_hh_mm_ddd()
    } else {
        this.isVisible = false
    }
}

@BindingAdapter("app:setTextOrGone")
fun TextView.setTextOrGone(text: String?) {
    if (text.isNullOrEmpty()) {
        this.isVisible = false
    } else {
        this.isVisible = true
        this.text = text
    }
}

@BindingAdapter("app:lottie_playAnimation")
fun LottieAnimationView.play(play: Boolean) {
    if (play) {
        this.playAnimation()
    } else {
        this.cancelAnimation()
    }
}

@BindingAdapter("app:imageUrlOrGone")
fun ImageView.loadImageOrGone(url: String?) {
    url?.let {
        this.isVisible = true
        Glide.with(context)
            .load(url)
            .into(this)
    } ?: run {
        this.isVisible = false
    }
}

fun ImageView.loadImage(url: String?, listener: (Boolean) -> Unit) {
    url?.let {
        this.isVisible = true
        Glide.with(context)
            .load(url)
            .addListener(object : RequestListener<Drawable> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Drawable>?,
                    isFirstResource: Boolean
                ): Boolean {
                    listener.invoke(false)
                    return false
                }

                override fun onResourceReady(
                    resource: Drawable?,
                    model: Any?,
                    target: Target<Drawable>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    listener.invoke(true)
                    return false
                }

            })
            .centerCrop()
            .into(this)
    } ?: run {
        this.isVisible = false
    }
}