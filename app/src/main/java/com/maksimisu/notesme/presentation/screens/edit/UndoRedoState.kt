package com.maksimisu.notesme.presentation.screens.edit

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

class UndoRedoState {

    var value by mutableStateOf("")

    private val changesList = mutableListOf<String>()
    private var currentValueNumber = 0

    fun setInitialValues(value: String) {
        this.value = value
        changesList.add(value)
    }

    fun onInput(value: String) {

        if (value != this.value) {
            this.value = value

            if (currentValueNumber + 1 < changesList.size) {
                var i = currentValueNumber + 1
                var size = changesList.size
                while (i < size) {
                    changesList.removeAt(i)
                    size--
                    i++
                }
            }

            if (changesList.size > 20 - 1) {
                changesList.removeFirst()
                changesList.add(value)
            } else {
                changesList.add(value)
                currentValueNumber++
            }
        }
    }

    fun undo() {
        if (canUndo()) {
            value = changesList[--currentValueNumber]
        }
    }

    fun redo() {
        if (canRedo()) {
            value = changesList[++currentValueNumber]
        }
    }

    fun canUndo() = currentValueNumber > 0

    fun canRedo() = currentValueNumber < changesList.size - 1
}