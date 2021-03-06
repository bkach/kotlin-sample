package app.com.boris.android.randomgif

import android.net.Uri
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import app.com.boris.android.randomgif.networking.GifData
import com.squareup.picasso.Picasso

/**
 * Created by bkach on 1/24/16.
 */

class RecyclerViewAdapter(val data: GifData) : RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var videoView: CustomVideoView
        var imageView: ImageView

        init {
            this.videoView = itemView.findViewById(R.id.video_view) as CustomVideoView;
            this.imageView = itemView.findViewById(R.id.preview_image_view) as ImageView;
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.videoView?.visibility = View.INVISIBLE
        holder.imageView?.visibility = View.VISIBLE

        data.getGif(position, { gif ->
            Picasso.with(holder.imageView?.context)
                    .load(gif.thumbnail.url)
                    .into(holder.imageView)

            holder.videoView.setVideoSize(gif.width,gif.height)
            holder.imageView?.setOnClickListener{

                holder.videoView.visibility = View.VISIBLE
                holder.imageView.visibility = View.INVISIBLE

                data.getGifMP4URL(gif, { MP4URL ->
                    holder.videoView.setVideoURI(Uri.parse(MP4URL))
                    holder.videoView.setOnPreparedListener { mediaPlayer ->
                        holder.videoView.start()
                        mediaPlayer.isLooping = true

                    }
                })
            }
        })

    }

    override fun getItemCount(): Int {
        return GifData.gifs.size;
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder? {
        // create a new view
        val v: View = LayoutInflater.from(parent?.context)
                .inflate(R.layout.recycler_view_item, parent, false)
        val vh = ViewHolder(v)
        return vh
    }
}
