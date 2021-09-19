package com.afiniti.kiosk.scalabletask.ui.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.afiniti.kiosk.scalabletask.data.repository.Repository
import com.afiniti.kiosk.scalabletask.model.RepoModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class HomeViewModel @Inject constructor( private val repository: Repository) : ViewModel() {

    private val disposables = CompositeDisposable()
    private val reposMutableLiveData = MutableLiveData<List<RepoModel>>()
    val repoLiveData: LiveData<List<RepoModel>> = reposMutableLiveData

    init{
        fetchRepos()
    }

    private fun fetchRepos() {
        disposables.add(
            repository.getPublicRepos()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ result ->
                    if (result.isSuccessful) {
                        result.body()?.also {
                            println("Data is  $it")
                            reposMutableLiveData.postValue(it)
                        }
                    } else {
                        Log.e("Error", result.message())
                    }
                }, { throwable ->
                    Log.e("Exception", throwable.message.toString())
                })
        )
    }
}