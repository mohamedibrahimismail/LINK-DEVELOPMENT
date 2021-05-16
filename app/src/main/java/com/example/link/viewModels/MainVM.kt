package com.example.link.viewModels

import androidx.lifecycle.MutableLiveData
import com.example.link.R
import com.example.link.model.GenericResponse
import com.example.link.model.main.ArticlesModelItem
import com.example.link.model.main.NavigationItemModel
import com.example.link.network.AppRepository
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.BiFunction
import io.reactivex.schedulers.Schedulers

class MainVM(private val repository: AppRepository) : BaseViewModel() {

    var AllArticles: MutableLiveData<MutableList<ArticlesModelItem>> = MutableLiveData()
    var ErrorHappened: MutableLiveData<Boolean> = MutableLiveData()


    fun getAllArticles(){
        loading.value = true
        mCompositeDisposable.add(
            Observable.zip(repository.getAssociatedPress()
                , repository.getTheNextWeb(),
                BiFunction<GenericResponse<MutableList<ArticlesModelItem>>,GenericResponse<MutableList<ArticlesModelItem>>,MutableList<ArticlesModelItem>> { t1, t2 ->
                    val list : MutableList<ArticlesModelItem> = ArrayList()
                    list.addAll(t1.articles!!)
                    list.addAll(t2.articles!!)
                    list
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    {result ->
                        loading.value = false
                        AllArticles.value = result
                       },
                    {error ->
                        loading.value = false
                        ErrorHappened.value = true
                    }
                )
        )
    }

}


