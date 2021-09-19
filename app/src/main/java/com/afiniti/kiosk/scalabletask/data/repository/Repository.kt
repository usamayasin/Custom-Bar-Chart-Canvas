package com.afiniti.kiosk.scalabletask.data.repository

import com.afiniti.kiosk.scalabletask.data.network.ApiService
import com.afiniti.kiosk.scalabletask.model.CommitListModel
import com.afiniti.kiosk.scalabletask.model.RepoModel
import io.reactivex.Observable
import retrofit2.Response
import retrofit2.Retrofit
import javax.inject.Inject

class Repository @Inject constructor( private val retrofit: Retrofit ) {

    private var apiService: ApiService = retrofit.create(ApiService::class.java)

    fun getPublicRepos(): Observable<Response<List<RepoModel>>> {
        return apiService.getPublicRepos()
    }

    fun getCommits(repoName:String): Observable<Response<List<CommitListModel>>> {
        return apiService.getCommits(repoName)
    }

}