package app.com.boris.android.randomgif.networking

import app.com.boris.android.randomgif.networking.response.GfycatResponse
import app.com.boris.android.randomgif.networking.response.RedditResponse
import app.com.boris.android.randomgif.networking.retrofit.GfycatRetrofitInterface
import app.com.boris.android.randomgif.networking.retrofit.RedditRetrofitInterface
import rx.Observable
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import java.net.URL
import java.util.concurrent.Semaphore

/**
 * Created by bkach on 1/24/16.
 */

class GifData() {

    companion object {
        var currentlyRequesting = false
        val gifs: MutableList<Gif> = arrayListOf();
    }

    val reddit = RedditRetrofitInterface.create()
    val gfycat = GfycatRetrofitInterface.create()
    val NUM_RESULTS = 10;

    fun getData(callback: () -> Unit) {
        var redditObservable : Observable<RedditResponse>
        if (gifs.size == 0) {
            redditObservable = this.reddit.getGifsFrontPage(NUM_RESULTS)
        } else {
            redditObservable = this.reddit.getGifsFrontPage(NUM_RESULTS, gifs[gifs.size-1].id)
            print(gifs[gifs.size-1].id)
        }
            redditObservable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap {
                    var gfycatObservables = parseRedditResponse(it)
                    Observable.merge(gfycatObservables)
                }
                .subscribe( { gif ->
                    gifs.add(gif)               // Success
                }, { error ->
                    error.printStackTrace()     // Failure
                }, {
                    callback();                 // Completed
                })
    }

    fun getGif(position: Int, callback: (Gif) -> Unit) {
        callback(gifs[position])
    }

    private fun parseRedditResponse(redditResponse: RedditResponse): List<Observable<Gif>> {
        var gfycatObservables: MutableList<Observable<Gif>> = arrayListOf()

        redditResponse.data.children.forEach { child ->
            if (child.data.media != null) {
                val width = child.data.media_embed.width
                val height = child.data.media_embed.height
                val gifId = child.data.id
                val thumbnailURL = child.data.media.oembed.thumbnail_url
                val thumbnailWidth = child.data.media.oembed.thumbnail_width
                val thumbnailHeight = child.data.media.oembed.thumbnail_height

                val imgurURL = URL(child.data.url)
                if (imgurURL.host.contains("imgur")) {
                    val imgurID = getImgurIDFromURL("${imgurURL.host + imgurURL.path}")
                    val gifURL = "i.imgur.com/$imgurID.gif"
                    gfycatObservables.add(
                        gfycat.getGfycatConversionURL(gifURL)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .map { gfycatResponse ->
                                Gif(Gif.Thumbnail(thumbnailURL, thumbnailWidth, thumbnailHeight),
                                    gfycatResponse.mp4Url, gifId, width, height)
                            }
                    )
                }
            }
        }
        return gfycatObservables.toList();
    }

    private fun getImgurIDFromURL(path: String): Any {
        return path.substring(
                path.indexOf(".com/") + 5,
                path.indexOf(".gif")
        )
    }

    data class Gif(val thumbnail: Gif.Thumbnail,
                   val videoURL: String,
                   val id: String,
                   val width: Int,
                   val height: Int) {
        data class Thumbnail(val url: String, val width: Int, val height: Int)
    }

    data class IndexedCallback(val position: Int, val callback: (Gif) -> Unit)
}
