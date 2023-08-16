package com.maksimisu.notesme.presentation.screens.edit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.maksimisu.notesme.data.models.Note
import com.maksimisu.notesme.data.repository.NotesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import java.util.Calendar
import javax.inject.Inject

@HiltViewModel
class EditViewModel @Inject constructor(
    private val notesRepository: NotesRepository
) : ViewModel() {

    private var _note = MutableStateFlow<Note?>(value = null)
    val note: Flow<Note?> = _note.asStateFlow()

    private val _noteExists = MutableStateFlow(false)
    val noteExists: Flow<Boolean> = _noteExists.asStateFlow()

    fun loadNote(fileName: String) {
        viewModelScope.launch {
            _note.emit(notesRepository.loadNote(fileName))
        }
    }

    fun saveNote(note: Note) {
        viewModelScope.launch {
            notesRepository.saveNote(note)
        }
    }

    fun renameNote(oldFileName: String, newFileName: String) {
        viewModelScope.launch {
            notesRepository.renameNote(oldFileName, newFileName)
        }
    }

    fun checkNoteExists(title: String) {
        viewModelScope.launch {
            _noteExists.emit(notesRepository.checkNoteExists(title))
        }
    }

}