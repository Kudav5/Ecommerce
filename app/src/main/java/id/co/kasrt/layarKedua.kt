package id.co.kasrt

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.cardview.widget.CardView

class layarKedua : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_layar_kedua)

        val cardView_warga: CardView = findViewById(R.id.data_warga)
        val cardView_laporan: CardView = findViewById(R.id.laporan)
        val lingkungan: CardView = findViewById(R.id.kelolalingkungan)
        val aboutUus: CardView = findViewById(R.id.aboutUs)

        val nex = findViewById<Button>(R.id.next)
        val prev = findViewById<Button>(R.id.previous)

        prev.setOnClickListener {
            val intent = Intent(this, layarUtama::class.java)
            startActivity(intent)
        }

        cardView_warga.setOnClickListener {
            val intent = Intent(this, MainActivity2::class.java)
            startActivity(intent)
        }
        cardView_laporan.setOnClickListener {
            val intent = Intent(this, laporan_keuangan::class.java)
            startActivity(intent)
        }

        aboutUus.setOnClickListener {
            val intent = Intent(this, PembukuanKeuangan::class.java)
            startActivity(intent)
        }

        lingkungan.setOnClickListener {
            val intent = Intent(this, PengelolaanLingkungan::class.java)
            startActivity(intent)
        }
    }
}