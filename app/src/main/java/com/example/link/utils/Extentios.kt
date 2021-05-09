package  com.example.link.utils

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.TypedValue
import android.widget.ImageView
import androidx.annotation.AttrRes
import androidx.annotation.DrawableRes
import androidx.core.graphics.drawable.DrawableCompat
import androidx.vectordrawable.graphics.drawable.VectorDrawableCompat
import com.bumptech.glide.Glide
import com.example.link.R
import com.google.gson.JsonObject


fun ImageView?.loadImage(url: String?) {
    this?.context?.let {
        Glide.with(it)
            .load(url)
            .placeholder(R.drawable.laoding)
            .error(R.drawable.placeholder)
            .into(this)
    }
}



