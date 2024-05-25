package id.co.kasrt

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import com.google.firebase.messaging.FirebaseMessaging


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

        val cardView_orang: CardView = findViewById(R.id.orang)
        val cardView_api: CardView = findViewById(R.id.api)

        cardView_orang.setOnClickListener {
            val intent = Intent(this, MainActivity2::class.java)
            startActivity(intent)
        }

        cardView_api.setOnClickListener {
            val intent = Intent(this, laporan_keuangan::class.java)
            startActivity(intent)
        }
    }
    // Fungsi untuk menampilkan Toast
    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}