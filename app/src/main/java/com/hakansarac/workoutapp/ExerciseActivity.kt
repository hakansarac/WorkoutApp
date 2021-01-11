package com.hakansarac.workoutapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_exercise.*

class ExerciseActivity : AppCompatActivity() {

    private var exercisesList : ArrayList<Exercise>? = null
    private var currentExercise = -1

    private var restTimer: CountDownTimer? = null
    private var restProgress = 10

    private var exerciseTimer: CountDownTimer? = null
    private var exerciseProgress = 30

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

        exercisesList = Constants.defaultExercisesList()

        setupRestView()
    }

    private fun setRestProgressBar(){
        progressBarRest.progress = restProgress
        restTimer = object : CountDownTimer(10000,1000){
            override fun onTick(p0: Long) {
                progressBarRest.progress = restProgress
                textViewTimer.text = restProgress.toString()
                restProgress--
            }

            override fun onFinish() {
                currentExercise++
                setupExerciseView()
            }
        }.start()
    }

    private fun setExerciseProgressBar(){
        progressBarExercise.progress = exerciseProgress
        exerciseTimer = object : CountDownTimer(30000,1000){
            override fun onTick(p0: Long) {
                progressBarExercise.progress = exerciseProgress
                textViewExerciseTimer.text = exerciseProgress.toString()
                exerciseProgress--
            }

            override fun onFinish() {
                if(currentExercise < exercisesList?.size!!-1)
                    setupRestView()
                else
                    Toast.makeText(this@ExerciseActivity,"Congratulations!",Toast.LENGTH_SHORT).show()
            }
        }.start()
    }



    private fun setupRestView(){
        linearLayoutRestView.visibility = View.VISIBLE
        linearLayoutExerciseView.visibility = View.GONE
        if(restTimer!= null){
            restTimer!!.cancel()
            restProgress = 10
        }
        textViewUpcomingExercise.text = exercisesList!![currentExercise+1].getName()
        setRestProgressBar()
    }

    private fun setupExerciseView(){
        linearLayoutRestView.visibility = View.GONE
        linearLayoutExerciseView.visibility = View.VISIBLE
        if(exerciseTimer!= null){
            exerciseTimer!!.cancel()
            exerciseProgress = 30
        }
        setExerciseProgressBar()
        imageViewExercise.setImageResource(exercisesList!![currentExercise].getImage())
        textViewExerciseName.text = exercisesList!![currentExercise].getName()
    }

    override fun onDestroy() {
        if(restTimer!= null){
            restTimer!!.cancel()
            restProgress = 10
        }
        super.onDestroy()
    }
}