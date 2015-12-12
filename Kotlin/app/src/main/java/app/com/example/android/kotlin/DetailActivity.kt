package app.com.example.android.kotlin

import android.app.Activity
import android.os.Bundle
import android.view.ViewManager
import android.widget.TextView
import org.jetbrains.anko.*
import org.jetbrains.anko.custom.ankoView

class DetailActivity : Activity() {

    companion object {

        val extra1: String = "extra1";

        fun extraKey() : String {
            return extra1;
        }

    }

    public inline fun ViewManager.CustomLinearLayout(init: CustomLinearLayout.() -> Unit): CustomLinearLayout {
        return ankoView({ CustomLinearLayout(it) }, init)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        var fl = frameLayout {
            var cll = CustomLinearLayout() {}
        }


        var textView = find<TextView>(R.id.detailView)
        textView textShouldBe (intent.getStringExtra(extraKey()) ?:  "${intent.getIntExtra(extraKey(), 1)}")
        textView += " pressed"

    }
}

infix fun TextView.textShouldBe(s: String) {
    text = s
}

operator fun TextView.plusAssign(s: String) {
    text = text.toString() + s
}
