package app.com.example.android.kotlin

import android.content.Context
import android.graphics.Typeface
import android.view.Gravity
import org.jetbrains.anko.*

/**
 * Created by bkach on 10/30/15.
 */
class CustomLinearLayout : _LinearLayout, AnkoLogger {

    constructor(context: Context) : super(context) {

        linearLayout {
            lparams {
                width = matchParent
                height = matchParent
            }
            textView {
                id = R.id.detailView
                gravity = Gravity.CENTER_HORIZONTAL + Gravity.CENTER_VERTICAL
                typeface = Typeface.DEFAULT_BOLD
                textSize = 20f;
            }
        }
    }
}
