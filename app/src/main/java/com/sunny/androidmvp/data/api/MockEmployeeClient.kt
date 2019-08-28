package com.sunny.androidmvp.data.api
import com.squareup.moshi.Moshi
import com.sunny.androidmvp.App
import com.sunny.androidmvp.data.models.Employees
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import java.io.IOException
import java.nio.charset.Charset


object MockEmployeeClient{

    private val headerInterceptor = object : Interceptor{
        override fun intercept(chain: Interceptor.Chain): Response {
            val response = loadJson()
            return Response.Builder()
                .code(200)
                .message(response)
                .request(chain.request())
                .protocol(Protocol.HTTP_1_1)
                .body(ResponseBody.create(MediaType.parse("application/json"), response.toByteArray()))
                .addHeader("content-type", "application/json")
                .build()
        }

        private fun loadJson(): String {
            var json = ""
            try {
                val file = App.getContext()?.assets?.open("employees.json")
                val size = file?.available()
                val buffer = ByteArray(size ?: 0)
                file?.read(buffer)
                file?.close()
                json = String(buffer, Charset.forName("UTF-8"))
            } catch (ex: IOException) {
                ex.printStackTrace()
            }
            return json
        }
    }

    private val interceptor = HttpLoggingInterceptor()

    init {
        interceptor.level = HttpLoggingInterceptor.Level.BODY
    }

    private val client = OkHttpClient().newBuilder()
        .addInterceptor(headerInterceptor)
        .addInterceptor(interceptor)
        .build()

    var moshi = Moshi.Builder()
        .build()


    private fun retrofit() : Retrofit = Retrofit.Builder()
        .client(client)
        .baseUrl("https://google.com/")
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .build()


    val api : EmployeeApi = retrofit().create(EmployeeApi::class.java)
}

interface EmployeeApi{
    @GET("employees")
    fun getEmployees(): Call<Employees>
}
