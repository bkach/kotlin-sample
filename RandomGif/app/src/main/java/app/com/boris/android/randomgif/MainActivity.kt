package app.com.boris.android.randomgif

import android.media.MediaPlayer
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.MotionEvent
import android.widget.TextView
import android.widget.VideoView

class MainActivity : AppCompatActivity() {

    var currentGif : Int = 0;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var gifFetcher : GifFetcher = GifFetcher { mp4urls ->
            val vidView = findViewById(R.id.vid_view) as VideoView

            vidView.setVideoURI(Uri.parse(mp4urls[currentGif]))
            vidView.setOnPreparedListener { mediaPlayer ->
                vidView.start()
                mediaPlayer.isLooping = true
            };

            vidView.setOnTouchListener { view, motionEvent ->
                if (motionEvent.action == MotionEvent.ACTION_DOWN) {
                    currentGif = if (currentGif == mp4urls.size - 1) 0 else currentGif + 1
                    vidView.stopPlayback()
                    vidView.setVideoURI(Uri.parse(mp4urls[currentGif]))
                    vidView.start();
                }
                super.onTouchEvent(motionEvent)
            }
        }
    }
}
