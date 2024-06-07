package id.co.kasrt

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import com.google.firebase.messaging.FirebaseMessaging
import java.lang.reflect.Modifier


class layarUtama : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_layar_utama)

        FirebaseMessaging.getInstance().subscribeToTopic("Laporan")
            .addOnCompleteListener { task ->
                var msg = "Done"
                if (!task.isSuccessful) {
                    msg = "Failed"
                }
            }
        val topView: View = findViewById(R.id.top_View)
        val cardView_orang: CardView = findViewById(R.id.tambah)
        val cardView_api: CardView = findViewById(R.id.lihat)
        val cardView_ubah: CardView = findViewById(R.id.ubah)
        val cardView_hapus: CardView = findViewById(R.id.hapus)

        cardView_orang.setOnClickListener {
            val intent = Intent(this, add_data::class.java)
            startActivity(intent)
        }

        cardView_api.setOnClickListener {
            val intent = Intent(this, read_data::class.java)
            startActivity(intent)
        }
        cardView_ubah.setOnClickListener {
            val intent = Intent(this, ubah_data::class.java)
            startActivity(intent)
        }
        cardView_hapus.setOnClickListener {
            val intent = Intent(this, hapus_data::class.java)
            startActivity(intent)
        }
    }
    // Fungsi untuk menampilkan Toast
    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }


}