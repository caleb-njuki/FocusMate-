package com.example.focusmatepro

import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.RatingBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.util.Locale
import android.media.MediaPlayer
import android.media.RingtoneManager

class MainActivity : AppCompatActivity() {

    // 1. Declare all the UI elements from your sketch
    private lateinit var etHours: EditText
    private lateinit var etMinutes: EditText
    private lateinit var etSeconds: EditText
    private lateinit var tvTimerDisplay: TextView
    private lateinit var btnStartStop: Button
    private lateinit var btnReset: Button

    // Rating & Comment Section elements
    private lateinit var layoutRatingSection: LinearLayout
    private lateinit var ratingBar: RatingBar
    private lateinit var etComment: EditText
    private lateinit var btnOk: Button

    // History element
    private lateinit var tvHistoryLog: TextView

    // 2. Timer variables to keep track of state
    private var countDownTimer: CountDownTimer? = null
    private var isTimerRunning = false
    private var timeLeftInMillis: Long = 0
    private var initialTimeSetString: String = ""
    private var mediaPlayer: MediaPlayer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // 3. Initialize the UI elements by matching them to their XML IDs
        etHours = findViewById(R.id.etHours)
        etMinutes = findViewById(R.id.etMinutes)
        etSeconds = findViewById(R.id.etSeconds)
        tvTimerDisplay = findViewById(R.id.tvTimerDisplay)
        btnStartStop = findViewById(R.id.btnStartStop)
        btnReset = findViewById(R.id.btnReset)

        layoutRatingSection = findViewById(R.id.layoutRatingSection)
        ratingBar = findViewById(R.id.ratingBar)
        etComment = findViewById(R.id.etComment)
        btnOk = findViewById(R.id.btnOk)

        tvHistoryLog = findViewById(R.id.tvHistoryLog)
        val prefs = getSharedPreferences(
            "FocusMatePrefs",
            MODE_PRIVATE
        )

        val history = prefs.getString(
            "history",
            "your history appears here"
        )

        tvHistoryLog.text = history

        // 4. Set up what happens when you click "Start / Stop"
        btnStartStop.setOnClickListener {
            if (isTimerRunning) {
                pauseTimer()
            } else {
                startTimer()
            }
        }

        // 5. Set up what happens when you click "Reset"
        btnReset.setOnClickListener {
            resetTimer()
        }

        // 6. Set up what happens when you click "OK" in the rating section
        btnOk.setOnClickListener {
            saveSessionToHistory()
        }
    }

    // --- TIMER LOGIC FUNCTIONS ---

    private fun startTimer() {
        // If the timer isn't already paused mid-session, read fresh input from user
        if (timeLeftInMillis == 0L) {
            val hoursText = etHours.text.toString()
            val minutesText = etMinutes.text.toString()
            val secondsText = etSeconds.text.toString()

            // Convert empty inputs to 0 if the user left them blank
            val hours = if (hoursText.isEmpty()) 0 else hoursText.toInt()
            val minutes = if (minutesText.isEmpty()) 0 else minutesText.toInt()
            val seconds = if (secondsText.isEmpty()) 0 else secondsText.toInt()

            // Validation checks as we agreed: Hours up to 24, M/S up to 59
            if (hours > 24 || (hours == 24 && (minutes > 0 || seconds > 0))) {
                Toast.makeText(this, "Please set a time up to 24 hours max", Toast.LENGTH_SHORT).show()
                return
            }
            if (minutes > 59 || seconds > 59) {
                Toast.makeText(this, "Minutes and Seconds must be under 60", Toast.LENGTH_SHORT).show()
                return
            }

            // Total time chosen in milliseconds
            val totalSeconds = (hours * 3600) + (minutes * 60) + seconds
            if (totalSeconds == 0) {
                Toast.makeText(this, "Please enter a time to start", Toast.LENGTH_SHORT).show()
                return
            }

            timeLeftInMillis = totalSeconds * 1000L
            // Store a clean string of what the user originally set for the history log later
            initialTimeSetString = String.format(Locale.getDefault(), "%02dH : %02dM : %02dS", hours, minutes, seconds)
        }

        // Hide rating section if a fresh session or resumed session starts
        layoutRatingSection.visibility = View.GONE

        // Start the actual countdown mechanism
        countDownTimer = object : CountDownTimer(timeLeftInMillis, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                timeLeftInMillis = millisUntilFinished
                updateTimerText()
            }

            override fun onFinish() {
                isTimerRunning = false
                btnStartStop.text = "Start / Stop"
                tvTimerDisplay.text = "00:00:00"
                timeLeftInMillis = 0

                // Show the rating and comment section now that focus session is done!
                layoutRatingSection.visibility = View.VISIBLE
                Toast.makeText(this@MainActivity, "Focus Session Finished!", Toast.LENGTH_LONG).show()
            }
        }.start()

        isTimerRunning = true
        btnStartStop.text = "Pause"
    }

    private fun pauseTimer() {
        countDownTimer?.cancel()
        isTimerRunning = false
        btnStartStop.text = "Resume"
    }

    private fun resetTimer() {
        countDownTimer?.cancel()
        isTimerRunning = false
        timeLeftInMillis = 0
        tvTimerDisplay.text = "00:00:00"
        btnStartStop.text = "Start / Stop"
        layoutRatingSection.visibility = View.GONE

        // Clear inputs
        etHours.text.clear()
        etMinutes.text.clear()
        etSeconds.text.clear()
    }

    private fun updateTimerText() {
        val totalSeconds = timeLeftInMillis / 1000
        val hours = totalSeconds / 3600
        val minutes = (totalSeconds % 3600) / 60
        val seconds = totalSeconds % 60

        val formattedTime = String.format(Locale.getDefault(), "%02d:%02d:%02d", hours, minutes, seconds)
        tvTimerDisplay.text = formattedTime
    }

    private fun saveSessionToHistory() {

        val stars = ratingBar.rating.toInt()

        val comment = etComment.text.toString().trim()

        val starIcons = "⭐".repeat(stars)

        val newEntry =
            "Session: $initialTimeSetString\n" +
                    "Rating: $starIcons\n" +
                    "Comment: $comment\n\n"

        val prefs =
            getSharedPreferences("FocusMatePrefs", MODE_PRIVATE)

        val oldHistory =
            prefs.getString("history", "") ?: ""

        val updatedHistory =
            newEntry + oldHistory

        prefs.edit()
            .putString("history", updatedHistory)
            .apply()

        tvHistoryLog.text = updatedHistory

        ratingBar.rating = 0f

        etComment.text.clear()

        layoutRatingSection.visibility = View.GONE

        Toast.makeText(
            this,
            "Logged to history!",
            Toast.LENGTH_SHORT
        ).show()
    }
    private fun playAlarmSound() {
        try {
            // Grab the default notification sound URI from the Android device
            val alarmUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM)

            // Initialize the MediaPlayer with the sound URI
            mediaPlayer = MediaPlayer.create(this, alarmUri)
            mediaPlayer?.start()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}