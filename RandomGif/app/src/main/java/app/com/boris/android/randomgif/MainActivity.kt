package app.com.boris.android.randomgif

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import app.com.boris.android.randomgif.networking.GifData

class MainActivity : AppCompatActivity() {

    var data = GifData()
    var recyclerView : RecyclerView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupRecyclerView()
        getGifData()
    }

    private fun getGifData() {
        data.getData {
            recyclerView?.adapter = RecyclerViewAdapter(data)
            recyclerView?.setOnScrollListener(CustomScrollListener())
        };
    }

    private fun setupRecyclerView() {
        recyclerView = findViewById(R.id.recycler_view) as RecyclerView
        recyclerView?.setHasFixedSize(false)
        recyclerView?.layoutManager = LinearLayoutManager(this)
    }

    class CustomScrollListener : RecyclerView.OnScrollListener() {

        var data = GifData()

        override fun onScrollStateChanged(recyclerView: RecyclerView?, newState: Int) {
            if (newState == RecyclerView.SCROLL_STATE_DRAGGING) {
                val lastVisiblePosition =
                        ((recyclerView?.layoutManager) as LinearLayoutManager).findLastCompletelyVisibleItemPosition()
                val listSize = GifData.gifs.size - 1
                if (lastVisiblePosition == listSize) {
                    data.getData {
                        recyclerView?.adapter?.notifyItemRangeChanged(0, GifData.gifs.size)
                    }
                }
            }
        }
    }
}
