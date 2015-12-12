package app.com.boris.android.randomgif.RetrofitInterface

import app.com.boris.android.randomgif.JsonResponse.GfycatResponse
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

    @GET("/transcode")
    fun getGfycatConversionURL(@Query("fetchUrl") url: String): Observable<GfycatResponse>


    companion object {
        fun create() : GfycatRetrofitInterface {

            val restAdapter = Retrofit.Builder()
                    .baseUrl("http://upload.gfycat.com")
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()

            return restAdapter.create(GfycatRetrofitInterface::class.java)
        }
    }
}