package com.maksimisu.notesme.data.repository

import android.content.Context
import com.maksimisu.notesme.data.models.Note
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.File
import java.util.Calendar
import kotlin.time.Duration.Companion.minutes
import kotlin.time.Duration.Companion.seconds

class NotesRepository(@ApplicationContext val context: Context) {

    private val fileType = ".txt"

    fun saveNote(note: Note) {
        val file = File(context.filesDir, note.title + fileType)
        if (!file.exists())
            file.createNewFile()
        file.writeText("${note.creationDate}\n${note.body}", Charsets.UTF_8)
    }

    fun loadNotes(): List<Note> {
        val files = context.fileList().filter {
            it.endsWith(fileType)
        }
        val notes = mutableListOf<Note>()
        files.forEach { fileName ->
            var body = ""
            var creationDate = ""
            val lastUpdateDate = Calendar.getInstance().timeInMillis - File(context.filesDir, fileName).lastModified()
            val file = File(context.filesDir, fileName)
            file.useLines { lines ->
                creationDate = lines.elementAt(0)
            }
            file.useLines { lines ->
                val linesList = lines.toList()
                for (i in 1 until linesList.size)
                    if (i == 1)
                        body += linesList[i]
                    else
                        body += "\n${linesList[i]}"
            }
            notes.add(
                Note(
                    title = fileName.removeSuffix(fileType),
                    body = body,
                    creationDate = creationDate,
                    lastUpdate = lastUpdateDate
                )
            )
        }
        return notes
    }

    fun loadNote(fileName: String): Note? {
        val file = File(context.filesDir, fileName + fileType)
        if (file.exists()) {
            var body = ""
            var creationDate = ""
            val lastUpdateDate = Calendar.getInstance().timeInMillis - file.lastModified()
            file.useLines { lines ->
                creationDate = lines.elementAt(0)
            }
            file.useLines { lines ->
                val linesList = lines.toList()
                for (i in 1 until linesList.size) {
                    if (i == 1)
                        body += linesList[i]
                    else
                        body += "\n${linesList[i]}"
                }
            }
            return Note(
                title = fileName.removeSuffix(fileType),
                body = body,
                creationDate = creationDate,
                lastUpdate = lastUpdateDate
            )
        } else {
            return null
        }
    }

    fun renameNote(oldFileName: String, newFileName: String) {
        val oldFile = File(context.filesDir, oldFileName + fileType)
        val newFile = File(context.filesDir, newFileName + fileType)
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

}