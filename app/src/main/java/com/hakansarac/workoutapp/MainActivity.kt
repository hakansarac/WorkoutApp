package com.hakansarac.workoutapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        linearLayoutStart.setOnClickListener {
            Toast.makeText(this,"Here we will start the exercise",Toast.LENGTH_SHORT).show()
        }
    }
}