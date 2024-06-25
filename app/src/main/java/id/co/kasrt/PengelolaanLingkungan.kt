package id.co.kasrt

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration

class PengelolaanLingkungan : AppCompatActivity() {

    private lateinit var suhuTextView: TextView
    private lateinit var kelembabanTextView: TextView
    private lateinit var kualitasUdaraTextView: TextView
    private lateinit var refreshButton: Button
    private lateinit var db: FirebaseFirestore
    private var environmentListener: ListenerRegistration? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pengelolaan_lingkungan)

        // Inisialisasi Firebase Firestore
        db = FirebaseFirestore.getInstance()

        // Menghubungkan view dengan variabel
        suhuTextView = findViewById(R.id.suhuTextView)
        kelembabanTextView = findViewById(R.id.kelembabanTextView)
        kualitasUdaraTextView = findViewById(R.id.kualitasUdaraTextView)
        refreshButton = findViewById(R.id.refreshButton)

        // Set listener untuk tombol refresh
        refreshButton.setOnClickListener {
            fetchEnvironmentData()
        }

        // Mengambil data lingkungan saat pertama kali membuat aktivitas
        fetchEnvironmentData()
    }

    private fun fetchEnvironmentData() {
        environmentListener?.remove()

        // Mendapatkan data lingkungan dari Firestore
        environmentListener = db.collection("lingkungan")
            .document("5JDh7f5tRwcNEYM8VtMg")  // Ganti dengan ID dokumen yang sesuai
            .addSnapshotListener { snapshot, exception ->
                if (exception != null) {
                    Toast.makeText(this, "Gagal memuat data lingkungan: ${exception.message}", Toast.LENGTH_SHORT).show()
                    return@addSnapshotListener
                }

                if (snapshot != null && snapshot.exists()) {
                    val suhu = snapshot.getDouble("suhu")
                    val kelembaban = snapshot.getDouble("kelembaban")
                    val kualitasUdara = snapshot.getString("kualitas udara")

                    suhuTextView.text = "Suhu: ${suhu ?: "-"} °C"
                    kelembabanTextView.text = "Kelembaban: ${kelembaban ?: "-"} %"
                    kualitasUdaraTextView.text = "Kualitas Udara: ${kualitasUdara ?: "-"}"
                } else {
                    Toast.makeText(this, "Data lingkungan tidak tersedia", Toast.LENGTH_SHORT).show()
                }
            }
    }

    override fun onDestroy() {
        super.onDestroy()
        environmentListener?.remove()
    }
}