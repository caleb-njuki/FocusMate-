package com.example.focusmatepro

import android.content.Intent
import android.os.Bundle
import android.view.GestureDetector
import android.view.MotionEvent
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DashboardActivity : AppCompatActivity() {

    private lateinit var gestureDetector: GestureDetector

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_dashboard)

        val btnManageTime = findViewById<Button>(R.id.btnManageTime)
        val btnMyHistory = findViewById<Button>(R.id.btnMyHistory)
        val btnMySchedule = findViewById<Button>(R.id.btnMySchedule)
        val btnMotivation = findViewById<Button>(R.id.btnMotivation)

        // -------------------------
        // Swipe Gesture
        // -------------------------

        gestureDetector = GestureDetector(
            this,
            object : GestureDetector.SimpleOnGestureListener() {

                private val SWIPE_THRESHOLD = 100
                private val SWIPE_VELOCITY_THRESHOLD = 100

                override fun onFling(
                    e1: MotionEvent?,
                    e2: MotionEvent,
                    velocityX: Float,
                    velocityY: Float
                ): Boolean {

                    val diffX = e2.x - (e1?.x ?: 0f)

                    if (
                        kotlin.math.abs(diffX) > SWIPE_THRESHOLD &&
                        kotlin.math.abs(velocityX) > SWIPE_VELOCITY_THRESHOLD
                    ) {

                        if (diffX > 0) {

                            startActivity(
                                Intent(
                                    this@DashboardActivity,
                                    ScheduleActivity::class.java
                                )
                            )

                        } else {

                            startActivity(
                                Intent(
                                    this@DashboardActivity,
                                    HistoryActivity::class.java
                                )
                            )

                        }

                        return true
                    }

                    return false
                }
            }
        )

        val root = findViewById<android.view.View>(android.R.id.content)

        root.setOnTouchListener { _, event ->
            gestureDetector.onTouchEvent(event)
            true
        }

        // -------------------------
        // Buttons
        // -------------------------

        btnManageTime.setOnClickListener {

            startActivity(
                Intent(
                    this,
                    MainActivity::class.java
                )
            )

        }

        btnMyHistory.setOnClickListener {

            startActivity(
                Intent(
                    this,
                    HistoryActivity::class.java
                )
            )

        }

        btnMySchedule.setOnClickListener {

            startActivity(
                Intent(
                    this,
                    ScheduleActivity::class.java
                )
            )

        }

        // -------------------------
        // Long Press
        // -------------------------

        btnMotivation.setOnLongClickListener {

            Toast.makeText(
                this,
                "Long press detected!",
                Toast.LENGTH_SHORT
            ).show()

            true
        }

        // -------------------------
        // Motivation API
        // -------------------------

        btnMotivation.setOnClickListener {

            RetrofitClient.api.getAdvice()

                .enqueue(

                    object : Callback<AdviceResponse> {

                        override fun onResponse(
                            call: Call<AdviceResponse>,
                            response: Response<AdviceResponse>
                        ) {

                            if (response.isSuccessful) {

                                val advice =
                                    response.body()?.slip?.advice

                                Toast.makeText(
                                    this@DashboardActivity,
                                    advice ?: "No advice found",
                                    Toast.LENGTH_LONG
                                ).show()

                            } else {

                                Toast.makeText(
                                    this@DashboardActivity,
                                    "Request Failed",
                                    Toast.LENGTH_SHORT
                                ).show()

                            }

                        }

                        override fun onFailure(
                            call: Call<AdviceResponse>,
                            t: Throwable
                        ) {

                            Toast.makeText(
                                this@DashboardActivity,
                                t.message,
                                Toast.LENGTH_LONG
                            ).show()

                        }

                    }

                )

        }

    }

}