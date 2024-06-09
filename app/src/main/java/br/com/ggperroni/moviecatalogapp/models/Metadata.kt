package br.com.ggperroni.moviecatalogapp.models

data class Metadata(
    val currentPage: String,
    val pageCount: Int,
    val perPage: Int,
    val totalCount: Int
)
