package app.com.boris.android.randomgif;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.VideoView;

/**
 * Created by bkach on 1/23/16.
 */
public class CustomVideoView extends VideoView {

    private int videoWidth;
    private int videoHeight;

    public CustomVideoView(Context context) {
        super(context);
    }

    public CustomVideoView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomVideoView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setVideoSize(int width, int height) {
        videoWidth = width;
        videoHeight = height;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = getDefaultSize(this.videoWidth, widthMeasureSpec);
        int height = getDefaultSize(this.videoHeight, heightMeasureSpec);
        if (this.videoWidth > 0 && this.videoHeight > 0) {
            if (this.videoWidth * height > width * this.videoHeight) {
                height = width * this.videoHeight / this.videoWidth;
            } else if (this.videoWidth * height < width * this.videoHeight) {
                width = height * this.videoWidth / this.videoHeight;
            }
        }
        setMeasuredDimension(width, height);
    }
}
