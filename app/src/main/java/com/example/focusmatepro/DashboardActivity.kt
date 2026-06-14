package com.example.focusmatepro

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DashboardActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_dashboard)


        val btnManageTime=findViewById<Button>(R.id.btnManageTime)

        val btnMyHistory=findViewById<Button>(R.id.btnMyHistory)

        val btnMySchedule=findViewById<Button>(R.id.btnMySchedule)

        val btnMotivation=findViewById<Button>(R.id.btnMotivation)



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

        btnMotivation.setOnClickListener {

            RetrofitClient.api.getAdvice()

                .enqueue(

                    object : Callback<AdviceResponse>{

                        override fun onResponse(

                            call: Call<AdviceResponse>,

                            response: Response<AdviceResponse>

                        ) {

                            if(response.isSuccessful){

                                val advice=

                                    response.body()?.slip?.advice

                                Toast.makeText(

                                    this@DashboardActivity,

                                    advice ?: "No advice found",

                                    Toast.LENGTH_LONG

                                ).show()

                            }

                            else{

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