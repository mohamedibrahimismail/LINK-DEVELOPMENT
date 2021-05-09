package com.example.link.model

import java.io.Serializable

data class GenericResponse<T>(
    val status: String?,
    val source: String?,
    val sortBy: String?,
    val articles: T?,
) : Serializable