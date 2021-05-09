package com.example.link.di

import com.example.link.network.AppRepository
import com.example.link.viewModels.CommanVM
import com.example.link.viewModels.MainVM
import org.koin.androidx.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module

/**
 * Hero is the appModule for koin used to define view models && inject singleton class's objects
 * @author Mohamed Ibrahim
 */
val appModule = module {

    //repos
    factory { AppRepository() }


    //viewmodel
    viewModel { CommanVM(get()) }
    viewModel { MainVM(get()) }
}