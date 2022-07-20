package com.andersonpimentel.faceitdata

import android.app.Application
import com.andersonpimentel.faceitdata.login.data.di.loginDataModule
import com.andersonpimentel.faceitdata.login.domain.di.loginDomainModule
import com.andersonpimentel.faceitdata.login.presentation.di.loginPresentationModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class FaceitDataApplication: Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@FaceitDataApplication)
            modules(
                loginDataModule,
                loginPresentationModule,
                loginDomainModule
            )
        }
    }
}