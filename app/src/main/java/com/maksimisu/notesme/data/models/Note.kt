package com.maksimisu.notesme.data.models

data class Note(
    val id: Int,
    val title: String,
    val body: String,
    val creationDate: String,
    val lastUpdateDate: String,
)