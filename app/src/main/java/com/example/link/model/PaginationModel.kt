package com.example.link.model

data class PaginationModel(
    val current_page: Int = 0,
    val from: Int = 0,
    val last_page: Int = 0,
    val path: String = "",
    val per_page: Int = 0,
    val to: Int = 0,
    val total: Int = 0
)