package br.com.ggperroni.moviecatalogapp.paging

import retrofit2.Response

class PaginationFactory<Index, Movie>(
    private val initialPage: Index,
    private inline val onLoadUpdated: (Boolean) -> Unit,
    private inline val onRequest: suspend (nextPage: Index) -> Response<Movie>,
    private inline val getNextIndex: suspend (Movie) -> Index,
    private inline val onError: suspend (Throwable?) -> Unit,
    private inline val onSuccess: suspend (items: Movie, newPage: Index) -> Unit,
) : Pagination<Index, Movie> {

    private var currentPage = initialPage
    private var isRequestInProgress = false
    override suspend fun loadNextPage() {
        if (isRequestInProgress) return
        isRequestInProgress = true
        onLoadUpdated(true)
        try {
            val response = onRequest(currentPage)
            if (response.isSuccessful) {
                isRequestInProgress = false
                val items = response.body()!!
                currentPage = getNextIndex(items)!!
                onSuccess(items, currentPage)
                onLoadUpdated(false)
            }
        } catch (e: Throwable) {
            onError(e)
            onLoadUpdated(false)
        }
    }

    override fun reset() {
        currentPage = initialPage
    }

}