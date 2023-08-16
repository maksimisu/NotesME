package com.maksimisu.notesme.presentation.screens.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.maksimisu.notesme.R
import com.maksimisu.notesme.presentation.navigation.MainNavigation
import com.maksimisu.notesme.presentation.ui.components.NoteItem
import com.maksimisu.notesme.presentation.ui.theme.LightBlue

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navHostController: NavHostController) {

    val viewModel = hiltViewModel<HomeViewModel>()
    val notes = viewModel.notes.collectAsStateWithLifecycle(initialValue = emptyList()).value

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
                    val lastId = if (notes.maxByOrNull { it.id } != null) {
                        notes.maxByOrNull { it.id }!!.id
                    } else {
                        0L
                    }
                    navHostController
                        .navigate(route = MainNavigation.EditScreen.route + "?name=${null}?lastId=${lastId}")
                },
                shape = RoundedCornerShape(100f),
                containerColor = LightBlue,
                contentColor = Color.Black
            ) {
                Icon(
                    imageVector = Icons.Filled.Add,
                    contentDescription = stringResource(id = R.string.add)
                )
            }
        }
    ) { paddingValues ->
        paddingValues.calculateTopPadding()

        // CONTENT
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 64.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(10.dp, Alignment.Top)
        ) {

            item {
                Spacer(modifier = Modifier.height(10.dp))
            }

            notes.forEach {
                item {
                    NoteItem(note = it) {
                        navHostController
                            .navigate(MainNavigation.ReadScreen.route + "/${it.title}")
                    }
                }
            }

            item {
                Spacer(modifier = Modifier.height(70.dp))
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun HomeScreenPreview() {
    HomeScreen(navHostController = rememberNavController())
}