package com.maksimisu.notesme.presentation.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.compose.ui.window.SecureFlagPolicy
import com.maksimisu.notesme.data.models.ActionType
import com.maksimisu.notesme.presentation.ui.theme.LightBlue

@Composable
fun TwoActionsDialog(
    title: String,
    message: String,
    negativeButtonLabel: String,
    positiveButtonLabel: String,
    negativeButtonAction: () -> Unit,
    positiveButtonAction: () -> Unit,
    onDismissRequest: () -> Unit
) {
    Dialog(
        onDismissRequest = onDismissRequest,
        properties = DialogProperties(
            dismissOnClickOutside = false,
            dismissOnBackPress = true
        )
    ) {
        Box(
            modifier = Modifier
                .width(200.dp)
                .clip(shape = RoundedCornerShape(10.dp))
                .background(LightBlue)
                .padding(5.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.Top
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.headlineMedium
                )
                Text(
                    text = message,
                    style = MaterialTheme.typography.bodySmall
                )
                Spacer(modifier = Modifier.height(10.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    DialogActionButton(
                        actionType = ActionType.POSITIVE,
                        title = positiveButtonLabel
                    ) {
                        positiveButtonAction()
                        onDismissRequest()
                    }
                    DialogActionButton(
                        actionType = ActionType.NEGATIVE,
                        title = negativeButtonLabel
                    ) {
                        negativeButtonAction()
                        onDismissRequest()
                    }
                }
            }
        }
    }
}