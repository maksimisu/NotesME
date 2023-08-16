package com.maksimisu.notesme.data.models

data class Note(
    var title: String,
    var id: Long,
    var body: String,
    val creationDate: String,
    val lastModified: Long,
) {

    fun getTimeFormatted(): String {
        if (lastModified >= 1000 * 60 * 60 * 24) {
            return "${lastModified / 1000 / 60 / 60 / 24} days ago"
        } else if (lastModified >= 1000 * 60 * 60) {
            return "${lastModified / 1000 / 60 / 60} hours ago"
        } else if (lastModified >= 1000 * 60) {
            return "${lastModified / 1000 / 60} minutes ago"
        } else if (lastModified >= 1000) {
            return "${lastModified / 1000} seconds ago"
        } else if (lastModified > 0) {
            return "now"
        }
        return "Can not resolve!"
    }

}