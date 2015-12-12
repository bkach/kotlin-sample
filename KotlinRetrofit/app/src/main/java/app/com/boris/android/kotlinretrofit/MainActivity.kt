package app.com.boris.android.kotlinretrofit

import android.app.Activity
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.widget.TextView
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

class MainActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val fab = findViewById(R.id.fab) as FloatingActionButton
        val tv = findViewById(R.id.text) as TextView

        val obs  = RetrofitInterface.create().get("square")
        obs.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe( {
                tv.text = it.fold("REPO LIST:", {last, current -> last + "\n" + current.name});
            }, {tv.text = it.message} )

        fab.setOnClickListener { tv.text = "Pressed" }
    }
}
