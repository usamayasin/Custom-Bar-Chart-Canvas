package com.afiniti.kiosk.scalabletask.ui.detail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.afiniti.kiosk.scalabletask.data.repository.Repository
import com.afiniti.kiosk.scalabletask.model.CommitListModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class RepoDetailViewModel @Inject constructor(private val repository: Repository) : ViewModel() {

    private val disposables = CompositeDisposable()
    private val reposMutableLiveData = MutableLiveData<List<CommitListModel>>()
    val repoCommitsLiveData: LiveData<List<CommitListModel>> = reposMutableLiveData

    fun fetchCommits(repoName: String) {
        disposables.add(
            repository.getCommits(repoName)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ result ->
                    if (result.isSuccessful) {
                        result.body()?.also {
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