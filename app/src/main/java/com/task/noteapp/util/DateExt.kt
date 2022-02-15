package com.task.noteapp.util

import android.text.format.DateFormat
import java.util.*

fun Long.toDD_MM_YYYY(): String {
    val calendar = Calendar.getInstance(Locale.ENGLISH)
    calendar.timeInMillis = this
    return DateFormat.format("dd/MM/yyyy", calendar).toString()
}

fun Long.toDD_MMM(): String {
    val calendar = Calendar.getInstance(Locale.ENGLISH)
    calendar.timeInMillis = this
    return DateFormat.format("dd MMM", calendar).toString()
}

fun Long.toMMM_DD_hh_mm_ddd(): String {
    val calendar = Calendar.getInstance(Locale.ENGLISH)
    calendar.timeInMillis = this
    return DateFormat.format("MMMM dd, hh:mm EEE", calendar).toString()
}