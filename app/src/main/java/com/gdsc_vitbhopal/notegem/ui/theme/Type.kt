package com.gdsc_vitbhopal.notegem.ui.theme

import androidx.compose.material.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.gdsc_vitbhopal.notegem.R

val Kanit = FontFamily(
    Font(R.font.kanit_light),
    Font(R.font.kanit_regular, FontWeight.Bold)
)

val BebeasNeue = FontFamily(
    Font(R.font.bebas_neue, FontWeight.Bold)
)

val Mono = FontFamily(
    Font(R.font.mono, FontWeight.Normal)
)

val Sans = FontFamily(
    Font(R.font.sans_extralight),
    Font(R.font.sans_light),
    Font(R.font.sans_medium, FontWeight.Bold),
    Font(R.font.sans_semibold)

)
// Set of Material typography styles to start with
fun getTypography(font: FontFamily) = Typography(
    defaultFontFamily = font,
    body1 = TextStyle(
//        fontFamily = Kanit,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp
    ),
    body2 = TextStyle(
//        fontFamily = Kanit,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp
    ),
    button = TextStyle(
//        fontFamily = Kanit,
        fontWeight = FontWeight.W500,
        fontSize = 14.sp
    ),
    h1 = TextStyle(
//        fontFamily = Kanit,
        fontWeight = FontWeight.Normal,
        fontSize = 96.sp
    ),
    h2 = TextStyle(
//        fontFamily = Kanit,
        fontWeight = FontWeight.Normal,
        fontSize = 60.sp
    ),
    h3 = TextStyle(
//        fontFamily = Kanit,
        fontWeight = FontWeight.Normal,
        fontSize = 48.sp
    ),
    h4 = TextStyle(
//        fontFamily = BebeasNeue,
        fontWeight = FontWeight.Normal,
        fontSize = 34.sp
    ),
    h5 = TextStyle(
//        fontFamily = Kanit,
        fontWeight = FontWeight.Normal,
        fontSize = 24.sp
    ),
    h6 = TextStyle(
//        fontFamily = BebeasNeue,
        fontWeight = FontWeight.SemiBold,
        fontSize = 20.sp
    ),
    subtitle1 = TextStyle(
//        fontFamily = Kanit,
        fontWeight = FontWeight.Normal,
        fontSize = 10.sp
    )
    /* Other default text styles to override
    button = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.W500,
        fontSize = 14.sp
    ),
    caption = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp
    )
    */
)