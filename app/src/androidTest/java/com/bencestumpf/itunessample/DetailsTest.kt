package com.bencestumpf.itunessample

import android.app.Activity
import android.app.Instrumentation
import android.content.Intent
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.intent.Intents
import android.support.test.espresso.intent.Intents.intended
import android.support.test.espresso.intent.Intents.intending
import android.support.test.espresso.intent.matcher.IntentMatchers.*
import android.support.test.espresso.matcher.ViewMatchers.withId
import android.support.test.espresso.matcher.ViewMatchers.withText
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import com.bencestumpf.itunessample.domain.model.Song
import com.bencestumpf.itunessample.domain.repositories.SongRepository
import com.bencestumpf.itunessample.helper.espressoDaggerMockRule
import com.bencestumpf.itunessample.presentation.details.DetailsActivity
import com.bencestumpf.itunessample.presentation.details.DetailsActivity.Companion.EXTRA_SONG_ID
import com.nhaarman.mockito_kotlin.eq
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import io.reactivex.Single
import org.hamcrest.CoreMatchers.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.`when`
import java.util.*

@RunWith(AndroidJUnit4::class)
class DetailsTest {

    private val songRepository: SongRepository = mock()

    @get:Rule
    val rule = espressoDaggerMockRule()

    @get:Rule
    val activityRule = ActivityTestRule(DetailsActivity::class.java, false, false)

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

    private lateinit var intent: Intent

    @Before
    fun setUp() {
        `when`(songRepository.getSong(eq(dummySong.trackId))).thenReturn(Single.just(dummySong))

        intent = Intent().apply {
            putExtra(EXTRA_SONG_ID, dummySong.trackId)
        }
    }

    @Test
    fun songDetailsShows() {
        //Given

        //When
        activityRule.launchActivity(intent)

        //Then

        onView(withId(R.id.song_details_title)).check(matches(withText(dummySong.title)))
        onView(withId(R.id.song_details_artist)).check(matches(withText(dummySong.artistName)))
        onView(withId(R.id.song_details_album)).check(matches(withText(dummySong.album)))
    }

    @Test
    fun skipToNextWorks() {
        //Given
        `when`(songRepository.getNextSong(eq(dummySong.trackId))).thenReturn(Single.just(dummySong2))

        //When
        activityRule.launchActivity(intent)

        //Then
        onView(withId(R.id.song_details_title)).check(matches(withText(dummySong.title)))

        //When
        onView(withId(R.id.song_details_next)).perform(click())

        //Then
        onView(withId(R.id.song_details_title)).check(matches(withText(dummySong2.title)))
        verify(songRepository).getNextSong(eq(dummySong.trackId))
    }

    @Test
    fun skipToPreviousWorks() {
        //Given
        `when`(songRepository.getPreviousSong(eq(dummySong.trackId))).thenReturn(Single.just(dummySong2))

        //When
        activityRule.launchActivity(intent)

        //Then
        onView(withId(R.id.song_details_title)).check(matches(withText(dummySong.title)))

        //When
        onView(withId(R.id.song_details_previous)).perform(click())

        //Then
        onView(withId(R.id.song_details_title)).check(matches(withText(dummySong2.title)))
        verify(songRepository).getPreviousSong(eq(dummySong.trackId))
    }

    @Test
    fun shareCurrentSongInvokesChooserWithSendAction() {
        //Given

        //When
        activityRule.launchActivity(intent)
        val expectedIntent = allOf(
            hasAction(Intent.ACTION_CHOOSER),
            hasExtra(
                `is`(Intent.EXTRA_INTENT),
                allOf(
                    hasAction(Intent.ACTION_SEND),
                    hasExtra(
                        Intent.EXTRA_TEXT,
                        activityRule.activity.baseContext.getString(
                            R.string.share_message,
                            dummySong.artistName,
                            dummySong.title
                        )
                    )
                )
            )
        )

        Intents.init()
        intending(not(isInternal())).respondWith(Instrumentation.ActivityResult(Activity.RESULT_OK, null))
        onView(withId(R.id.song_details_share)).perform(click())

        //Then
        intended(expectedIntent)
        Intents.release()
    }
}