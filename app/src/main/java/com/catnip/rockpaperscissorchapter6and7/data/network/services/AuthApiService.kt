package com.catnip.rockpaperscissorchapter6and7.data.network.services

import com.catnip.rockpaperscissorchapter6and7.BuildConfig
import com.catnip.rockpaperscissorchapter6and7.data.local.preference.datasource.LocalDataSource
import com.catnip.rockpaperscissorchapter6and7.data.network.model.request.binar.RegisterRequest
import com.catnip.rockpaperscissorchapter6and7.data.network.model.request.auth.AuthRequest
import com.catnip.rockpaperscissorchapter6and7.data.network.model.response.auth.BaseAuthResponse
import com.catnip.rockpaperscissorchapter6and7.data.network.model.response.auth.UserData
import com.catnip.rockpaperscissorchapter6and7.data.network.model.response.auth.BaseResponse
import com.catnip.rockpaperscissorchapter6and7.data.network.model.response.auth.RegisterData
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import java.util.concurrent.TimeUnit

interface AuthApiService {

    @GET("auth/me")
    suspend fun getSyncUser(): BaseAuthResponse<UserData, String>

    @POST("auth/register")
    suspend fun postRegisterUser(@Body registerRequest: RegisterRequest): BaseResponse<RegisterData, String>

    @POST("auth/login")
    suspend fun postLoginUser(@Body loginRequest: AuthRequest): BaseAuthResponse<UserData, String>

    @GET("users")
    suspend fun getUserData() : BaseAuthResponse<UserData, String>

    @PUT("users")
    suspend fun putUserData(@Body data : RequestBody) : BaseAuthResponse<UserData, String>

//    @POST("auth/register")
//    suspend fun postRegisterUser(@Body registerRequest: AuthRequest): BaseAuthResponse<UserData, String>

    companion object {

        @JvmStatic
        operator fun invoke(localDataSource: LocalDataSource) : AuthApiService {
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

            return retrofit.create(AuthApiService::class.java)
        }
    }
}