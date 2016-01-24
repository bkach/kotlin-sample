package app.com.boris.android.randomgif

import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    var vidView : CustomVideoView? = null
    var progressBar : ProgressBar? = null
    val gifFetcher : GifFetcher = GifFetcher()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        vidView = findViewById(R.id.vid_view) as CustomVideoView
        progressBar = findViewById(R.id.progress_bar) as ProgressBar
        vidView?.setOnTouchListener(defaultTouchListener())
        setupProgressBar();
        fetchAndDisplayGifs()
    }

    private fun setupProgressBar() {
        progressBar?.isIndeterminate = true;
        progressBar?.visibility = View.GONE;
    }

    private fun fetchAndDisplayGifs() {
        gifFetcher.onVideosPreparedListener { videos ->

            vidView?.setOnTouchListener(getNextVideoTouchListener(videos))

            vidView?.setOnPreparedListener { mediaPlayer ->
                progressBar?.visibility = View.GONE
                vidView?.start();
                mediaPlayer.isLooping = true

                vidView?.setOnTouchListener(getNextVideoTouchListener(videos));

            };
        }
    }

    private fun getNextVideoTouchListener(videos: List<GifFetcher.Video>) : View.OnTouchListener {
        return View.OnTouchListener { view, motionEvent ->
            if (motionEvent.action == MotionEvent.ACTION_DOWN) {
                vidView?.setOnTouchListener(defaultTouchListener());
                if (GifFetcher.currentGif == videos.size - 1) {
                    Toast.makeText(this,"no more gifs",Toast.LENGTH_SHORT).show();
                } else {
                    progressBar?.visibility = View.VISIBLE
                    GifFetcher.currentGif = GifFetcher.currentGif + 1
                    vidView?.stopPlayback()
                    vidView?.setVideoSize(videos[GifFetcher.currentGif].width,
                            videos[GifFetcher.currentGif].height)
                    vidView?.setVideoURI(Uri.parse(videos[GifFetcher.currentGif].gfyUrl))
                }
            }
            super.onTouchEvent(motionEvent)
        }
    }

    private fun defaultTouchListener() : View.OnTouchListener {
        return View.OnTouchListener { v, motionEvent ->
            false }
    }
}
