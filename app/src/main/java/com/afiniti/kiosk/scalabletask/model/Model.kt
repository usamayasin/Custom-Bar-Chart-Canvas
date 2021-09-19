package com.afiniti.kiosk.scalabletask.model

import kotlinx.android.parcel.Parcelize
import android.os.Parcelable

@Parcelize
data class RepoModel(
    val name: String,
    val forks: Int,
    var default_branch: String,
    var commits_url:String
) : Parcelable


@Parcelize
data class CommitListModel(
    val commit: Commit
) : Parcelable

@Parcelize
data class Commit(
    val author: Author
) : Parcelable

@Parcelize
data class Author(
    val name: String,
    val email:String,
    val date:String
) : Parcelable
