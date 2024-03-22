package id.co.ecommerce

import android.os.Bundle
import android.os.PersistableBundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import id.co.ecommerce.model.Dataitem
import id.co.ecommerce.model.ResponseUser
import id.co.ecommerce.network.ApiConfig
import id.co.ecommerce.network.ApiService
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    private lateinit var adapter: UserAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        adapter = UserAdapter(mutableListOf())

        val rv_users  //buat val
        rv_users.setHasFixedSize(true)
        rv_users.layoutManager = LinearLayoutManager(this)
        rv_users.adapter = adapter

        getUser()
    }

    private fun getUser() {
        val retrofit = Retrofit.Builder()
                .baseUrl("https://reqres.in/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        val service = retrofit.create(ApiService::class.java);

        val client = ApiConfig.getApiService().getListUsers("1")

        client.enqueue(object: Callback<ResponseUser> {
            override fun onResponse(call: Call<ResponseUser>, response: Response<ResponseUser>)
            if (response.isSuccessful) {
                val dataArray = response.body()?.data as List<Dataitem>
                for (data in dataArray) {
                    adapter.addUser(data)
                }
            }

            override fun onFailure(call: Call<ResponseUser>, t: Throwable) {
                Toast.makeText(this, t.message, Toast.LENGTH_SHORT).show()
                t.printStackTrace()
            }
        })
    }
}