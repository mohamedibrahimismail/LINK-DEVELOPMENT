package  com.example.link.utils

import android.annotation.SuppressLint
import android.os.Build
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import com.bumptech.glide.Glide
import com.example.link.R
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter


fun ImageView?.loadImage(url: String?) {
    this?.context?.let {
        Glide.with(it)
            .load(url)
            .placeholder(R.drawable.laoding)
            .error(R.drawable.placeholder)
            .into(this)
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("SimpleDateFormat")
fun TextView.convertToDate(date_: String) {
    val desiredFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("MMMM dd, yyyy")
    val dateTime: OffsetDateTime = OffsetDateTime.parse(date_)
    val presentationDateTimeString: String = dateTime.format(desiredFormatter)
    this.text = presentationDateTimeString
}

