package br.com.ggperroni.moviecatalogapp.viewModel

import br.com.ggperroni.moviecatalogapp.models.MoviesList
import br.com.ggperroni.moviecatalogapp.utils.RetrofitInstance
import retrofit2.Response

class Repository {
    suspend fun getMovieList(page: Int): Response<MoviesList> {
        return RetrofitInstance.api.getMovies(page)
    }
}