package  com.example.link.network.retrofit

import android.util.Log
import com.example.link.App
import com.example.link.model.GenericResponse
import com.google.gson.Gson
import io.reactivex.observers.DisposableObserver
import okhttp3.ResponseBody
import retrofit2.HttpException
import java.io.IOException
import java.net.SocketTimeoutException

/**
 * Hero is the CallBackWrapper abstract class used to wrap all the api calls response
 * and parse the error response and handle response status codes
 * @author Mohamed Ibrahim
 * @param T Generic response of API call
 */
abstract class CallbackWrapper<T : Any> : DisposableObserver<T>() {

    protected abstract fun onSuccess(t: T)
    protected abstract fun onFail(t: String?)
    protected abstract fun onFail(t: Map<String, ArrayList<String>>)

    override fun onNext(t: T) {
        try {
            onSuccess(t)
        } catch (e: Exception) {
            if (e is HttpException) {
                val responseBody = e.response().errorBody()
                onFail(getErrorMessage(responseBody!!)!!)
            } else {
                Log.e("ERRORS", e.message.toString())
            }
        }
    }

    override fun onError(e: Throwable) {
        if (e is HttpException) {
            when {
                e.code() == 401 -> {
                    val responseBody = e.response().errorBody()
                    responseBody?.let { it ->
                        getSingleErrorMessage(it)?.let {
                            if (it != "Invalid Email or Password") {
                                App.isNotAuth.value = true
                            } else {
                                onFail(it)
                            }
                        }
                    }
                }
                e.code() == 403 -> {
//                    App.isNotAuth.value = true
                    val responseBody = e.response().errorBody()
                    responseBody?.let { getSingleErrorMessage(it)?.let { onFail(it) } }
                }
                e is SocketTimeoutException -> {
                    val responseBody = e.response().errorBody()
                    responseBody?.let { getSingleErrorMessage(it)?.let { onFail(it) } }
                }
                e is IOException -> {
                    val responseBody = e.response().errorBody()
                    responseBody?.let { getSingleErrorMessage(it)?.let { onFail(it) } }
                }
                e.code() == 404 -> {
                    val responseBody = e.response().errorBody()
                    responseBody?.let { getSingleErrorMessage(it)?.let { onFail(it) } }
                }
                else -> {
                    val responseBody = e.response().errorBody()
                    responseBody?.let { getErrorMessage(it)?.let { onFail(it) } }
                }
            }
        }
    }

    override fun onComplete() {

    }

    /**
     * Function used to parse list of errors as HashMap<String, ArrayList<String>>
     * @author Mohamed Ibrahim
     * @param responseBody response of API call
     */
    private fun getErrorMessage(responseBody: ResponseBody): String? {
        var response = String(responseBody.string().toByteArray())
        val error = Gson().fromJson(response, GenericResponse::class.java)
        return error.status
    }

    /**
     * Function used to parse single string error message
     * @author Mohamed Ibrahim
     * @param responseBody response of API call
     */
    private fun getSingleErrorMessage(responseBody: ResponseBody): String? {
        var response: String = String(responseBody.string().toByteArray())
        val error = Gson().fromJson(response, GenericResponse::class.java)
        return error.status
    }

}