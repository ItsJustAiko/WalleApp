package me.aikoo.walleapp

import android.annotation.SuppressLint
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.TextView
import java.util.*
import kotlin.concurrent.scheduleAtFixedRate

class MainActivity : AppCompatActivity() {

    private lateinit var timer: Timer;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        supportActionBar?.hide()

        setContentView(R.layout.activity_main)
    }

    fun startTimer(view: View) {
        val countdownTextView = findViewById<TextView>(R.id.countdown)
        val buttonView = findViewById<TextView>(R.id.button1)

        if (buttonView.text == "Start") {
            buttonView.text = "Stop"

            if (countdownTextView.text == "00:00:00") {
                timer = Timer()
                timer.scheduleAtFixedRate(object : TimerTask() {
                    var seconds = 0
                    override fun run() {
                        runOnUiThread {
                            val hours = seconds / 3600
                            val minutes = (seconds % 3600) / 60
                            val secs = seconds % 60
                            val time = String.format(Locale.getDefault(), "%02d:%02d:%02d", hours, minutes, secs)
                            countdownTextView.text = time
                            seconds++
                        }
                    }
                }, 0, 1000)
            } else {
                timer = Timer()
                timer.scheduleAtFixedRate(object : TimerTask() {
                    val splitted = countdownTextView.text.split(":")
                    var seconds = splitted[0].toInt() * 3600 + splitted[1].toInt() * 60 + splitted[2].toInt()
                    override fun run() {
                        runOnUiThread {
                            val hours = seconds / 3600
                            val minutes = (seconds % 3600) / 60
                            val secs = seconds % 60
                            val time = String.format(Locale.getDefault(), "%02d:%02d:%02d", hours, minutes, secs)
                            countdownTextView.text = time
                            seconds++
                        }
                    }
                }, 0, 1000)
            }
        } else {
            buttonView.text = "Start"
            timer.cancel()
        }
    }
}