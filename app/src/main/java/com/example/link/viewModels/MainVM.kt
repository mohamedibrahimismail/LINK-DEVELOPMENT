package com.example.link.viewModels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.link.R
import com.example.link.model.GenericResponse
import com.example.link.model.main.ArticlesModelItem
import com.example.link.model.main.NavigationItemModel
import com.example.link.network.AppRepository
import com.example.link.utils.NetworkUtils
import com.example.link.utils.ResponseApiErrorModel
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.BiFunction
import io.reactivex.schedulers.Schedulers
import retrofit2.HttpException

private const val TAG = "MainVM"

class MainVM(private val repository: AppRepository) : BaseViewModel() {

    var AllArticles: MutableLiveData<MutableList<ArticlesModelItem>> = MutableLiveData()
    var ApiErrorHappened: MutableLiveData<NetworkUtils.RESPONSE_ERROR> = MutableLiveData()


    fun getAllArticles() {
        loading.value = true
        mCompositeDisposable.add(
            Observable.zip(repository.getAssociatedPress()
                , repository.getTheNextWeb(),
                BiFunction<GenericResponse<MutableList<ArticlesModelItem>>, GenericResponse<MutableList<ArticlesModelItem>>, MutableList<ArticlesModelItem>> { t1, t2 ->
                    val list: MutableList<ArticlesModelItem> = ArrayList()
                    list.addAll(t1.articles!!)
                    list.addAll(t2.articles!!)
                    list
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { result ->
                        loading.value = false
                        AllArticles.value = result
                    },
                    { error ->

                        if (error is HttpException) {

                            val responseCode = error.code()
                            if (responseCode == 429)
                                ApiErrorHappened.postValue(
                                    NetworkUtils.RESPONSE_ERROR.EXCEED_LIMIT
                                )
                            else
                                ApiErrorHappened.postValue(
                                    NetworkUtils.RESPONSE_ERROR.OTHER
                                )


                        } else {
                            ApiErrorHappened.postValue(
                                NetworkUtils.RESPONSE_ERROR.OTHER
                            )
                        }
                        loading.value = false

                    }
                )
        )
    }

}


