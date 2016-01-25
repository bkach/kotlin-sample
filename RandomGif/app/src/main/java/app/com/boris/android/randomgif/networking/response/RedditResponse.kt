package app.com.boris.android.randomgif.networking.response

/**
 * Created by bkach on 12/12/15.
 */
class RedditResponse(val data : RedditResponse.Data) {
    class Data(val children: List<Data.Child>) {
        class Child(val data : Child.Data) {

            class Data(val url : String, val media_embed : Data.MediaEmbed, val name: String, val media: Data.Media?) {
                class MediaEmbed(val width : Int, val height : Int){}
                class Media(val oembed: Media.Thumbnail) {
                    class Thumbnail(val thumbnail_width: Int, val thumbnail_url: String, val thumbnail_height: Int) {}
                }
            }

        }
    }
}