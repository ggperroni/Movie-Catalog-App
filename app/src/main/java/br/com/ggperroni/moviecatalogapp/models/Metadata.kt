package br.com.ggperroni.moviecatalogapp.models

data class Metadata(
    val currentPage: String,
    val page_count: Int,
    val per_page: Int,
    val totalCount: Int
)
