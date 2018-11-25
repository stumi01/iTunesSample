package com.bencestumpf.itunessample.presentation.search

import android.content.Context
import android.widget.ImageView
import android.widget.TextView
import com.bencestumpf.itunessample.R
import com.bencestumpf.itunessample.domain.model.Song
import com.bumptech.glide.Glide
import com.hannesdorfmann.annotatedadapter.annotation.ViewField
import com.hannesdorfmann.annotatedadapter.annotation.ViewType
import com.hannesdorfmann.annotatedadapter.support.recyclerview.SupportAnnotatedAdapter
import java.text.SimpleDateFormat

class SongsAdapter(private val context: Context, private val onSongClick: (Long) -> Unit) :
    SupportAnnotatedAdapter(context),
    SongsAdapterBinder {
    private val songs: ArrayList<Song> = ArrayList()

    @JvmField
    @ViewType(
        layout = R.layout.view_song_row,
        views = [ViewField(id = R.id.song_title, name = "title", type = TextView::class),
            ViewField(id = R.id.song_artist, name = "artist", type = TextView::class),
            ViewField(id = R.id.song_genre, name = "genre", type = TextView::class),
            ViewField(id = R.id.song_album, name = "album", type = TextView::class),
            ViewField(id = R.id.song_price, name = "price", type = TextView::class),
            ViewField(id = R.id.song_length, name = "length", type = TextView::class),
            ViewField(id = R.id.song_release_date, name = "releaseDate", type = TextView::class),
            ViewField(id = R.id.song_image, name = "image", type = ImageView::class)
        ]
    )
    val songRow: Int = 0

    private val dateFormat = SimpleDateFormat("yyyy-MM-dd")

    override fun getItemCount(): Int {
        return songs.size
    }

    override fun bindViewHolder(vh: SongsAdapterHolders.SongRowViewHolder?, position: Int) {
        vh?.let {
            val song = songs[position]
            it.title?.text = song.title
            it.length?.text = song.trackTimeMillis?.toReadable()
            it.artist?.text = song.artistName
            it.album?.text = song.album
            it.genre?.text = song.genre
            it.releaseDate?.text = dateFormat.format(song.releaseDate)
            it.price?.text = context.getString(R.string.price_with_holders, song.trackPrice, song.currency)


            Glide.with(it.image)
                .load(song.artworkThumbnailUrl)
                .into(it.image)

            it.itemView?.setOnClickListener { onSongClick.invoke(song.trackId) }
        }
    }

    fun setData(newData: List<Song>) {
        songs.clear()
        songs.addAll(newData)
        notifyDataSetChanged()
    }

}

private fun Long.toReadable(): String {
    val minutes = this / 1000 / 60
    val seconds = this / 1000 % 60
    return "$minutes:${addLeadingZeroIfNecessary(seconds)}"
}

fun addLeadingZeroIfNecessary(seconds: Long): String {
    return if (seconds < 10) "0$seconds" else seconds.toString()
}
