package com.catnip.rockpaperscissorchapter6and7.data.network.services

import com.catnip.rockpaperscissorchapter6and7.BuildConfig
import com.catnip.rockpaperscissorchapter6and7.data.local.room.datasource.LocalDataSource
import com.catnip.rockpaperscissorchapter6and7.data.network.model.request.binar.RegisterRequest
import com.catnip.rockpaperscissorchapter6and7.data.network.model.response.binar.BaseResponse
import com.catnip.rockpaperscissorchapter6and7.data.network.model.response.binar.RegisterData
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST
import java.util.concurrent.TimeUnit

interface BinarApiService {
    @POST("auth/register")
    suspend fun postRegisterUser(@Body registerRequest: RegisterRequest): BaseResponse<RegisterData, String>

    companion object {
        @JvmStatic
        operator fun invoke(localDataSource: LocalDataSource) : BinarApiService {
            val authInterceptor = Interceptor {
                val requestBuilder = it.request().newBuilder()
                localDataSource.getAuthToken()?.let { token ->
                    requestBuilder.addHeader("Authorization", "Bearer $token")
                }
                it.proceed(requestBuilder.build())
            }
            val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(authInterceptor)
                .connectTimeout(120, TimeUnit.SECONDS)
                .readTimeout(120, TimeUnit.SECONDS)
                .build()

            val retrofit = Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_URL_BINAR_AUTH)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build()

            return retrofit.create(BinarApiService::class.java)
        }
    }
}