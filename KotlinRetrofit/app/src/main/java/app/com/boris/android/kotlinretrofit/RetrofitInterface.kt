package app.com.boris.android.kotlinretrofit

import app.com.boris.android.kotlinretrofit.Model.Result
import com.google.gson.GsonBuilder
import com.squareup.okhttp.ResponseBody
import retrofit.GsonConverterFactory
import retrofit.Retrofit
import retrofit.RxJavaCallAdapterFactory
import retrofit.http.GET
import retrofit.http.Path
import rx.Observable
import java.util.*

/**
 * Created by bkach on 11/24/15.
 */
interface RetrofitInterface {
    @GET("https://api.github.com/users/{username}/repos")
    fun get(@Path("username") username: String): Observable<ArrayList<Result>>

    companion object {
        fun create() : RetrofitInterface {
            val gsonBuilder = GsonBuilder()

            val restAdapter = Retrofit.Builder()
                    .baseUrl("https://api.github.com/users/bkach/repos")
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create(gsonBuilder.create()))
                    .build()



            return restAdapter.create(RetrofitInterface::class.java)
        }
    }
}