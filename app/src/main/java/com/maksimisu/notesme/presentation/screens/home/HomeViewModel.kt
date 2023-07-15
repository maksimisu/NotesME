package com.maksimisu.notesme.presentation.screens.home

import androidx.lifecycle.ViewModel
import com.maksimisu.notesme.data.models.Note
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor() : ViewModel() {

    private val _notes = emptyFlow<List<Note>>()
    val notes: Flow<List<Note>>
        get() = _notes

    init {
        getNotes()
    }

    private fun getNotes() {

    }
}