package io.betterapps.graysky.ui.image

import android.widget.ImageView
import com.squareup.picasso.Picasso
import io.betterapps.graysky.R

/**
 * Facade to any image libraries (Picasso, Fresco, Glide), so it is easier to change to another library.
 */
object ImageLoader {

    fun load(imageView: ImageView, url: String) {
        Picasso.get()
            .load(url)
            .error(R.drawable.ic_launcher_foreground)
            .fit()
            .into(imageView)
    }
}
