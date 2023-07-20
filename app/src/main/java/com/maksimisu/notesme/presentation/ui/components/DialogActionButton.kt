package com.maksimisu.notesme.presentation.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.maksimisu.notesme.presentation.models.ActionType

@Composable
fun DialogActionButton(
    actionType: ActionType,
    title: String,
    action: () -> Unit
) {
    Box(
        modifier = Modifier
            .width(90.dp)
            .height(25.dp)
            .clip(shape = RoundedCornerShape(50.dp))
            .background(actionType.color)
            .clickable {
                action()
            },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = title.uppercase(),
            style = MaterialTheme.typography.bodySmall
        )
    }
}