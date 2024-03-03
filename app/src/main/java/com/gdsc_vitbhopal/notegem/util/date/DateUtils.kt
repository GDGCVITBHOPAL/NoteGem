package com.gdsc_vitbhopal.notegem.util.date

import android.text.format.DateUtils
import java.text.SimpleDateFormat
import java.util.Locale

fun Long.formatDate(): String {
    val sdf = if (DateUtils.isToday(this))
        SimpleDateFormat("h:mm a", Locale.getDefault())
    else
        SimpleDateFormat("MMM dd,yyyy h:mm a", Locale.getDefault())
    return sdf.format(this)
}