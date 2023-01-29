package com.ayesh.spectrum.data.local

import androidx.lifecycle.asLiveData
import androidx.test.filters.SmallTest
import com.ayesh.spectrum.data.local.dao.GenresDao
import com.ayesh.spectrum.data.local.entity.GenresEntity
import com.ayesh.spectrum.getOrAwaitValue
import com.google.common.truth.Truth
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject
import javax.inject.Named

@SmallTest
@HiltAndroidTest
class GenresDaoTest {

    @get:Rule
    var  hiltRule = HiltAndroidRule(this)

    @Inject
    @Named("movie_test_db")
    lateinit var dataBase: MovieDataBase
    private lateinit var dao: GenresDao

    @Before
    fun setup(){
        hiltRule.inject()
        dao = dataBase.genresDao()
    }

    @After
    fun endCall(){
        dataBase.close()
    }

    @Test
    fun insertGenre() = runBlocking {
        val genresEntity1 = GenresEntity(id = 100, name = "War Movie")
        val genresEntity2 = GenresEntity(id = 101, name = "Romantic Movie")
        dao.insertGenres(listOf(genresEntity1,genresEntity2))
        val all = dao.getAllGenresFromLocal().asLiveData().getOrAwaitValue()
        Truth.assertThat(all).contains(genresEntity1)

    }
    @Test
    fun deleteGenre() = runBlocking {
        dao.deleteGenresAll()
        val all = dao.getAllGenresFromLocal().asLiveData().getOrAwaitValue()
        Truth.assertThat(all).isEmpty()

    }
}