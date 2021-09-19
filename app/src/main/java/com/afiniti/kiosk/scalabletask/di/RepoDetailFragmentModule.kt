package com.afiniti.kiosk.scalabletask.di

import com.afiniti.kiosk.scalabletask.ui.detail.RepoDetailFragment
import com.afiniti.kiosk.scalabletask.ui.home.HomeFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class RepoDetailFragmentModule {

    @ContributesAndroidInjector
    abstract fun contributeRepoDetailFragmentInjector(): RepoDetailFragment

}