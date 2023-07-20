package com.maksimisu.notesme.presentation.screens.edit

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.maksimisu.notesme.R
import com.maksimisu.notesme.data.models.Note
import com.maksimisu.notesme.presentation.ui.theme.LightBlue
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Calendar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditScreen(
    navHostController: NavHostController,
    name: String?
) {

    val viewModel = hiltViewModel<EditViewModel>()

    var canEditTitle by remember { mutableStateOf(true) }

    if (name != null) {
        viewModel.loadNote(name)
        canEditTitle = false
    }

    val noteExists = viewModel.noteExists.collectAsStateWithLifecycle(initialValue = false).value
    val note = viewModel.note.collectAsState(
        initial = Note(
            "",
            "",
            Calendar.getInstance().time.toString(),
            0
        )
    ).value

    var title by remember { mutableStateOf(note.title) }
    var body by remember { mutableStateOf(note.body) }

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
                    // OPTIONS MENU
                    Icon(
                        imageVector = Icons.Filled.MoreVert,
                        contentDescription = stringResource(id = R.string.options_menu),
                        modifier = Modifier
                            .offset(y = 8.dp)
                            .size(48.dp)
                            .clip(shape = RoundedCornerShape(100f))
                            .clickable {}
                    )
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    if (!noteExists) {
                        note.title = title
                        note.body = body
                        viewModel.saveNote(note)
                        navHostController.popBackStack()
                    }
                },
                shape = RoundedCornerShape(100f),
                containerColor = LightBlue,
                contentColor = Color.Black
            ) {
                Icon(
                    imageVector = Icons.Filled.Done,
                    contentDescription = stringResource(id = R.string.done)
                )
            }
        }
    ) { paddingValues ->
        paddingValues.calculateTopPadding()

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
            if (name == null) {
                Column {
                    BasicTextField(
                        value = title,
                        onValueChange = {
                            title = it
                            viewModel.checkNoteExists(title)
                        },
                        textStyle = MaterialTheme.typography.headlineMedium
                    )
                    if (noteExists) {
                        Text(
                            text = stringResource(id = R.string.note_already_exists),
                            style = MaterialTheme.typography.bodySmall,
                            color = Color.Red
                        )
                    }
                }
            } else {
                BasicTextField(
                    value = title,
                    onValueChange = {
                        title = it
                    },
                    textStyle = MaterialTheme.typography.headlineMedium,
                    enabled = false
                )
            }
            Spacer(modifier = Modifier.height(10.dp))
            BasicTextField(
                value = body,
                onValueChange = { body = it },
                textStyle = MaterialTheme.typography.bodySmall,
                modifier = Modifier
                    .fillMaxSize()
            )
        }

    }

}