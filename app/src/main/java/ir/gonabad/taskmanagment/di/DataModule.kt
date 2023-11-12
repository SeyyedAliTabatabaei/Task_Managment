package ir.gonabad.taskmanagment.di

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import ir.gonabad.taskmanagment.framework.network.ApiService
import ir.gonabad.taskmanagment.utils.Constants
import ir.gonabad.taskmanagment.utils.IsOnline
import ir.gonabad.taskmanagment.utils.PrefHandler
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataModule {

    @Provides
    @Singleton
    @MainOkHttpClient
    fun provideOkHttpClient() : OkHttpClient{
        return OkHttpClient.Builder()
            .connectTimeout(20, TimeUnit.SECONDS)
            .readTimeout(20, TimeUnit.SECONDS)
            .writeTimeout(20, TimeUnit.SECONDS)
            .addInterceptor {
                val oldRequest = it.request()
                val newRequestBuilder = oldRequest.newBuilder()
//                if (TokenContainer.token != null)
//                    newRequestBuilder.addHeader("Authorization" , "Bearer ${TokenContainer.token}")
//
//                newRequestBuilder.addHeader("Content-Type" , "application/json")
//                newRequestBuilder.addHeader("X-API-KEY" , Constants.API_KEY)
//                newRequestBuilder.addHeader("app" , Constants.App)

                newRequestBuilder.method(oldRequest.method, oldRequest.body)

                return@addInterceptor it.proceed(newRequestBuilder.build())
            }
            .build()
    }

    @Provides
    @Singleton
    @MainRetrofit
    fun provideRetrofit(@MainOkHttpClient okHttpClient: OkHttpClient) : Retrofit{
        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(Constants.baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideApiService(@MainRetrofit retrofit: Retrofit) : ApiService =
        retrofit.create(ApiService::class.java)

    @Provides
    @Singleton
    fun provideSharedPreferences(@ApplicationContext context: Context) : SharedPreferences{
        return context.getSharedPreferences("task_manager" , Context.MODE_PRIVATE)
    }

    @Provides
    @Singleton
    fun providePreferenceHandler(application: Application) : PrefHandler {
        return PrefHandler(application)
    }

    @Provides
    @Singleton
    fun isOnline(@ApplicationContext context: Context): IsOnline {
        return IsOnline(context)
    }

}