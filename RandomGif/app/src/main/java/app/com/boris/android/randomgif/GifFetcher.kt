package app.com.boris.android.randomgif

import app.com.boris.android.randomgif.RetrofitInterface.GfycatRetrofitInterface
import app.com.boris.android.randomgif.RetrofitInterface.RedditRetrofitInterface
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import java.net.URL

/**
 * Created by bkach on 12/12/15.
 */
class GifFetcher(onLoadMp4Urls: (mp4Urls : MutableList<String>) -> Unit) {

    val retrofit = RedditRetrofitInterface.create()
    val gfycat = GfycatRetrofitInterface.create()

    init {
        getURLList(onLoadMp4Urls)
    }

    private fun getURLList(callback: (mp4Urls : MutableList<String>) -> Unit) {
        retrofit.getRedditGifsFrontPage()
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.io())
            .map { // Get Gif List
                it.data.children.mapNotNull { child ->
                    var url = URL(child.data.url)
                    var fullPath = "${url.host + url.path}"
                    val id : String;
                    if (url.host.contains("imgur")) {
                        id = fullPath.substring(
                                fullPath.indexOf(".com/") + 5,
                                fullPath.indexOf(".gif")
                        )
                        "i.imgur.com/$id.gif"
                    }
                    else null
                }
            }
            .subscribe({ urls ->
                var mp4urls: MutableList<String> = arrayListOf();
                urls.forEach {
                    gfycat.getGfycatConversionURL(it)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({
                        mp4urls.add(it.mp4Url)
                        if (mp4urls.size.equals(urls.size)) {
                            callback(mp4urls)
                        }
                },{ error -> error.printStackTrace() })
            }
            }, { error -> error.printStackTrace() })
    }
}