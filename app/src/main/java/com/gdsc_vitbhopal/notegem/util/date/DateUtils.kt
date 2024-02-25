package com.gdsc_vitbhopal.notegem.util.date

import java.text.SimpleDateFormat
import java.util.Locale

fun Long.toFullDate(): String {
    val sdf = SimpleDateFormat("MMM dd,yyyy h:mm a", Locale.getDefault())
    return sdf.format(this)
}