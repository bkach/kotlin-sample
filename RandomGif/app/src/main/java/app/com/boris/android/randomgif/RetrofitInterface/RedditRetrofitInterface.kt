package app.com.boris.android.randomgif.RetrofitInterface

import app.com.boris.android.randomgif.JsonResponse.RedditResponse
import com.google.gson.GsonBuilder
import retrofit.GsonConverterFactory
import retrofit.Retrofit
import retrofit.RxJavaCallAdapterFactory
import retrofit.http.GET
import rx.Observable

/**
 * Created by bkach on 12/12/15.
 */
interface RedditRetrofitInterface {

    @GET("/r/gifs.json?limit=500")
    fun getRedditGifsFrontPage(): Observable<RedditResponse>


    companion object {
        fun create() : RedditRetrofitInterface {

            val restAdapter = Retrofit.Builder()
                    .baseUrl("https://www.reddit.com")
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()

            return restAdapter.create(RedditRetrofitInterface::class.java)
        }
    }
}