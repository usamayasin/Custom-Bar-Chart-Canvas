package com.afiniti.kiosk.scalabletask.data.network

import com.afiniti.kiosk.scalabletask.model.CommitListModel
import com.afiniti.kiosk.scalabletask.model.RepoModel
import io.reactivex.Observable
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {

    @GET("/users/usamayasin/repos")
    fun getPublicRepos(): Observable<Response<List<RepoModel>>>

    @GET("/repos/usamayasin/{name}/commits")
    fun getCommits(
        @Path("name") name:String,
    ): Observable<Response<List<CommitListModel>>>

}