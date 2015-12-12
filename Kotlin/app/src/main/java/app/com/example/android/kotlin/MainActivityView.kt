package app.com.example.android.kotlin

import android.content.Context
import android.widget.LinearLayout
import org.jetbrains.anko.*

/**
 * Created by bkach on 10/30/15.
 */
class MainActivityView : LinearLayout, AnkoLogger {

    private var ctx: Context;

    fun <T> startActivityWithExtra(extra: T) {
        when (extra) {
            is Int -> ctx.startActivity<DetailActivity>(DetailActivity.extraKey() to extra)
            is String -> ctx.startActivity<DetailActivity>(DetailActivity.extraKey() to extra)
        }
    }

    constructor(context: Context) : super(context) {
        ctx = context

        verticalLayout {

            lparams {
                width = matchParent
                height = matchParent
            }

            weightSum = 1.0f;

            button() {
                textResource = R.string.app_name
                textSize = 20f
                onClick {
                    startActivityWithExtra("Detail")
                }
            }.lparams(width = matchParent, height = 0) { weight = 0.5f }

            frameLayout {

                linearLayout {

                    orientation = LinearLayout.HORIZONTAL;
                    weightSum = 1.0f

                    button("2") {
                        textSize = 20f
                        onClick {
                            startActivityWithExtra(2)
                        }
                    }.lparams(width = 0, height = matchParent){
                        weight = 0.5f
                    }

                    button("Detail3") {
                        textSize = 20f
                        onClick {
                            startActivityWithExtra("Detail3")
                        }
                    }.lparams(width = context.sp(0), height = matchParent){
                        weight = 0.5f
                    }

                }

            }.lparams(width = matchParent, height = 0) { weight = 0.5f }
        }
    }
}

