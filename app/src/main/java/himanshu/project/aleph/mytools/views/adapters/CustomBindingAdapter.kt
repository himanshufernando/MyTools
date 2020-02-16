package himanshu.project.aleph.mytools.views.adapters

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.squareup.picasso.Picasso

object CustomBindingAdapter {
    @BindingAdapter("toolImageUrl")
    @JvmStatic
    fun loadImage(view: ImageView, url: String) {
        Picasso.get().load(url).into(view)
    }
}