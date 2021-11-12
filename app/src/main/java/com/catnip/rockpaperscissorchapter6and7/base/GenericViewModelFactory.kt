package com.catnip.rockpaperscissorchapter6and7.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.lang.IllegalArgumentException

class GenericViewModelFactory(private val viewModel: ViewModel) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {

        @Suppress("UNCHECKED_CAST")
        if(modelClass.isAssignableFrom(viewModel::class.java)){
            return viewModel as T
        }
        throw IllegalArgumentException("Unknown Class Name")
    }

}