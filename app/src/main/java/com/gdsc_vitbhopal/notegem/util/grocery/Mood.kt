package com.gdsc_vitbhopal.notegem.util.grocery

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.ui.graphics.Color
import com.gdsc_vitbhopal.notegem.R
import com.gdsc_vitbhopal.notegem.ui.theme.Blue
import com.gdsc_vitbhopal.notegem.ui.theme.DarkOrange
import com.gdsc_vitbhopal.notegem.ui.theme.Green
import com.gdsc_vitbhopal.notegem.ui.theme.Purple

enum class Mood(@DrawableRes val icon: Int, val color: Color, @StringRes val title: Int) {
    AWESOME(R.drawable.ic_very_happy, Green, R.string.awesome),
    GOOD(R.drawable.ic_happy, Blue, R.string.good),
    OKAY(R.drawable.ic_ok_face, Purple, R.string.okay),
    BAD(R.drawable.ic_sad, DarkOrange, R.string.bad),
    TERRIBLE(R.drawable.ic_very_sad, Color.Red, R.string.terrible)
}