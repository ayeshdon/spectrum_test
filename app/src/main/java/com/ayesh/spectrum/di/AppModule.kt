package com.ayesh.spectrum.di

import android.app.Application
import androidx.room.Room
import com.ayesh.spectrum.data.local.MovieDataBase
import com.ayesh.spectrum.data.remote.MovieApiClass
import com.ayesh.spectrum.data.repository.CacheRepositoryImpl
import com.ayesh.spectrum.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideMovieDatabase(application: Application): MovieDataBase =
        Room.databaseBuilder(application, MovieDataBase::class.java, Constants.DATABASE_NAME)
            .build()

    @Provides
    @Singleton
    fun okHttpClient(interceptor: Interceptor): OkHttpClient {
        return OkHttpClient()
            .newBuilder().apply {
                connectTimeout(60, TimeUnit.SECONDS)
                readTimeout(60, TimeUnit.SECONDS)
                writeTimeout(60, TimeUnit.SECONDS)
                addInterceptor(
                    Interceptor { chain ->
                        val builder = chain.request().newBuilder()
                        return@Interceptor chain.proceed(builder.build())
                    }
                )
            }
            .addInterceptor(interceptor)
            .build()

    }

    @Provides
    @Singleton
    fun getInterceptor(): Interceptor {
        return HttpLoggingInterceptor().apply {
            level =
                HttpLoggingInterceptor.Level.BODY

        }
    }


    @Provides
    @Singleton
    fun provideStockApi(okHttpClient: OkHttpClient): MovieApiClass {


        return Retrofit.Builder()
            .baseUrl(MovieApiClass.BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .client(okHttpClient)
            .build()
            .create(MovieApiClass::class.java)
    }


    @Provides
    @Singleton
    fun provideMovieRepo(apiClass: MovieApiClass, dataBase: MovieDataBase): CacheRepositoryImpl {
        return CacheRepositoryImpl(apiClass, dataBase)
    }
}