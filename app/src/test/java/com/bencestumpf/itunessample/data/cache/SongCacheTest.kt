package com.bencestumpf.itunessample.data.cache

import com.bencestumpf.itunessample.domain.model.Song
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.collection.IsIterableContainingInOrder.contains
import org.junit.Assert.assertThat
import org.junit.Before
import org.junit.Test
import java.util.*

class SongCacheTest {

    private var songCache: SongCache = SongCache()

    @Before
    fun setUp() {
    }

    @Test
    fun updateTest() {
        //Given
        val song1 = createSong(1)
        val song2 = createSong(2)
        val song3 = createSong(3)
        val dummyList = listOf(song1, song2, song3)

        //When
        songCache.update(dummyList)

        //Then
        assertThat(songCache.getSong(1).blockingGet(), `is`(song1))
        assertThat(songCache.getSong(2).blockingGet(), `is`(song2))
        assertThat(songCache.getSong(3).blockingGet(), `is`(song3))
    }

    @Test
    fun getNextTest() {
        //Given
        val song1 = createSong(1)
        val song2 = createSong(2)
        val song3 = createSong(3)
        val dummyList = listOf(song1, song2, song3)

        //When
        songCache.update(dummyList)

        //Then
        assertThat(songCache.getNextSong(1).blockingGet(), `is`(song2))
        assertThat(songCache.getNextSong(2).blockingGet(), `is`(song3))
        assertThat(songCache.getNextSong(3).blockingGet(), `is`(song1))
    }

    @Test
    fun getPreviousTest() {
        //Given
        val song1 = createSong(1)
        val song2 = createSong(2)
        val song3 = createSong(3)
        val dummyList = listOf(song1, song2, song3)

        //When
        songCache.update(dummyList)

        //Then
        assertThat(songCache.getPreviousSong(1).blockingGet(), `is`(song3))
        assertThat(songCache.getPreviousSong(2).blockingGet(), `is`(song1))
        assertThat(songCache.getPreviousSong(3).blockingGet(), `is`(song2))
    }

    @Test
    fun sortByTest() {
        //Given
        val song1 = createSong(1, 3, 2.0f, "rock")
        val song2 = createSong(2, 2, 1.0f, "ska")
        val song3 = createSong(3, 1, 1.5f, "alternative")
        val dummyList = listOf(song1, song2, song3)

        //When
        songCache.update(dummyList)

        //Then
        assertThat(songCache.sortByLength().blockingGet(), contains(song3, song2, song1))
        assertThat(songCache.sortByPrice().blockingGet(), contains(song2, song3, song1))
        assertThat(songCache.sortByGenre().blockingGet(), contains(song3, song1, song2))
    }

    private fun createSong(id: Long, length: Long = 1, price: Float = 1.0f, genre: String = "rock"): Song = Song(
        id, "", length, price, "", "",
        "", "", "", "", Date(), genre
    )

}