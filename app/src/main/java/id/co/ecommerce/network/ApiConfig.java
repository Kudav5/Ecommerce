package id.co.ecommerce.network;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiConfig {
    companion object {
        fun getApiService(): ApiService {
            val loggingInterceptor = new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
            val client = new OkHttpClient.Builder()
                    .addInterceptor(loggingInterceptor)
                    .build()

            val retrofit = new Retrofit.Builder()
                    .baseUrl("https://reqres.in/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
            return retrofit.create(ApiService::class.java);
        }
    }
}

private fun getUser() {
    val client = ApiConfig.getApiService().getListUsers("1")
}
