package com.catnip.rockpaperscissorchapter6and7.ui.about

import android.util.Log
import com.catnip.rockpaperscissorchapter6and7.base.BasePresenterImpl
import com.catnip.rockpaperscissorchapter6and7.base.model.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception

class AboutPresenter(
    private val view: AboutContract.View,
    private val repository: AboutContract.Repository
) : AboutContract.Presenter, BasePresenterImpl() {


    override fun getTeamMembers() {
        view.onDataCallback(Resource.Loading())
        scope.launch {
            try {
                val members = repository.getTeamMembers()
                scope.launch(Dispatchers.Main) {
                    view.onDataCallback(Resource.Success(members))
                }
            } catch (e: Exception) {
                scope.launch(Dispatchers.Main) {
                    view.onDataCallback(Resource.Error(e.message.orEmpty()))
                }
            }
        }
    }


}