package com.example.focusmatepro

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class ScheduleActivity : AppCompatActivity() {

    lateinit var db: ScheduleDatabaseHelper

    lateinit var btnAction: Button

    lateinit var edits: List<EditText>

    var saved = true

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_schedule)

        db = ScheduleDatabaseHelper(this)

        btnAction = findViewById(R.id.btnAction)

        edits = listOf(

            // MON
            findViewById(R.id.etMon1),
            findViewById(R.id.etMon2),
            findViewById(R.id.etMon3),
            findViewById(R.id.etMon4),
            findViewById(R.id.etMon5),
            findViewById(R.id.etMon6),
            findViewById(R.id.etMon7),
            findViewById(R.id.etMon8),

            // TUE
            findViewById(R.id.etTue1),
            findViewById(R.id.etTue2),
            findViewById(R.id.etTue3),
            findViewById(R.id.etTue4),
            findViewById(R.id.etTue5),
            findViewById(R.id.etTue6),
            findViewById(R.id.etTue7),
            findViewById(R.id.etTue8),

            // WED
            findViewById(R.id.etWed1),
            findViewById(R.id.etWed2),
            findViewById(R.id.etWed3),
            findViewById(R.id.etWed4),
            findViewById(R.id.etWed5),
            findViewById(R.id.etWed6),
            findViewById(R.id.etWed7),
            findViewById(R.id.etWed8),

            // THU
            findViewById(R.id.etThu1),
            findViewById(R.id.etThu2),
            findViewById(R.id.etThu3),
            findViewById(R.id.etThu4),
            findViewById(R.id.etThu5),
            findViewById(R.id.etThu6),
            findViewById(R.id.etThu7),
            findViewById(R.id.etThu8),

            // FRI
            findViewById(R.id.etFri1),
            findViewById(R.id.etFri2),
            findViewById(R.id.etFri3),
            findViewById(R.id.etFri4),
            findViewById(R.id.etFri5),
            findViewById(R.id.etFri6),
            findViewById(R.id.etFri7),
            findViewById(R.id.etFri8),

            // SAT
            findViewById(R.id.etSat1),
            findViewById(R.id.etSat2),
            findViewById(R.id.etSat3),
            findViewById(R.id.etSat4),
            findViewById(R.id.etSat5),
            findViewById(R.id.etSat6),
            findViewById(R.id.etSat7),
            findViewById(R.id.etSat8),

            // SUN
            findViewById(R.id.etSun1),
            findViewById(R.id.etSun2),
            findViewById(R.id.etSun3),
            findViewById(R.id.etSun4),
            findViewById(R.id.etSun5),
            findViewById(R.id.etSun6),
            findViewById(R.id.etSun7),
            findViewById(R.id.etSun8)

        )

        val columns=arrayOf(

            "mon1","mon2","mon3","mon4","mon5","mon6","mon7","mon8",

            "tue1","tue2","tue3","tue4","tue5","tue6","tue7","tue8",

            "wed1","wed2","wed3","wed4","wed5","wed6","wed7","wed8",

            "thu1","thu2","thu3","thu4","thu5","thu6","thu7","thu8",

            "fri1","fri2","fri3","fri4","fri5","fri6","fri7","fri8",

            "sat1","sat2","sat3","sat4","sat5","sat6","sat7","sat8",

            "sun1","sun2","sun3","sun4","sun5","sun6","sun7","sun8"

        )

        for (i in edits.indices) {

            edits[i].setText(

                db.getCell(columns[i])

            )

        }

        for (edit in edits) {

            edit.addTextChangedListener(

                object : TextWatcher {

                    override fun beforeTextChanged(
                        s: CharSequence?,
                        start: Int,
                        count: Int,
                        after: Int
                    ) {
                    }

                    override fun onTextChanged(
                        s: CharSequence?,
                        start: Int,
                        before: Int,
                        count: Int
                    ) {

                        if (saved) {

                            btnAction.text = "SAVE"

                            saved = false

                        }

                    }

                    override fun afterTextChanged(s: Editable?) {}

                }

            )

        }

        btnAction.text = "EDIT"

        btnAction.setOnClickListener {

            if (btnAction.text == "SAVE") {

                for (i in edits.indices) {

                    db.saveCell(

                        columns[i],

                        edits[i].text.toString()

                    )

                }

                Toast.makeText(

                    this,

                    "Schedule Saved",

                    Toast.LENGTH_SHORT

                ).show()

                btnAction.text = "EDIT"

                saved = true

            }

        }

    }

}