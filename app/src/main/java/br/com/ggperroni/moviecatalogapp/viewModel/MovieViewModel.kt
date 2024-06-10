package br.com.ggperroni.moviecatalogapp.viewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.ggperroni.moviecatalogapp.models.Data
import br.com.ggperroni.moviecatalogapp.models.Details
import br.com.ggperroni.moviecatalogapp.paging.PaginationFactory
import kotlinx.coroutines.launch

class MovieViewModel : ViewModel() {
    private val repository = Repository()
    var state by mutableStateOf(ScreenState())
    var genre by mutableIntStateOf(0)
    var id by mutableIntStateOf(0)

    private val pagination = PaginationFactory(
        initialPage = state.page,
        onLoadUpdated = {
            state = state.copy(isLoading = it)
        },
        onRequest = {
            repository.getMovieList(page = it)
        },
        getNextIndex = {
            state.page + 1
        },
        onError = {
            state = state.copy(error = it?.localizedMessage)
        },
        onSuccess = { movies, newPage ->
            state = state.copy(
                movies = state.movies + movies.data,
                page = newPage,
                endReached = state.page == 25
            )
        }
    )

    init {
        loadNextMovies()
    }

    fun loadNextMovies() {
        viewModelScope.launch {
            pagination.loadNextPage()
        }
    }

    fun getDetailsById() {
        viewModelScope.launch {
            try {
                val response = repository.getDetailsById(id = id)
                if (response.isSuccessful) {
                    state = state.copy(detailsData = response.body()!!)
                }
            } catch (e: Exception) {
                state = state.copy(error = e.localizedMessage)
            }
        }
    }
}

data class ScreenState(
    val movies: List<Data> = emptyList(),
    val page: Int = 1,
    val detailsData: Details = Details(),
    val endReached: Boolean = false,
    val error: String? = null,
    val isLoading: Boolean = false
)