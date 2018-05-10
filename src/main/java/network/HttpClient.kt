package network

import persist.BambooPluginSettings
import okhttp3.Credentials
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException


class HttpClient {
    companion object {
        val api : Api
            get()  {
                val bambooPluginSettings = BambooPluginSettings.getInstance()

                val httpClient = OkHttpClient.Builder()
                val interceptor = HttpLoggingInterceptor(HttpLoggingInterceptor.Logger {
                    println(it)
                })
                interceptor.level = HttpLoggingInterceptor.Level.BODY
                httpClient.addInterceptor(BasicAuthInterceptor(bambooPluginSettings.state.username, bambooPluginSettings.state.password))
                        .addInterceptor(interceptor)

                val retrofit = Retrofit.Builder()
                        .baseUrl(bambooPluginSettings.state.url + "rest/api/latest/")
                        .client(httpClient.build())
                        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                        .addConverterFactory(GsonConverterFactory.create())
                        .build()
                 return retrofit.create(Api::class.java)
        }
    }
}

class BasicAuthInterceptor(private val user: String, private val password: String) : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val credential = Credentials.basic(user, password)
        val authenticatedRequest = request.newBuilder()
                .addHeader("Accept", "application/json")
                .addHeader("Authorization", credential).build()
        return chain.proceed(authenticatedRequest)
    }
}