package com.hakansarac.workoutapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_finish.*

class FinishActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_finish)

        setSupportActionBar(toolbarFinish)
        val actionBar = supportActionBar
        if(actionBar!= null)
            actionBar.setDisplayHomeAsUpEnabled(true)   //back button

        toolbarFinish.setNavigationOnClickListener {
            onBackPressed()
        }

        buttonFinish.setOnClickListener {
            finish()
        }
    }
}