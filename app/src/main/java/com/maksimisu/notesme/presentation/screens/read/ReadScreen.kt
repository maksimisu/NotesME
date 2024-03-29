package com.maksimisu.notesme.presentation.screens.read

import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.indication
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.maksimisu.notesme.R
import com.maksimisu.notesme.data.models.Note
import com.maksimisu.notesme.presentation.navigation.MainNavigation
import com.maksimisu.notesme.presentation.ui.components.TwoActionsDialog
import com.maksimisu.notesme.presentation.ui.theme.LightBlue
import java.util.Calendar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReadScreen(
    navHostController: NavHostController,
    name: String
) {
    val viewModel = hiltViewModel<ReadViewModel>()

    viewModel.loadNote(name)
    val note = viewModel.note.collectAsState(
        initial = Note(
            title = "Title",
            id = 0,
            body = "Body",
            lastModified = 0,
            creationDate = Calendar.getInstance().time.toString()
        )
    ).value

    var isDeleteNoteDialogVisible by remember { mutableStateOf(false) }
    var isOptionsMenuVisible by remember { mutableStateOf(false) }
    val interactionSource = remember { MutableInteractionSource() }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(id = R.string.read).uppercase(),
                        style = MaterialTheme.typography.headlineMedium,
                        modifier = Modifier
                            .offset(y = 12.dp)
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(64.dp),
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = LightBlue,
                    titleContentColor = Color.Black,
                    actionIconContentColor = Color.Black,
                ),
                navigationIcon = {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = stringResource(id = R.string.back),
                        modifier = Modifier
                            .offset(y = 8.dp)
                            .size(48.dp)
                            .clip(shape = RoundedCornerShape(100f))
                            .clickable {
                                navHostController.popBackStack()
                            }
                    )
                },
                actions = {
                    // EDIT MODE
                    Icon(
                        imageVector = Icons.Filled.Edit,
                        contentDescription = stringResource(id = R.string.edit),
                        modifier = Modifier
                            .offset(y = 8.dp)
                            .size(48.dp)
                            .clip(shape = RoundedCornerShape(100f))
                            .clickable {
                                navHostController
                                    .navigate(
                                        MainNavigation.EditScreen.route +
                                                "?name=${note.title}?lastId=${note.id}"
                                    )
                            }
                    )

                    // OPTIONS MENU
                    Icon(
                        imageVector = Icons.Filled.MoreVert,
                        contentDescription = stringResource(id = R.string.options_menu),
                        modifier = Modifier
                            .offset(y = 8.dp)
                            .size(48.dp)
                            .clip(shape = RoundedCornerShape(100f))
                            .indication(interactionSource, LocalIndication.current)
                            .pointerInput(true) {
                                detectTapGestures(
                                    onPress = {
                                        val press = PressInteraction.Press(it)
                                        interactionSource.emit(press)
                                        tryAwaitRelease()
                                        interactionSource.emit(PressInteraction.Release(press))

                                        // ACTION
                                        isOptionsMenuVisible = true
                                    }
                                )
                            }
                    )
                    DropdownMenu(
                        expanded = isOptionsMenuVisible,
                        onDismissRequest = {
                            isOptionsMenuVisible = false
                        }
                    ) {
                        DropdownMenuItem(
                            leadingIcon = {
                                Icon(
                                    imageVector = Icons.Filled.Delete,
                                    contentDescription = stringResource(id = R.string.delete)
                                )
                            },
                            text = { Text(text = stringResource(id = R.string.delete)) },
                            onClick = {
                                isDeleteNoteDialogVisible = true
                                isOptionsMenuVisible = false
                            }
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        paddingValues.calculateTopPadding()

        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            // DROP DOWN MENU


            // DELETE NOTE DIALOG
            if (isDeleteNoteDialogVisible) {
                TwoActionsDialog(
                    title = "DELETE?",
                    message = "This action cannot be cancelled!",
                    negativeButtonLabel = "Cancel",
                    positiveButtonLabel = "Delete",
                    negativeButtonAction = {
                        isDeleteNoteDialogVisible = false
                    },
                    positiveButtonAction = {
                        viewModel.deleteNote(note)
                        navHostController.popBackStack()
                    },
                    onDismissRequest = {
                        isDeleteNoteDialogVisible = false
                    }
                )
            }

            // CONTENT
            Column(
                modifier = Modifier
                    .padding(top = 74.dp)
                    .fillMaxSize()
                    .padding(horizontal = 10.dp)
                    .padding(top = 10.dp)
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = note.title,
                    style = MaterialTheme.typography.headlineMedium,
                )
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = note.body,
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier
                        .fillMaxSize()
                )
            }
        }

    }
}