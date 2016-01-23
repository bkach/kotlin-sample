package app.com.boris.android.randomgif.retrofit

import app.com.boris.android.randomgif.response.GfycatResponse
import com.squareup.okhttp.OkHttpClient
import com.squareup.okhttp.logging.HttpLoggingInterceptor
import retrofit.GsonConverterFactory
import retrofit.Retrofit
import retrofit.RxJavaCallAdapterFactory
import retrofit.http.GET
import retrofit.http.Query
import rx.Observable

/**
 * Created by bkach on 12/12/15.
 */
interface GfycatRetrofitInterface{

    @GET("transcode")
    fun getGfycatConversionURL(@Query("fetchUrl") url: String): Observable<GfycatResponse>


    companion object {
        fun create() : GfycatRetrofitInterface {

            val logging = HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);

            val httpClient = OkHttpClient()
            httpClient.interceptors().add(logging);

            val restAdapter = Retrofit.Builder()
                    .baseUrl("http://upload.gfycat.com/")
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(httpClient)
                    .build()

            return restAdapter.create(GfycatRetrofitInterface::class.java)
        }
    }
}