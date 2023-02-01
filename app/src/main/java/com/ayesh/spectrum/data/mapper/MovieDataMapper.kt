package com.ayesh.spectrum.data.mapper

import com.ayesh.spectrum.data.local.entity.GenresEntity
import com.ayesh.spectrum.data.local.entity.MovieEntity
import com.ayesh.spectrum.data.remote.dto.GenresDto
import com.ayesh.spectrum.data.remote.dto.MovieDetailsDto
import com.ayesh.spectrum.data.remote.dto.MovieListResponse
import com.ayesh.spectrum.domain.model.MovieDetailModel
import com.ayesh.spectrum.domain.model.MovieModel
import java.math.RoundingMode
import java.text.DecimalFormat
import java.text.SimpleDateFormat

fun GenresDto.Genre.toGenresEntity(): GenresEntity {
    return GenresEntity(
        id = id,
        name = name
    )
}

fun MovieDetailModel.toMovieEntity(): MovieEntity {
    return MovieEntity(
        id = id,
        title = title,
        releaseDate = releaseDate,
        poster = poster,
        voteAvg = voteAvg,
        voteCount = voteCount
    )
}


fun MovieListResponse.Result.toMovieModel(): MovieModel {
    var pattern = "dd/MMM/yyyy"
    val parser = SimpleDateFormat("yyyy-MM-dd")
    val formatter = SimpleDateFormat(pattern)
    val formatterReleaseDate: String =
        if (release_date.isEmpty()) "" else formatter.format(parser.parse(release_date))
    return MovieModel(
        id = id,
        title = title,
        releaseDate = formatterReleaseDate,
        poster = poster_path,
        voteAvg = vote_average.toString(),
        voteCount = vote_count
    )
}

fun MovieEntity.toMovieModel(): MovieModel {
//    var pattern = "dd/MMM/yyyy"
//    val parser = SimpleDateFormat("yyyy-MM-dd")
//    val formatter = SimpleDateFormat(pattern)
//    val formatterReleaseDate: String = formatter.format(parser.parse(releaseDate))
    return MovieModel(
        id = id,
        title = title,
        releaseDate = releaseDate,
        poster = poster,
        voteAvg = voteAvg,
        voteCount = voteCount
    )
}


fun MovieDetailsDto.toMovieModel(): MovieDetailModel {
    var pattern = "dd/MMM/yyyy"
    val parser = SimpleDateFormat("yyyy-MM-dd")
    val formatter = SimpleDateFormat(pattern)
    val formatterReleaseDate: String = formatter.format(parser.parse(release_date))

    val df = DecimalFormat("#.##")
    df.roundingMode = RoundingMode.CEILING
    var formattedRating = df.format(vote_average)

    var languages = ""
    for (lan in spoken_languages) {
        languages = if (languages.isEmpty()) lan.name else "$languages | ${lan.name}"
    }
    var genresList = ""
    for (gen in genres) {
        genresList = if (genresList.isEmpty()) gen.name else "$genresList | ${gen.name}"
    }

    return MovieDetailModel(
        id = id,
        title = title,
        releaseDate = formatterReleaseDate,
        poster = poster_path,
        genreIds = genresList,
        voteAvg = formattedRating,
        voteCount = vote_count,
        tagLine = tagline,
        overview = overview,
        languages = languages,
        status = status,
        backDrop = backdrop_path
    )
}
