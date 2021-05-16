package  com.example.link.utils

import android.content.Context
import android.net.ConnectivityManager
import java.lang.Error

object NetworkUtils {

    fun isNetworkConnected(context: Context): Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = cm.activeNetworkInfo
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting
    }

    enum class RESPONSE_ERROR {
        EXCEED_LIMIT, OTHER
    }

}

data class ResponseApiErrorModel(var respose_error: NetworkUtils.RESPONSE_ERROR,var message:String)