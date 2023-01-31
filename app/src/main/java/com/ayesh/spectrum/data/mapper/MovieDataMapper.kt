package com.ayesh.spectrum.data.mapper

import com.ayesh.spectrum.data.local.entity.GenresEntity
import com.ayesh.spectrum.data.remote.dto.GenresDto
import com.ayesh.spectrum.data.remote.dto.MovieListResponse
import com.ayesh.spectrum.domain.model.MovieModel
import java.text.SimpleDateFormat

fun GenresDto.Genre.toGenresEntity(): GenresEntity {
    return GenresEntity(
        id = id,
        name = name
    )
}


fun MovieListResponse.Result.toMovieModel(): MovieModel {
    var pattern = "dd/MMM/yyyy"
    val parser = SimpleDateFormat("yyyy-MM-dd")
    val formatter = SimpleDateFormat(pattern)
    val formatterReleaseDate: String = formatter.format(parser.parse(release_date))
    return MovieModel(
        id = id,
        title = title,
        releaseDate = formatterReleaseDate,
        poster = poster_path,
        genreIds = genre_ids,
        voteAvg = vote_average,
        voteCount = vote_count
    )
}
