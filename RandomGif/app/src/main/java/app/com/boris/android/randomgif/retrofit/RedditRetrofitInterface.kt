package app.com.boris.android.randomgif.retrofit

import app.com.boris.android.randomgif.response.RedditResponse
import com.squareup.okhttp.OkHttpClient
import com.squareup.okhttp.logging.HttpLoggingInterceptor
import retrofit.GsonConverterFactory
import retrofit.Retrofit
import retrofit.RxJavaCallAdapterFactory
import retrofit.http.GET
import retrofit.http.Query
import rx.Observable
import java.util.logging.Level

/**
 * Created by bkach on 12/12/15.
 */
interface RedditRetrofitInterface {

    @GET("gifs.json")
    fun getRedditGifsFrontPage(@Query("limit") limit : Int): Observable<RedditResponse>


    companion object {
        fun create() : RedditRetrofitInterface {
            val logging = HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);

            val httpClient = OkHttpClient()
            httpClient.interceptors().add(logging);

            val retrofit = Retrofit.Builder()
                    .baseUrl("https://www.reddit.com/r/")
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(httpClient)
                    .build()

            return retrofit.create(RedditRetrofitInterface::class.java)
        }
    }
}