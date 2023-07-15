package com.maksimisu.notesme.data.repository

import com.maksimisu.notesme.data.models.Note
import com.maksimisu.notesme.di.NotesApp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import kotlin.time.Duration.Companion.days

class NotesRepository() {

    private val fileType = ".txt"
    private val path = NotesApp().filesDir

    suspend fun saveNote(note: Note) {
        withContext(Dispatchers.IO) {
            val file = File(path, note.title + fileType)
            if (!file.exists())
                file.createNewFile()
            file.writeText("${note.id}\n${note.creationDate}\n${note.body}", Charsets.UTF_8)
        }
    }

    suspend fun loadNotes(): List<Note> {
        val files = NotesApp().fileList().filter {
            it.endsWith(fileType)
        }
        val notes = mutableListOf<Note>()
        files.forEach {fileName ->
            var id: Int? = null
            var body = ""
            var creationDate = ""
            val lastUpdateDate = File(path, fileName).lastModified().days.toString()
            File(path, fileName).useLines { lines ->
                id = lines.elementAt(0).toInt()
                creationDate = lines.elementAt(1)
                for (i in 2 until lines.count()) {
                    body += lines.elementAt(i)
                }
            }
            notes.add(Note(
                id = id!!,
                title = fileName.removeSuffix(fileType),
                body = body,
                creationDate = creationDate,
                lastUpdateDate = lastUpdateDate
            ))
        }
        return notes
    }

    suspend fun loadNote(fileName: String): Note? {
        val file = File(path, fileName + fileType)
        if (file.exists()) {
            var id: Int? = null
            var body = ""
            var creationDate = ""
            val lastUpdateDate = file.lastModified().days.toString()
            file.useLines {lines ->
                id = lines.elementAt(0).toInt()
                creationDate = lines.elementAt(1)
                for (i in 2 until lines.count()) {
                    body += lines.elementAt(i)
                }
            }
            return Note(
                id = id!!,
                title = fileName.removeSuffix(fileType),
                body = body,
                creationDate = creationDate,
                lastUpdateDate = lastUpdateDate
            )
        } else {
            return null
        }
    }

    suspend fun renameNote(oldFileName: String, newFileName: String) {
        val oldFile = File(path, oldFileName + fileType)
        val newFile = File(path, newFileName + fileType)
        if (oldFile.exists() && !newFile.exists()) {
            oldFile.useLines { lines ->
                var text = ""
                lines.forEach {
                    text += it
                }
                newFile.writeText(text, Charsets.UTF_8)
            }
        }
    }

    suspend fun deleteNote(note: Note) {
        val file = File(path, note.title + fileType)
        if (file.exists()) {
            file.delete()
        }
    }

}