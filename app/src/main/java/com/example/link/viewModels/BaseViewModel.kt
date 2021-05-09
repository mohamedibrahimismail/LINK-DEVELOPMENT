package com.example.link.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable

/**
 * BaseViewModel is used as parent view model for all view models
 * contains base variables like (loading , error)
 * @author Mohamed Ibrahim
 */
abstract class BaseViewModel : ViewModel() {
    val snackbarText = MutableLiveData<Pair<Int, Int>>()
    val snackbarMessage: LiveData<Pair<Int, Int>>
        get() = snackbarText

    val loading = MutableLiveData<Boolean>()
    val refreshLoading = MutableLiveData<Boolean>(false)
    val isFirst = MutableLiveData<Boolean>(false)
    val moreLoading = MutableLiveData<Boolean>()
//    val dataLoading: LiveData<Boolean> get() = loading

    var error: MutableLiveData<String> = MutableLiveData()

    val mCompositeDisposable: CompositeDisposable = CompositeDisposable()
}
