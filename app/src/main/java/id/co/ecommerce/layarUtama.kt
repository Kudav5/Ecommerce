package id.co.ecommerce

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.cardview.widget.CardView

class layarUtama : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_layar_utama)

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