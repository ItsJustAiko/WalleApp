package me.aikoo.walleapp

import android.Manifest
import android.annotation.SuppressLint
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothManager
import android.bluetooth.BluetoothSocket
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.Window
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.util.*

class MainActivity : AppCompatActivity() {
    private lateinit var timer: Timer
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        supportActionBar?.hide()

        setContentView(R.layout.activity_main)
    }

    @SuppressLint("SetTextI18n")
    fun connectBluetooth() {
        val status = findViewById<TextView>(R.id.statusText)

        val btManager: BluetoothManager = getSystemService(BLUETOOTH_SERVICE) as BluetoothManager
        val btAdapter: BluetoothAdapter? = btManager.adapter
        val deviceAddress = "98:D3:51:FE:45:1A"
        val device: BluetoothDevice? = btAdapter?.getRemoteDevice(deviceAddress)
        val permission = packageManager.checkPermission(Manifest.permission.BLUETOOTH, packageName)

        if (permission != PackageManager.PERMISSION_GRANTED) {
            status.text = "Status: Failed to connect to Wall.E! Missing permissions!"
            return
        }

        if (device == null) {
            status.text = "Status: Failed to connect to Wall.E! No such device"
            return
        }

        status.text = "Status: Connecting to Wall.E..."
        val socket: BluetoothSocket? = device.createRfcommSocketToServiceRecord(UUID.fromString("00001101-0000-1000-8000-00805F9B34FB"))
        socket?.connect()
        if (socket?.isConnected == true) {
            status.text = "Status: Connected to Wall.E!"
        } else {
            status.text = "Status: Failed to connect to Wall.E! Is it turned on?"
        }
    }


    @SuppressLint("SetTextI18n")
    fun startTimer() {
        val countdownTextView by lazy { findViewById<TextView>(R.id.countdown) }
        val buttonView by lazy { findViewById<TextView>(R.id.button1) }

        when (buttonView.text) {
            "Start" -> {
                buttonView.text = "Stop"

                val time = countdownTextView.text.split(":", ".")
                var elapsedTime = time[0].toLong() * 60000 + time[1].toLong() * 1000 + time[2].toLong()

                timer = Timer()
                timer.scheduleAtFixedRate(object : TimerTask() {
                    override fun run() {
                        runOnUiThread {
                            elapsedTime++
                            val minutes = elapsedTime / 60000
                            val seconds = elapsedTime % 60000 / 1000
                            val milliseconds = elapsedTime % 1000

                            countdownTextView.text = "%02d:%02d.%03d".format(minutes, seconds, milliseconds)
                        }
                    }
                }, 0, 1)
            }
            "Stop" -> {
                buttonView.text = "Start"
                timer.cancel()
                timer.purge()
            }
        }
    }
}
