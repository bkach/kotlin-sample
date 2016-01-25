package app.com.boris.android.randomgif

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.VideoView

/**
 * Created by bkach on 1/23/16.
 */
class CustomVideoView : VideoView {

    private var videoWidth: Int = 0
    private var videoHeight: Int = 0

    constructor(context: Context) : super(context) {
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
    }

    fun setVideoSize(width: Int, height: Int) {
        videoWidth = width
        videoHeight = height
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        var width = View.getDefaultSize(this.videoWidth, widthMeasureSpec)
        var height = View.getDefaultSize(this.videoHeight, heightMeasureSpec)
        if (this.videoWidth > 0 && this.videoHeight > 0) {
            if (this.videoWidth * height > width * this.videoHeight) {
                height = width * this.videoHeight / this.videoWidth
            } else if (this.videoWidth * height < width * this.videoHeight) {
                width = height * this.videoWidth / this.videoHeight
            }
        }
        setMeasuredDimension(width, height)
    }
}
