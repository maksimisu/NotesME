package com.maksimisu.notesme.presentation.models

import androidx.compose.ui.graphics.Color
import com.maksimisu.notesme.presentation.ui.theme.DarkGreen
import com.maksimisu.notesme.presentation.ui.theme.DarkRed

enum class ActionType(val color: Color) {
    NEGATIVE(DarkRed),
    POSITIVE(DarkGreen)
}