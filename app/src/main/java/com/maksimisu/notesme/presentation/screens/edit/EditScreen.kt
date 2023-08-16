package com.maksimisu.notesme.presentation.screens.edit

import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.indication
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.maksimisu.notesme.R
import com.maksimisu.notesme.data.models.Note
import com.maksimisu.notesme.presentation.navigation.MainNavigation
import com.maksimisu.notesme.presentation.ui.theme.LightBlue
import java.util.Calendar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditScreen(
    navHostController: NavHostController,
    name: String?,
    lastIndex: Long
) {

    var isOptionsMenuVisible by remember { mutableStateOf(false) }
    val interactionSource = remember { MutableInteractionSource() }

    val viewModel = hiltViewModel<EditViewModel>()

    var canEditTitle by remember { mutableStateOf(true) }

    val noteExists = viewModel.noteExists.collectAsStateWithLifecycle(initialValue = false).value
    val note = viewModel.note.collectAsState(initial = null).value
    var tempNote by remember {
        mutableStateOf(
            Note(
                title = "",
                id = lastIndex + 1,
                body = "",
                creationDate = Calendar.getInstance().time.toString(),
                lastModified = 0
            )
        )
    }

    var title by remember { mutableStateOf("") }
    val bodyState by remember { mutableStateOf(UndoRedoState()) }

    if (name != null) {
        viewModel.loadNote(name)
        canEditTitle = false
    }

    if (note != null) {
        LaunchedEffect(key1 = Unit) {
            title = note.title
            bodyState.setInitialValues(note.body)
            tempNote = note
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(id = R.string.edit).uppercase(),
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
                    // SAVE
                    Icon(
                        imageVector = Icons.Filled.Done,
                        contentDescription = stringResource(id = R.string.done),
                        modifier = Modifier
                            .offset(y = 8.dp)
                            .size(48.dp)
                            .clip(shape = RoundedCornerShape(100f))
                            .clickable {
                                if (!noteExists) {
                                    tempNote.title = title
                                    tempNote.body = bodyState.value
                                    if (note == null) {
                                        viewModel.saveNote(tempNote)
                                    } else {
                                        if (tempNote.title == note.title) {
                                            viewModel.saveNote(tempNote)
                                        } else {
                                            viewModel.saveNote(tempNote)
                                            viewModel.renameNote(note.title, tempNote.title)
                                        }
                                    }
                                    navHostController.popBackStack()
                                    navHostController.popBackStack()
                                    navHostController.navigate(MainNavigation.HomeScreen.route)
                                    navHostController.navigate(
                                        MainNavigation.ReadScreen.route + "/${tempNote.title}"
                                    )
                                }
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
                        // UNDO
                        DropdownMenuItem(
                            leadingIcon = {
                                Icon(
                                    imageVector = Icons.Filled.ArrowBack,
                                    contentDescription = stringResource(id = R.string.undo)
                                )
                            },
                            text = { Text(text = stringResource(id = R.string.undo)) },
                            onClick = {
                                isOptionsMenuVisible = false
                                bodyState.undo()
                            },
                            enabled = bodyState.canUndo()
                        )
                        // REDO
                        DropdownMenuItem(
                            leadingIcon = {
                                Icon(
                                    imageVector = Icons.Filled.ArrowForward,
                                    contentDescription = stringResource(id = R.string.redo)
                                )
                            },
                            text = { Text(text = stringResource(id = R.string.redo)) },
                            onClick = {
                                isOptionsMenuVisible = false
                                bodyState.redo()
                            },
                            enabled = bodyState.canRedo()
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        paddingValues.calculateTopPadding()

        // CONTENT
        Column(
            modifier = Modifier
                .padding(top = 74.dp)
                .fillMaxSize()
                .padding(horizontal = 10.dp)
                .padding(top = 10.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ) {
            Column {
                Surface(
                    shadowElevation = 10.dp,
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    BasicTextField(
                        value = title,
                        onValueChange = {
                            title = it
                            viewModel.checkNoteExists(title)
                        },
                        textStyle = MaterialTheme.typography.headlineMedium,
                        singleLine = true,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(2.dp)
                    )
                }
                if (noteExists && title != note?.title) {
                    Text(
                        text = stringResource(id = R.string.note_already_exists),
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.Red
                    )
                }
            }
            Spacer(modifier = Modifier.height(10.dp))
            Surface(
                shadowElevation = 10.dp,
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = 10.dp)
            ) {
                BasicTextField(
                    value = bodyState.value,
                    onValueChange = {
                        bodyState.onInput(it)
                    },
                    textStyle = MaterialTheme.typography.bodySmall,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(2.dp)
                )
            }
        }
    }
}