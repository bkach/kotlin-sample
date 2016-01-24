package app.com.boris.android.randomgif.response

/**
 * Created by bkach on 12/12/15.
 */
class RedditResponse(val data : RedditResponse.Data) {
    class Data(val children: List<Data.Children>) {
        class Children(val data : Children.Data) {
            class Data(val url : String, val media_embed : Data.MediaEmbed, val id: String) {
                class MediaEmbed(val width : Int, val height : Int){

                }
            }

        }
    }
}