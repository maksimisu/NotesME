package com.maksimisu.notesme.presentation.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.maksimisu.notesme.R
import com.maksimisu.notesme.data.models.Note
import com.maksimisu.notesme.presentation.ui.theme.LightBlue
import com.maksimisu.notesme.presentation.ui.theme.LightYellow

@Composable
fun NoteItem(
    note: Note,
    onClick: () -> Unit
) {
    var isExpanded by rememberSaveable { mutableStateOf(false) }
    Box(
        modifier = Modifier
            .width(320.dp)
            .clip(shape = RoundedCornerShape(30.dp))
            .background(LightBlue)
            .clickable {
                onClick()
            },
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Top
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 10.dp, top = 10.dp, end = 12.dp),
                verticalAlignment = Alignment.Bottom,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text(
                        text = note.title,
                        style = MaterialTheme.typography.headlineMedium
                    )
                    Text(
                        text =  "${stringResource(id = R.string.last_update)} ${note.getTimeFormatted()}",
                        style = MaterialTheme.typography.bodySmall
                    )
                }
                Box(
                    modifier = Modifier
                        .padding(bottom = 20.dp)
                        .size(24.dp)
                        .clip(shape = RoundedCornerShape(24.dp))
                        .background(Color.Black),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = note.id.toString(),
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.White
                    )
                }
            }
            if (!isExpanded) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(
                            shape = RoundedCornerShape(
                                topStart = 0.dp,
                                topEnd = 30.dp,
                                bottomEnd = 30.dp,
                                bottomStart = 30.dp
                            )
                        )
                        .background(LightYellow)
                        .padding(start = 10.dp, bottom = 10.dp)
                ) {
                    Text(
                        text = note.body,
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier
                            .weight(1f)
                            .align(Alignment.Top),
                        maxLines = 3,
                        overflow = TextOverflow.Ellipsis
                    )
                    Icon(
                        imageVector = Icons.Filled.ArrowDropDown,
                        contentDescription = stringResource(id = R.string.expand),
                        tint = Color.Black,
                        modifier = Modifier
                            .size(48.dp)
                            .clip(RoundedCornerShape(100f))
                            .align(Alignment.Bottom)
                            .clickable {
                                isExpanded = true
                            }
                    )
                }
            } else {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(
                            shape = RoundedCornerShape(
                                topStart = 0.dp,
                                topEnd = 30.dp,
                                bottomEnd = 30.dp,
                                bottomStart = 30.dp
                            )
                        )
                        .background(LightYellow)
                        .padding(start = 10.dp, bottom = 10.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .align(Alignment.Top)
                    ) {
                        Text(
                            text = note.body,
                            style = MaterialTheme.typography.bodySmall,
                            modifier = Modifier
                                .widthIn(max = 262.dp),
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis
                        )
                        Spacer(modifier = Modifier.height(5.dp))
                        Text(
                            text = "${stringResource(id = R.string.creation_date)} ${note.creationDate}",
                            style = MaterialTheme.typography.bodySmall,
                            modifier = Modifier
                                .width(262.dp),
                        )
                        Spacer(modifier = Modifier.height(5.dp))
                        Text(
                            text = "${stringResource(id = R.string.symbols_used)}${note.body.trim().length}/20000",
                            style = MaterialTheme.typography.bodySmall,
                            modifier = Modifier
                                .width(262.dp),
                        )
                    }
                    Icon(
                        imageVector = Icons.Filled.ArrowDropDown,
                        contentDescription = stringResource(id = R.string.narrow),
                        tint = Color.Black,
                        modifier = Modifier
                            .size(48.dp)
                            .clip(RoundedCornerShape(100f))
                            .align(Alignment.Bottom)
                            .rotate(180f)
                            .clickable {
                                isExpanded = false
                            }
                    )
                }
            }
        }
    }
}