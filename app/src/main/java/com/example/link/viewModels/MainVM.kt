package com.example.link.viewModels

import androidx.lifecycle.MutableLiveData
import com.example.link.model.GenericResponse
import com.example.link.model.main.ArticlesModelItem
import com.example.link.network.AppRepository
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.BiFunction
import io.reactivex.schedulers.Schedulers

class MainVM(private val repository: AppRepository) : BaseViewModel() {

//    var AllObservableArticles: Observable<GenericResponse<MutableList<ArticlesModelItem>>> =
//        repository.getAssociatedPress()
//    var TheNextWebObservable: Observable<GenericResponse<MutableList<ArticlesModelItem>>> =
//        repository.getTheNextWeb()

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


//    var AssociatedPress: MutableLiveData<MutableList<ArticlesModelItem>> = MutableLiveData()
//    var TheNextWeb: MutableLiveData<MutableList<ArticlesModelItem>> = MutableLiveData()
//
//
//    fun getAssociatedPress() {
//        loading.value = true
//        mCompositeDisposable.add(
//            repository.getAssociatedPress()
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribeWith(object :
//                    CallbackWrapper<GenericResponse<MutableList<ArticlesModelItem>>>() {
//                    override fun onSuccess(t: GenericResponse<MutableList<ArticlesModelItem>>) {
//                        loading.value = false
//                        AssociatedPress.postValue(t.articles)
//                    }
//
//                    override fun onError(e: Throwable) {
//                        super.onError(e)
//                        loading.value = false
//                    }
//
//                    override fun onFail(t: String?) {
//                        loading.value = false
//                        //kotlin.error.postValue(t)
//                    }
//
//                    override fun onFail(t: Map<String, ArrayList<String>>) {
//                        loading.value = false
////                        t.forEach {
////                            if (it.key == "email") {
////                                emailErrors.value = it.value
////                            } else if (it.key == "password") {
////                                passwordErrors.value = it.value
////                            }else if (it.key == "mobile_number") {
////                                phoneErrors.value = it.value
////                            }
////                        }
//                    }
//                })
//        )
//    }
//
//    fun getTheNextWeb() {
//        loading.value = true
//        mCompositeDisposable.add(
//            repository.getTheNextWeb()
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribeWith(object :
//                    CallbackWrapper<GenericResponse<MutableList<ArticlesModelItem>>>() {
//                    override fun onSuccess(t: GenericResponse<MutableList<ArticlesModelItem>>) {
//                        loading.value = false
//                        TheNextWeb.postValue(t.articles)
//                    }
//
//                    override fun onError(e: Throwable) {
//                        super.onError(e)
//                        loading.value = false
//                    }
//
//                    override fun onFail(t: String?) {
//                        loading.value = false
//                        //kotlin.error.postValue(t)
//                    }
//
//                    override fun onFail(t: Map<String, ArrayList<String>>) {
//                        loading.value = false
////                        t.forEach {
////                            if (it.key == "email") {
////                                emailErrors.value = it.value
////                            } else if (it.key == "password") {
////                                passwordErrors.value = it.value
////                            }else if (it.key == "mobile_number") {
////                                phoneErrors.value = it.value
////                            }
////                        }
//                    }
//                })
//        )
}


