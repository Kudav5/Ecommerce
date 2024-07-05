package id.co.kasrt

import android.content.Intent
import android.content.IntentFilter
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.CompoundButton
import android.widget.Switch
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate

class settings : AppCompatActivity() {
    private lateinit var clickBroadcastReceiver: ClickBroadcastReceiver
    private lateinit var sharedPrefs: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        sharedPrefs = getSharedPreferences("settings", MODE_PRIVATE)

        // Inisialisasi dan daftarkan BroadcastReceiver
        clickBroadcastReceiver = ClickBroadcastReceiver()
        val filter = IntentFilter("id.co.kasrt.BUTTON_CLICKED")
        registerReceiver(clickBroadcastReceiver, filter)

        // Temukan tombol dan atur onClickListener untuk mengirim broadcast
        val button = findViewById<Button>(R.id.klikpanik)
        val switchDarkMode: Switch = findViewById(R.id.setting2Switch)

        switchDarkMode.isChecked = sharedPrefs.getBoolean("dark_mode", false)


        AppCompatDelegate.setDefaultNightMode(
            if (switchDarkMode.isChecked) AppCompatDelegate.MODE_NIGHT_YES
            else AppCompatDelegate.MODE_NIGHT_NO
        )

        switchDarkMode.setOnCheckedChangeListener { _: CompoundButton, isChecked: Boolean ->
            if (isChecked) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                saveDarkModeState(true)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                saveDarkModeState(false)
            }
        }

        button.setOnClickListener {
            val intent = Intent("id.co.kasrt.BUTTON_CLICKED")
            sendBroadcast(intent)
        }


    }

    override fun onDestroy() {
        super.onDestroy()
        // Unregister the BroadcastReceiver
        unregisterReceiver(clickBroadcastReceiver)
    }

    private fun saveDarkModeState(isEnabled: Boolean) {
        with(sharedPrefs.edit()) {
            putBoolean("dark_mode", isEnabled)
            apply()
        }
    }
}
