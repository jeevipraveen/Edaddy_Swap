package com.mauto.myapplication.api


import com.mauto.myapplication.BuildConfig
import com.mauto.myapplication.utils.ApiConstant
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.net.ssl.SSLContext


object RetrofitBuilder {
    private const val BASE_URL = BuildConfig.SERVER_URL;
    private var sOkHttpClient: OkHttpClient? = null
    private var sslContext: SSLContext? = null
    private var sslSocketFactory: javax.net.ssl.SSLSocketFactory? = null
//    val mediaType = "text/plain".toMediaTypeOrNull()




    var interceptor: HttpLoggingInterceptor =HttpLoggingInterceptor()

    private fun getRetrofit(): Retrofit {
        interceptor.apply { interceptor.level = HttpLoggingInterceptor.Level.BODY };
        return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(getOkHttpClient())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build()
    }

        private fun getOkHttpClient(): OkHttpClient? {
        // Create an ssl socket factory with our all-trusting manager
        sslSocketFactory = sslContext?.socketFactory
        val okHttpClientBuilder = OkHttpClient().newBuilder()

                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .addInterceptor { chain ->
                    val original = chain.request()
                    val request = original.newBuilder()
//                        .header("token", ApiConstant.OBJ_HEADER_TKN)
                        .method(original.method, original.body,)
                        .build()
                    chain.proceed(request)

                }
        okHttpClientBuilder.interceptors().add(interceptor);

        sOkHttpClient =okHttpClientBuilder.build()


        return sOkHttpClient
    }

    val apiService: ApiServices = getRetrofit().create(ApiServices::class.java)



}