package com.maksimisu.notesme.presentation.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.maksimisu.notesme.data.models.Note
import com.maksimisu.notesme.data.repository.NotesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val notesRepository: NotesRepository
) : ViewModel() {

    val notes = flow {
        val data = notesRepository.loadNotes().sortedByDescending {
            it.lastUpdate
        }
        emit(data)
    }
}