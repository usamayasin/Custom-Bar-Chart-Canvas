package com.afiniti.kiosk.scalabletask

import android.app.Application
import com.afiniti.kiosk.scalabletask.di.ApiModule
import com.afiniti.kiosk.scalabletask.di.AppModule
import com.afiniti.kiosk.scalabletask.di.DaggerAppComponent
import com.afiniti.kiosk.scalabletask.utils.AppConstants
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import javax.inject.Inject

open class MyApplication : Application(), HasAndroidInjector {

    @Inject
    lateinit var mInjector: DispatchingAndroidInjector<Any>

    override fun onCreate() {
        super.onCreate()
        DaggerAppComponent
            .builder()
            .apiModule(ApiModule(AppConstants.Keys.BASE_URL))
            .appModule(AppModule(this))
            .build()
            .inject(this)
    }

    override fun androidInjector(): AndroidInjector<Any> {
        return mInjector
    }
}