package com.maksimisu.notesme.data.repository

import android.content.Context
import android.util.Log
import com.maksimisu.notesme.data.models.Note
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.File
import java.util.Calendar

class NotesRepository(@ApplicationContext val context: Context) {

    private val fileType = ".txt"

    fun saveNote(note: Note) {
        val file = File(context.filesDir, note.title + fileType)
        if (!file.exists())
            file.createNewFile()
        file.writeText("${note.id}\n${note.creationDate}\n${note.body}", Charsets.UTF_8)
    }

    fun loadNote(fileName: String): Note? {
        val file = File(context.filesDir, fileName + fileType)
        if (file.exists())
            return file.readFile()
        return null
    }

    fun loadNotes(): List<Note> {
        val files = context.fileList().filter {
            it.endsWith(fileType)
        }
        val notes = mutableListOf<Note>()
        files.forEach { fileName ->
            val file = File(context.filesDir, fileName)
            if (file.exists()) {
                notes.add(file.readFile())
            }
        }
        return notes
    }

    fun renameNote(oldFileName: String, newFileName: String) {
        val oldFile = File(context.filesDir, oldFileName + fileType)
        val newFile = File(context.filesDir, newFileName + fileType)
        /*if (oldFile.exists() && !newFile.exists()) {
            oldFile.useLines { lines ->
                var text = ""
                lines.forEach {
                    text += it
                }
                newFile.writeText(text, Charsets.UTF_8)
            }
        }*/
        oldFile.renameTo(newFile)
    }

    fun deleteNote(note: Note) {
        val file = File(context.filesDir, note.title + fileType)
        if (file.exists()) {
            file.delete()
        }
    }

    fun checkNoteExists(title: String): Boolean {
        val file = File(context.filesDir, title + fileType)
        return file.exists()
    }

    private fun File.readFile(): Note {

        val currentDate = Calendar.getInstance().timeInMillis
        val lines = this.readLines()

        var id = 0L
        lateinit var body: String
        val lastModified = currentDate - this.lastModified()
        lateinit var creationDate: String

        lines.forEachIndexed { index, string ->
            when (index) {
                0 -> id = string.toLong()
                1 -> creationDate = string
                2 -> body = string
                else -> body += "\n$string"
            }
        }

        return Note(
            title = this.name.removeSuffix(fileType),
            id = id,
            body = body,
            lastModified = lastModified,
            creationDate = creationDate
        )
    }

}