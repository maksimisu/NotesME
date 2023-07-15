package com.maksimisu.notesme.presentation.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.maksimisu.notesme.R

val MonotypeCorsiva = FontFamily(Font(R.font.monotype_corsiva))

val Typography = Typography(

    bodySmall = TextStyle(
        fontFamily = MonotypeCorsiva,
        fontWeight = FontWeight.W400,
        fontSize = 16.sp,
        lineHeight = 18.sp,
        letterSpacing = 0.5.sp,
        //textAlign = TextAlign.Justify,
        color = Color.Black
    ),

    headlineMedium = TextStyle(
        fontFamily = MonotypeCorsiva,
        fontWeight = FontWeight.W400,
        fontSize = 36.sp,
        lineHeight = 18.sp,
        letterSpacing = 0.5.sp,
        color = Color.Black
    )
)