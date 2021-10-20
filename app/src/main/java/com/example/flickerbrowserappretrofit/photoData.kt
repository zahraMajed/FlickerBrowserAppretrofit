package com.example.flickerbrowserappretrofit

class photoData(
    val photos: Photos,
    val stat: String
) {
    class Photos(
        val page: Int,
        val pages: Int,
        val perpage: Int,
        val photo: List<Photo>,
        val total: Int
    ) {
        class Photo(
            val farm: Int,
            val id: String,
            val isfamily: Int,
            val isfriend: Int,
            val ispublic: Int,
            val owner: String,
            val secret: String,
            val server: String,
            val title: String
        )
    }
}