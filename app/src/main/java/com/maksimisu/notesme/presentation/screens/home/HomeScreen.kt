package com.maksimisu.notesme.presentation.screens.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.maksimisu.notesme.R
import com.maksimisu.notesme.data.models.Note
import com.maksimisu.notesme.presentation.ui.components.NoteItem
import com.maksimisu.notesme.presentation.ui.components.TwoActionsDialog
import com.maksimisu.notesme.presentation.ui.theme.LightBlue

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navHostController: NavHostController) {

    var showDeleteNoteDialog by remember { mutableStateOf(false) }
    var selectedDeleteNoteId by remember { mutableStateOf<Int?>(null) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(id = R.string.home).uppercase(),
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
                actions = {
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
        }
    ) { paddingValues ->
        paddingValues.calculateTopPadding()

        if (showDeleteNoteDialog) {
            TwoActionsDialog(
                title = "DELETE?",
                message = "This action cannot be cancelled!",
                negativeButtonLabel = "Cancel",
                positiveButtonLabel = "Delete",
                negativeButtonAction = {
                    selectedDeleteNoteId = null
                },
                positiveButtonAction = {
                    if (selectedDeleteNoteId != null && selectedDeleteNoteId!! > 0)
                        TODO("Delete note. Not implemented yet.")
                    else
                        TODO("Not implemented yet.")
                },
                onDismissRequest = {
                    showDeleteNoteDialog = false
                    selectedDeleteNoteId = null
                }
            )
        }

        // CONTENT
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 74.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(10.dp, Alignment.Top)
        ) {
            val notes = listOf(
                Note(
                    id = 1,
                    title = "First note",
                    body = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin tincidunt sollicitudin elementum. Donec finibus lectus metus, vel porta orci pulvinar et. Curabitur non ligula venenatis, tincidunt dui nec, vestibulum est. Sed ex leo, porta a felis a, vehicula finibus dolor. Fusce feugiat luctus ante non placerat. Sed sed ipsum dui. Aliquam erat volutpat. Ut vel diam finibus, tristique odio in, vestibulum nisi. Integer et nulla convallis nisl faucibus varius ut a ligula. Ut fringilla nulla a elit tristique, aliquam congue risus pharetra. Suspendisse odio quam, placerat eget porta et, tempus varius magna. Morbi commodo lectus sed nisi facilisis finibus. Nam.",
                    creationDate = "25.06.2023::15:22",
                    lastUpdateDate = "25.06.2023::15:22"
                ),
                Note(
                    id = 2,
                    title = "Second note",
                    body = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin tincidunt sollicitudin elementum. Donec finibus lectus metus, vel porta orci pulvinar et. Curabitur non ligula venenatis, tincidunt dui nec, vestibulum est. Sed ex leo, porta a felis a, vehicula finibus dolor. Fusce feugiat luctus ante non placerat. Sed sed ipsum dui. Aliquam erat volutpat. Ut vel diam finibus, tristique odio in, vestibulum nisi. Integer et nulla convallis nisl faucibus varius ut a ligula. Ut fringilla nulla a elit tristique, aliquam congue risus pharetra. Suspendisse odio quam, placerat eget porta et, tempus varius magna. Morbi commodo lectus sed nisi facilisis finibus. Nam.",
                    creationDate = "25.06.2023::15:22",
                    lastUpdateDate = "25.06.2023::15:22"
                )
            )

            notes.forEach {
                item {
                    NoteItem(note = it) {
                        showDeleteNoteDialog = true
                        selectedDeleteNoteId = it.id
                    }
                }
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun HomeScreenPreview() {
    HomeScreen(navHostController = rememberNavController())
}