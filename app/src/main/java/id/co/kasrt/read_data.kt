package id.co.kasrt

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.FirebaseMessaging

class read_data : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_read_data)

        val db = Firebase.firestore

        val namdep: TextView = findViewById(R.id.namdep)
        val nambel: TextView = findViewById(R.id.nambel)
        val email: TextView = findViewById(R.id.email)
        val alamat: TextView = findViewById(R.id.alamat)
        val iuran_bulanan: TextView = findViewById(R.id.IuranBulanan)
        val iuran_individu: TextView = findViewById(R.id.IuranIndividu)
        val iuran_akhir: TextView = findViewById(R.id.IuranAkhir)
        val pengeluaran: TextView = findViewById(R.id.Pengeluaran)
        val pemanfaatan: TextView = findViewById(R.id.Pemanfaatan)

        val dropdown = findViewById<Spinner>(R.id.pilihdokumen)

        // Mengambil data dari koleksi "laporan"
        db.collection("laporan")
            .get()
            .addOnSuccessListener { result ->
                // Buat list untuk menyimpan item
                val items = ArrayList<String>()

                // Loop melalui setiap dokumen dan tambahkan ID dokumen ke list
                for (document in result) {
                    val itemId = document.id
                    items.add(itemId)
                }

                // Buat adapter dengan data yang diambil dari Firestore
                val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, items)

                // Set adapter ke spinner
                dropdown.adapter = adapter
            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error getting documents.", exception)
            }

        // Set listener untuk item yang dipilih dari Spinner
        dropdown.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                val selectedDocumentId = parent.getItemAtPosition(position) as String
                fetchDocumentDetails(selectedDocumentId)
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // Do nothing
            }
        }
    }

    private fun fetchDocumentDetails(documentId: String) {
        val db = Firebase.firestore

        db.collection("laporan").document(documentId)
            .get()
            .addOnSuccessListener { document ->
                if (document != null) {
                    val namaDepan = document.getString("Nama Depan") ?: "N/A"
                    val namaBelakang = document.getString("Nama Belakang") ?: "N/A"
                    val emailData = document.getString("Email") ?: "N/A"
                    val alamatRumah = document.getString("Alamat Rumah") ?: "N/A"
                    val jumlahIuranBulanan = document.getDouble("Jumlah Iuran Bulanan")?.toString() ?: "0"
                    val pemanfaatanIuran = document.getString("Pemanfaatan Iuran") ?: "N/A"
                    val pengeluaranIuran = document.getDouble("Pengeluaran Iuran")?.toString() ?: "0"
                    val totalIuranIndividu = document.getDouble("Total Iuran Individu")?.toString() ?: "0"
                    val totalIuranAkhir = document.getDouble("Total Iuran Akhir")?.toString() ?: "0"

                    findViewById<TextView>(R.id.namdep).text = "Nama Depan: $namaDepan"
                    findViewById<TextView>(R.id.nambel).text = "Nama Belakang: $namaBelakang"
                    findViewById<TextView>(R.id.email).text = "Email: $emailData"
                    findViewById<TextView>(R.id.alamat).text = "Alamat Rumah: $alamatRumah"
                    findViewById<TextView>(R.id.IuranBulanan).text = "Jumlah iuran bulanan warga: $jumlahIuranBulanan"
                    findViewById<TextView>(R.id.Pemanfaatan).text = "Pemanfaatan dari iuran warga\nuntuk apa saja: $pemanfaatanIuran"
                    findViewById<TextView>(R.id.Pengeluaran).text = "Pengeluaran iuran dari hasil\niuran warga: $pengeluaranIuran"
                    findViewById<TextView>(R.id.IuranIndividu).text = "Total Iuran Individu warga: $totalIuranIndividu"
                    findViewById<TextView>(R.id.IuranAkhir).text = "Total iuran warga pada\nakhir rekap iuran bulanan: $totalIuranAkhir"

                    if (totalIuranAkhir == "0") {
                        main()
                        subscribeToIuranTopic()
                    }
                } else {
                    Log.d(TAG, "No such document")
                }
            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error getting document details.", exception)
            }
    }

    private fun subscribeToIuranTopic() {
        FirebaseMessaging.getInstance().subscribeToTopic("Iuran")
            .addOnCompleteListener { task ->
                val msg = if (task.isSuccessful) "Subscribed" else "Subscription failed"
                Log.d(TAG, msg)
            }
    }

    private fun main() {
        // Contoh pemanggilan fungsi yang tidak ada dalam kode asli
        // Implementasikan fungsi sesuai kebutuhan Anda
    }

    companion object {
        private const val TAG = "read_data"
    }
}
