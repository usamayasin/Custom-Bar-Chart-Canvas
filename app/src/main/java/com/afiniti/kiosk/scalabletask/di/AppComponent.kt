package com.afiniti.kiosk.scalabletask.di

import com.afiniti.kiosk.scalabletask.MyApplication
import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidInjectionModule::class,
        ViewModelModule::class,
        ApiModule::class,
        HomeFragmentModule::class,
        RepoDetailFragmentModule::class,
        AppModule::class
    ]
)
interface AppComponent {
    fun inject(application: MyApplication)
}