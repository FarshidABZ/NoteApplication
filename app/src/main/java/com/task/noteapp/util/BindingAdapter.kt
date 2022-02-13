package com.task.noteapp.util

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter
import com.airbnb.lottie.LottieAnimationView
import com.bumptech.glide.Glide

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
        Glide.with(context)
            .load(url)
            .into(this)
    } ?: run {
        this.isVisible = false
    }
}