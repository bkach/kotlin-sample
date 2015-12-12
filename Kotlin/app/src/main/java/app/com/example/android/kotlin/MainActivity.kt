package app.com.example.android.kotlin

import android.app.Activity
import android.os.Bundle
import android.view.ViewManager
import org.jetbrains.anko.*
import org.jetbrains.anko.custom.ankoView

class MainActivity : Activity() {

    public inline fun ViewManager.MainActivityView(init: MainActivityView.() -> Unit): MainActivityView {
        return ankoView({ MainActivityView(it) }, init)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        var frame = frameLayout {
            var view = MainActivityView() {}
        }
    }

}
