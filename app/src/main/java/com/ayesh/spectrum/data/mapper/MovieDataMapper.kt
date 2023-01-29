package com.ayesh.spectrum.data.mapper

import com.ayesh.spectrum.data.local.entity.GenresEntity
import com.ayesh.spectrum.data.remote.dto.GenresDto

fun GenresDto.Genre.toGenresEntity(): GenresEntity {
    return GenresEntity(
        id = id,
        name = name
    )
}
