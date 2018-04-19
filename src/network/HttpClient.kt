package network

import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import java.security.cert.CertificateException
import java.security.cert.X509Certificate
import javax.net.ssl.*


class HttpClient {
    companion object {
        val api : Api
        init {
            val httpClient = OkHttpClient.Builder()
            val interceptor = HttpLoggingInterceptor(HttpLoggingInterceptor.Logger {
                    println(it)
                })
                interceptor.level = HttpLoggingInterceptor.Level.BODY
            httpClient.addInterceptor(BasicAuthInterceptor("<username>", "<password>"))
                .addInterceptor(interceptor)

            val retrofit = Retrofit.Builder()
                .baseUrl("<bamboolink>")
                .client(httpClient.build())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            api = retrofit.create(Api::class.java)

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