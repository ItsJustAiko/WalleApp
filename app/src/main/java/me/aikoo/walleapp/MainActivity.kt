package me.aikoo.walleapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import java.util.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    private fun startTimer(view: View) {
        // Get the countdown TextView element
        val countdownTextView = findViewById<TextView>(R.id.countdown)

        // Start the timer and update the countdown TextView element in form of "00:00:00"
        val timer = Timer()
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
    }
}