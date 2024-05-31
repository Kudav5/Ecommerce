package id.co.kasrt

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.Constants.MessageNotificationKeys.TAG

class add_data : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_data)

        val db = Firebase.firestore

        val namdep: EditText = findViewById(R.id.namdep)
        val nambel: EditText = findViewById(R.id.nambel)
        val email: EditText = findViewById(R.id.email)
        val alamat: EditText = findViewById(R.id.alamat)
        val iuran_bulanan: EditText = findViewById(R.id.IuranBulanan)
        val iuran_individu: EditText = findViewById(R.id.IuranIndividu)
        val iuran_akhir: EditText = findViewById(R.id.IuranAkhir)
        val pengeluaran: EditText = findViewById(R.id.Pengeluaran)
        val pemanfaatan: EditText = findViewById(R.id.Pemanfaatan)

        val kirim: Button = findViewById(R.id.kirim)

        val laporan = hashMapOf(
            "Nama Depan" to namdep,
            "Nama Belakang" to nambel,
            "Email" to email,
            "Alamat Rumah" to alamat,
            "Jumlah Iuran Bulanan" to iuran_bulanan,
            "Total Iuran Individu" to iuran_individu,
            "Total Iuran Akhir" to iuran_akhir,
            "Pengeluaran Iuran" to pengeluaran,
            "Pemanfaatan Iuran" to pemanfaatan
        )

        kirim.setOnClickListener {
            namdep.text.toString()
            nambel.text.toString()
            email.text.toString()
            alamat.text.toString()
            iuran_bulanan.text.toString()
            iuran_individu.text.toString()
            iuran_akhir.text.toString()
            pengeluaran.text.toString()
            pemanfaatan.text.toString()


            // Add a new document with a generated ID
            db.collection("laporan")
                .add(laporan)
                .addOnSuccessListener { documentReference ->
                    Log.d(TAG, "DocumentSnapshot added with ID: ${documentReference.id}")
                    Toast.makeText(this, "Laporan berhasil dikirim", Toast.LENGTH_SHORT).show()
                }
                .addOnFailureListener { e ->
                    Log.w(TAG, "Error adding document", e)
                    Toast.makeText(this, "Gagal mengirim laporan", Toast.LENGTH_SHORT).show()
                }
        }

    }
}