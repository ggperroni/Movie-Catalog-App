package br.com.ggperroni.moviecatalogapp.paging

interface Pagination<Index, Movie> {
    suspend fun loadNextPage()
    fun reset()
}