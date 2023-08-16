package com.maksimisu.notesme.presentation.screens.config

import com.maksimisu.notesme.presentation.models.AppFontSize
import com.maksimisu.notesme.presentation.models.AppTheme

data class ConfigScreenStateHolder(
    val theme: AppTheme,
    val fontSize: AppFontSize,
    val autoSaveEditable: Boolean
)