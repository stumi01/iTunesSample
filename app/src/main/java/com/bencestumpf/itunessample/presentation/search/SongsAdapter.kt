package com.bencestumpf.itunessample.presentation.search

import android.content.Context
import android.widget.TextView
import com.bencestumpf.itunessample.R
import com.bencestumpf.itunessample.domain.model.Song
import com.hannesdorfmann.annotatedadapter.annotation.ViewField
import com.hannesdorfmann.annotatedadapter.annotation.ViewType
import com.hannesdorfmann.annotatedadapter.support.recyclerview.SupportAnnotatedAdapter

class SongsAdapter(context: Context) : SupportAnnotatedAdapter(context), SongsAdapterBinder {
    private val songs: ArrayList<Song> = ArrayList()

    @JvmField
    @ViewType(
        layout = R.layout.view_song_row,
        views = [ViewField(id = R.id.song_title, name = "title", type = TextView::class),
            ViewField(id = R.id.song_artist, name = "artist", type = TextView::class),
            ViewField(id = R.id.song_details, name = "details", type = TextView::class),
            ViewField(id = R.id.song_album, name = "album", type = TextView::class)
        ]
    )
    val songRow: Int = 0

    override fun getItemCount(): Int {
        return songs.size
    }

    override fun bindViewHolder(vh: SongsAdapterHolders.SongRowViewHolder?, position: Int) {
        vh?.let {
            val song = songs[position]
            it.title.text = song.title
            it.artist.text = song.artist
            it.details.text = song.details
            it.album.text = song.album

        }
    }

    fun setData(newData: List<Song>) {
        songs.clear()
        songs.addAll(newData)
        notifyDataSetChanged()
    }

}