package com.hakansarac.workoutapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_exercise.*

class ExerciseActivity : AppCompatActivity() {

    private var restTimer: CountDownTimer? = null
    private var restProgress = 10

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_exercise)

        setSupportActionBar(toolbarExerciseActivity)
        val actionbar = supportActionBar
        if(actionbar!=null)
            actionbar.setDisplayHomeAsUpEnabled(true)

        toolbarExerciseActivity.setNavigationOnClickListener {
            onBackPressed()
        }

        setupRestView()
    }

    private fun setProgressBar(){
        progressBar.progress = restProgress
        restTimer = object : CountDownTimer(10000,1000){
            override fun onTick(p0: Long) {
                progressBar.progress = restProgress
                textViewTimer.text = restProgress.toString()
                restProgress--
            }

            override fun onFinish() {
                //TODO: add exercises here
            }
        }.start()
    }

    private fun setupRestView(){
        if(restTimer!= null){
            restTimer!!.cancel()
            restProgress = 10
        }
        setProgressBar()
    }

    override fun onDestroy() {
        if(restTimer!= null){
            restTimer!!.cancel()
            restProgress = 10
        }
        super.onDestroy()
    }
}