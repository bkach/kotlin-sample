package app.com.boris.android.randomgif

import app.com.boris.android.randomgif.retrofit.GfycatRetrofitInterface
import app.com.boris.android.randomgif.retrofit.RedditRetrofitInterface
import rx.Observable
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import java.net.URL

/**
 * Created by bkach on 12/12/15.
 */
class GifFetcher(onLoadMp4Urls: (videos : List<GifFetcher.Video>) -> Unit) {

    val retrofit = RedditRetrofitInterface.create()
    val gfycat = GfycatRetrofitInterface.create()
    var videos : List<Video> = arrayListOf();

    companion object {
        var currentGif = 0
    }

    init {
        getURLList(onLoadMp4Urls)
    }

    private fun getURLList(callback: (videos : List<Video>) -> Unit) {
        retrofit.getRedditGifsFrontPage(5)
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.io())
            .flatMap { redditResponse ->

                var videos: MutableList<Video> = arrayListOf();

                redditResponse.data.children.forEach { child ->
                    var url = URL(child.data.url)
                    var width = child.data.media_embed.width
                    var height = child.data.media_embed.height
                    var fullPath = "${url.host + url.path}"
                    if (url.host.contains("imgur")) {
                        val id = fullPath.substring(
                                fullPath.indexOf(".com/") + 5,
                                fullPath.indexOf(".gif")
                        )
                        videos.add(Video("i.imgur.com/$id.gif", width, height))
                    }
                }

                Observable.just(videos.toList());
            }
            .subscribe({ videos ->
                videos.map { video ->
                    gfycat.getGfycatConversionURL(video.url)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe({ gfycatUrl -> video.url = gfycatUrl.mp4Url},
                                   { error -> error.printStackTrace() })
                }
                this.videos = videos;
            }, { error -> error.printStackTrace() }, {
                callback(videos)
            })
    }

    class Video(var url: String, val width: Int, val height: Int){}
}