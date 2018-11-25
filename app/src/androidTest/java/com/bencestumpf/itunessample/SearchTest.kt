package com.bencestumpf.itunessample

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions.*
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import android.view.KeyEvent
import android.widget.AutoCompleteTextView
import com.bencestumpf.itunessample.domain.model.Song
import com.bencestumpf.itunessample.domain.repositories.SongRepository
import com.bencestumpf.itunessample.helper.ChildMatcher.hasChildren
import com.bencestumpf.itunessample.helper.ChildMatcher.withIndex
import com.bencestumpf.itunessample.helper.espressoDaggerMockRule
import com.bencestumpf.itunessample.presentation.search.SearchActivity
import com.nhaarman.mockito_kotlin.*
import io.reactivex.Single
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.not
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.`when`
import java.util.*

@RunWith(AndroidJUnit4::class)
class SearchTest {

    private val songRepository: SongRepository = mock()

    @get:Rule
    val rule = espressoDaggerMockRule()

    @get:Rule
    val activityRule = ActivityTestRule(SearchActivity::class.java, false, false)

    private val dummySong = Song(
        1, "My title", 1000, 2.0f, "USD", "Regular Folks",
        "", "Albumm",
        "https://is1-ssl.mzstatic.com/image/thumb/Music2/v4/a2/66/32/a2663205-663c-8301-eec7-57937c2d0878/source/100x100bb.jpg",
        "https://is1-ssl.mzstatic.com/image/thumb/Music2/v4/a2/66/32/a2663205-663c-8301-eec7-57937c2d0878/source/100x100bb.jpg",
        Date(), "Rock"
    )

    private val dummySong2 = Song(
        2, "My title2", 1000, 1.0f, "USD", "Regular Folks2",
        "", "Albumm2",
        "https://is1-ssl.mzstatic.com/image/thumb/Music2/v4/a2/66/32/a2663205-663c-8301-eec7-57937c2d0878/source/100x100bb.jpg",
        "https://is1-ssl.mzstatic.com/image/thumb/Music2/v4/a2/66/32/a2663205-663c-8301-eec7-57937c2d0878/source/100x100bb.jpg",
        Date(), "Rock"
    )

    @Test
    fun welcomeScreenShowsAtFirst() {
        //Given

        //When
        activityRule.launchActivity(null)

        //Then
        onView(withId(R.id.error_view)).check(matches(not(isDisplayed())))
        onView(withId(R.id.empty_view)).check(matches(not(isDisplayed())))
        onView(withId(R.id.search_swipeRefresh)).check(matches(not(isDisplayed())))
        onView(withId(R.id.welcome_view)).check(matches(isDisplayed()))
    }

    @Test
    fun searchErrorShowsErrorView() {
        //Given
        `when`(songRepository.searchWith(any())).thenReturn(Single.error(RuntimeException("Test exception")))

        //When
        launchAndSearch()

        //Then
        verify(songRepository, atLeastOnce()).searchWith(any())
        onView(withId(R.id.error_view)).check(matches(isDisplayed()))
        onView(withId(R.id.empty_view)).check(matches(not(isDisplayed())))
        onView(withId(R.id.search_swipeRefresh)).check(matches(not(isDisplayed())))
        onView(withId(R.id.welcome_view)).check(matches(not(isDisplayed())))
    }

    @Test
    fun searchEmptyResponseShowsEmptyView() {
        //Given
        `when`(songRepository.searchWith(any())).thenReturn(Single.just(listOf()))

        //When
        launchAndSearch()

        //Then
        verify(songRepository, atLeastOnce()).searchWith(any())
        onView(withId(R.id.error_view)).check(matches(not(isDisplayed())))
        onView(withId(R.id.empty_view)).check(matches(isDisplayed()))
        onView(withId(R.id.search_swipeRefresh)).check(matches(not(isDisplayed())))
        onView(withId(R.id.welcome_view)).check(matches(not(isDisplayed())))
    }

    @Test
    fun searchValidResponseShowsContentView() {
        //Given
        val dummySong = Song(
            1, "My title", 1000, 1.0f, "USD", "Regular Folks",
            "", "Albumm",
            "https://is1-ssl.mzstatic.com/image/thumb/Music2/v4/a2/66/32/a2663205-663c-8301-eec7-57937c2d0878/source/100x100bb.jpg",
            "https://is1-ssl.mzstatic.com/image/thumb/Music2/v4/a2/66/32/a2663205-663c-8301-eec7-57937c2d0878/source/100x100bb.jpg",
            Date(), "Rock"
        )
        `when`(songRepository.searchWith(any())).thenReturn(Single.just(listOf(dummySong)))

        //When
        launchAndSearch()

        //Then
        verify(songRepository, atLeastOnce()).searchWith(any())
        onView(withId(R.id.error_view)).check(matches(not(isDisplayed())))
        onView(withId(R.id.empty_view)).check(matches(not(isDisplayed())))
        onView(withId(R.id.search_swipeRefresh)).check(matches(isDisplayed()))
        onView(withId(R.id.welcome_view)).check(matches(not(isDisplayed())))

        onView(withId(R.id.search_list)).check(matches(hasChildren(`is`(1))))
        onView(withId(R.id.song_title)).check(matches(withText(dummySong.title)))
        onView(withId(R.id.song_artist)).check(matches(withText(dummySong.artistName)))
        onView(withId(R.id.song_album)).check(matches(withText(dummySong.album)))
        onView(withId(R.id.song_genre)).check(matches(withText(dummySong.genre)))
        onView(withId(R.id.song_length)).check(matches(withText("0:01")))
    }

    @Test
    fun searchValidResponseShowsContentViewMultipleResults() {
        //Given
        `when`(songRepository.searchWith(any())).thenReturn(Single.just(listOf(dummySong, dummySong2)))

        //When
        launchAndSearch()

        //Then
        verify(songRepository, atLeastOnce()).searchWith(any())
        onView(withId(R.id.error_view)).check(matches(not(isDisplayed())))
        onView(withId(R.id.empty_view)).check(matches(not(isDisplayed())))
        onView(withId(R.id.search_swipeRefresh)).check(matches(isDisplayed()))
        onView(withId(R.id.welcome_view)).check(matches(not(isDisplayed())))

        onView(withId(R.id.search_list)).check(matches(hasChildren(`is`(2))))
    }


    @Test
    fun reorderInvokesRepositoryAndRefreshesTheView() {
        //Given
        `when`(songRepository.searchWith(any())).thenReturn(Single.just(listOf(dummySong, dummySong2)))
        `when`(songRepository.sortByPrice()).thenReturn(Single.just(listOf(dummySong2, dummySong)))

        //When
        launchAndSearch()

        //Then
        onView(withIndex(withId(R.id.song_title), 0)).check(matches(withText(dummySong.title)))
        onView(withIndex(withId(R.id.song_title), 1)).check(matches(withText(dummySong2.title)))

        //When
        onView(withId(R.id.order))
            .perform(click())
        onView(withText(R.string.price))
            .perform(click())

        //Then
        verify(songRepository, atLeastOnce()).searchWith(any())
        verify(songRepository).sortByPrice()
        onView(withIndex(withId(R.id.song_title), 0)).check(matches(withText(dummySong2.title)))
        onView(withIndex(withId(R.id.song_title), 1)).check(matches(withText(dummySong.title)))
    }


    @Test
    fun songRowClickNavigatesToDetails() {
        //Given
        `when`(songRepository.searchWith(any())).thenReturn(Single.just(listOf(dummySong, dummySong2)))
        `when`(songRepository.getSong(eq(dummySong2.trackId))).thenReturn(Single.just(dummySong2))

        //When
        launchAndSearch()
        onView(withIndex(withId(R.id.song_title), 1)).perform(click())

        //Then
        verify(songRepository, atLeastOnce()).searchWith(any())
        verify(songRepository).getSong(eq(dummySong2.trackId))
        onView(withId(R.id.content_view)).check(matches(isDisplayed()))
    }

    private fun launchAndSearch() {
        activityRule.launchActivity(null)
        onView(withId(R.id.search))
            .perform(click())
        onView(isAssignableFrom(AutoCompleteTextView::class.java))
            .perform(typeText("something"))
            .perform(pressKey(KeyEvent.KEYCODE_ENTER))
    }

}
