package com.andersonpimentel.faceitdata.login.data.di

import com.andersonpimentel.faceitdata.login.data.api.LoginService
import com.andersonpimentel.faceitdata.login.data.datasource.LoginDataStub
import com.andersonpimentel.faceitdata.login.data.datasource.LoginLocalDataSource
import com.andersonpimentel.faceitdata.login.data.datasource.LoginRemoteDataSource
import com.andersonpimentel.faceitdata.login.data.repository.LoginRepositoryImpl
import com.andersonpimentel.faceitdata.login.util.Constants
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

private const val TIMEOUT_CONNECTION = 30L

val loginDataModule = module {

    factory { providesOkHttpClient() }

    single {
        createWebService<LoginService>(
            okHttpClient = get(),
        )
    }

    factory { LoginLocalDataSource(LoginDataStub) }

    factory { LoginRemoteDataSource(service = get()) }

    factory { LoginRepositoryImpl(
        loginLocalDataSource = get(),
        loginRemoteDataSource = get()
    ) }
}

internal inline fun <reified T> createWebService(okHttpClient: OkHttpClient): T {
    val baseUrl = "https://api.faceit.com/auth/v1/"
    return Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(baseUrl)
        .client(okHttpClient)
        .build()
        .create(T::class.java)
}

internal fun providesOkHttpClient(): OkHttpClient {
    return OkHttpClient.Builder()
        .addInterceptor(HttpLoggingInterceptor().apply { setLevel(HttpLoggingInterceptor.Level.BODY) })
        .connectTimeout(TIMEOUT_CONNECTION, TimeUnit.SECONDS)
        .readTimeout(TIMEOUT_CONNECTION, TimeUnit.SECONDS)
        .writeTimeout(TIMEOUT_CONNECTION, TimeUnit.SECONDS)
        .build()
}