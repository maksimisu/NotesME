package com.maksimisu.notesme.data.local.models

data class Note(
    val id: Long,
    val title: String,
    val body: String,
    val fileName: String = "${id}_$title"
)