package com.task.noteapp.util

import android.view.View
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter
import com.airbnb.lottie.LottieAnimationView

@BindingAdapter("app:isVisible")
fun View.setVisibility(isVisible: Boolean) {
    this.isVisible = isVisible
}

@BindingAdapter("app:lottie_playAnimation")
fun LottieAnimationView.play(play: Boolean) {
    if (play) {
        this.playAnimation()
    } else {
        this.cancelAnimation()
    }
}