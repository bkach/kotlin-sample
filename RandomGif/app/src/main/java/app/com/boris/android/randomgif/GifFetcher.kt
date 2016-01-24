package app.com.boris.android.randomgif

import app.com.boris.android.randomgif.networking.response.RedditResponse
import app.com.boris.android.randomgif.networking.retrofit.GfycatRetrofitInterface
import app.com.boris.android.randomgif.networking.retrofit.RedditRetrofitInterface
import rx.Observable
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import java.net.URL

/**
 * Created by bkach on 12/12/15.
 */
class GifFetcher() {

    val retrofit = RedditRetrofitInterface.create()
    val gfycat = GfycatRetrofitInterface.create()
    var videos : List<Video> = arrayListOf();

    companion object {
        var currentGif = 0
    }

    public fun onVideosPreparedListener(callback: (videos : List<Video>) -> Unit){
        getVideos(callback);
    }

    private fun getVideos(callback: (videos : List<Video>) -> Unit) {
        retrofit.getGifsFrontPage(20)
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.io())
            .flatMap { redditResponse ->
                Observable.just(
                    redditResponseToVideos(redditResponse)
                ) }
            .subscribe({ videos -> addGfycatURL(videos, callback) },
                       { error -> error.printStackTrace() })
    }

    private fun addGfycatURL(videos: List<GifFetcher.Video>,
                             callback: (videos: List<Video>) -> Unit) {
        videos.map { video ->
            gfycat.getGfycatConversionURL(video.imgurUrl)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({ gfycatUrl ->
                        video.gfyUrl = gfycatUrl.mp4Url
                        if (Video.numLoadedVideos == videos.size) {
                            callback(videos);
                        }
                    },
                            { error -> error.printStackTrace() })
        }
        this.videos = videos;
    }

    private fun redditResponseToVideos(response: RedditResponse) : List<Video> {
        var videos: MutableList<Video> = arrayListOf()
        response.data.children.forEach { child : RedditResponse.Data.Child ->
            val url = URL(child.data.url)
            val width = child.data.media_embed.width
            val height = child.data.media_embed.height
            val gifId = child.data.id

            if (url.host.contains("imgur")) {
                val imgurID = getImgurIDFromURL("${url.host + url.path}")
                videos.add(Video("i.imgur.com/$imgurID.gif", gifId, width, height))
            }
        }
        return videos.toList()
    }

    private fun getImgurIDFromURL(path: String): Any {
        return path.substring(
                path.indexOf(".com/") + 5,
                path.indexOf(".gif")
        )
    }

    class Video(var imgurUrl: String, val id: String, val width: Int, val height: Int){

        companion object {
            var numLoadedVideos: Int = 0;
        }

        var gfyUrl: String = ""
            set(value) {
                field = value;
                numLoadedVideos++
            }

    }
}