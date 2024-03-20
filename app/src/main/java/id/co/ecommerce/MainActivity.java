package id.co.ecommerce;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import id.co.ecommerce.model.ResponseUser;

public class MainActivity extends AppCompatActivity {
    private lateinit var adapter: UserAdapter

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        adapter = UserAdapter(mutableListOf())

        rv_users.setHasFixedSize(true)
        rv_users.layoutManager = LinearLayoutManager(this)
        rv_users.adapter = adapter

        getUser()
    }

    private fun getUser() {
        val client = ApiConfig.getApiService().getListUsers("1")

        client.enqueue(Callback<ResponseUser> {
            override fun onResponse(Call<ResponseUser>, response: ResponseUser)
            if (response.isSuccessful) {
                val dataArray = response.body()?.data as List<DataItem>
                for (data in dataArray) {
                    adapter.addUser(data)
                }
            }
        })

        override fun onFailure(Call<ResponseUser>, t: Throwable) {
            Toast.makeText(this, t.message, Toast.Legth)
            t.printStackTrace()
        }

    }
}