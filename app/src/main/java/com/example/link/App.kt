package com.example.link

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.androidnetworking.interceptors.HttpLoggingInterceptor
import com.example.link.di.appModule
import com.example.link.network.retrofit.*
import com.example.link.utils.AppConstants.BASE_URL
import io.github.inflationx.calligraphy3.CalligraphyConfig
import io.github.inflationx.calligraphy3.CalligraphyInterceptor
import io.github.inflationx.viewpump.ViewPump
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import org.koin.android.ext.android.inject
import org.koin.android.ext.android.startKoin
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory


/**
 * App class used as the .name Application class and contains the singleton retrofit api client call
 * @author Mohamed Ibrahim
 */
class App : Application() {


    override fun onCreate() {
        super.onCreate()
        createApi(null,  APIContentInterceptor())
        //initCalligraphyConfig()
        startKoin(this, listOf(appModule))
        isNotAuth = MutableLiveData()
        isNotAuth.value = false

    }

    /**
     * initCalligraphyConfig used to set the application font path for the whole app (livvic font)
     * @author Mohamed Ibrahim
     */
    private fun initCalligraphyConfig() {
        ViewPump.init(
            ViewPump.builder()
                .addInterceptor(
                    CalligraphyInterceptor(
                        CalligraphyConfig.Builder()
                            .setDefaultFontPath("fonts/livvic.ttf")
                            .build()
                    )
                )
                .build()
        )
    }

    companion object {
        lateinit var getService: Service

        var chuckInterceptor: Interceptor? = null
        lateinit var isNotAuth: MutableLiveData<Boolean>

        internal fun createApi(
            apiInterceptur: APInterceptor?,
            contentInterceptor: APIContentInterceptor
        ) {
            val clientBuilder: OkHttpClient.Builder
            val client: OkHttpClient
            val interceptor = HttpLoggingInterceptor()
            interceptor.level = HttpLoggingInterceptor.Level.BODY

            clientBuilder = OkHttpClient.Builder()
                .addInterceptor(interceptor)


            chuckInterceptor?.let {
                clientBuilder.addInterceptor(
                    it
                )
            }


            contentInterceptor?.let {
                clientBuilder.addInterceptor(
                    contentInterceptor
                )
            }

            apiInterceptur?.let {
                clientBuilder.addInterceptor(
                    apiInterceptur
                )
            }


            client = clientBuilder.build()

            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            getService = retrofit.create(Service::class.java)
        }
    }
}
