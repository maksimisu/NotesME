package com.maksimisu.notesme.data.models

data class Note(
    var title: String,
    var body: String,
    val creationDate: String,
    val lastUpdate: Long,
) {

    fun getTimeFormatted(): String {
        if (lastUpdate >= 1000 * 60 * 60 * 24) {
            return "${lastUpdate / 1000 / 60 / 60 / 24} days ago"
        } else if (lastUpdate >= 1000 * 60 * 60) {
            return "${lastUpdate / 1000 / 60 / 60} hours ago"
        } else if (lastUpdate >= 1000 * 60) {
            return "${lastUpdate / 1000 / 60} minutes ago"
        } else if (lastUpdate >= 1000) {
            return "${lastUpdate / 1000} seconds ago"
        } else if (lastUpdate > 0) {
            return "now"
        }
        return "Can not resolve!"
    }

}