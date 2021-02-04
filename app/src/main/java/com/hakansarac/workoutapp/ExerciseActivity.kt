package com.hakansarac.workoutapp

import android.app.Dialog
import android.content.Intent
import android.media.MediaPlayer
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.speech.tts.TextToSpeech
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_exercise.*
import kotlinx.android.synthetic.main.dialog_custom_back_confirmation.*
import java.util.*
import kotlin.collections.ArrayList

class ExerciseActivity : AppCompatActivity(), TextToSpeech.OnInitListener {

    private var exercisesList : ArrayList<Exercise>? = null
    private var currentExercise = -1

    private var restTimer: CountDownTimer? = null
    private var restProgress = 10

    private var exerciseTimer: CountDownTimer? = null
    private var exerciseProgress = 30

    private var player : MediaPlayer? = null
    private var tts: TextToSpeech? = null

    private var exerciseAdapter : ExerciseStatusAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_exercise)

        setSupportActionBar(toolbarExerciseActivity)
        val actionbar = supportActionBar
        if(actionbar!=null)
            actionbar.setDisplayHomeAsUpEnabled(true)     //back button symbol

        toolbarExerciseActivity.setNavigationOnClickListener {
            dialogCustomForBackButton()
        }

        tts = TextToSpeech(this,this)
        exercisesList = Constants.defaultExercisesList()

        setupRestView()
        setupExerciseStatusRecyclerView()
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
                exercisesList!![currentExercise].setIsSelected(true)
                exerciseAdapter!!.notifyDataSetChanged()
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
                if(currentExercise < exercisesList?.size!!-1) {
                    exercisesList!![currentExercise].setIsSelected(false)
                    exercisesList!![currentExercise].setIsCompleted(true)
                    exerciseAdapter!!.notifyDataSetChanged()
                    setupRestView()
                }
                else {
                    finish()
                    val intent = Intent(this@ExerciseActivity,FinishActivity::class.java)
                    startActivity(intent)
                }
            }
        }.start()
    }



    private fun setupRestView(){

        try{
            //val soundURI = Uri.parse("android:resource://com.hakansarac.workoutapp/"+R.raw.press_start)
            player = MediaPlayer.create(applicationContext,R.raw.press_start)
            if(player!=null) {
                player!!.isLooping = false
                player!!.start()
            }
        }catch(e:Exception){
            e.printStackTrace()
        }

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
        speakOut(exercisesList!![currentExercise].getName())
        setExerciseProgressBar()
        imageViewExercise.setImageResource(exercisesList!![currentExercise].getImage())
        textViewExerciseName.text = exercisesList!![currentExercise].getName()
    }

    /**
     * What to do if the activity is destroyed
     */
    override fun onDestroy() {
        if(restTimer!= null){
            restTimer!!.cancel()
            restProgress = 10
        }

        if(exerciseTimer!= null){
            exerciseTimer!!.cancel()
            exerciseProgress = 30
        }

        if(tts!=null){
            tts!!.stop()
            tts!!.shutdown()
        }

        if(player!=null){
            player!!.stop()
        }

        super.onDestroy()
    }

    /**
     * initialize of TexttoSpeech
     */
    override fun onInit(p0: Int) {
        if(p0 == TextToSpeech.SUCCESS){
            val result = tts!!.setLanguage(Locale.ENGLISH)
            if(result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED)
                Log.e("TTS","The language specified is not supported")
        }else
            Log.e("TTS","Initialization is failed.")
    }

    /**
     * voice prompt to say exercise name
     */
    private fun speakOut(text : String){
        tts!!.speak(text,TextToSpeech.QUEUE_FLUSH,null,"")
    }

    private fun setupExerciseStatusRecyclerView(){
        recyclerViewExerciseStatus.layoutManager = LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false)
        exerciseAdapter = ExerciseStatusAdapter(this,exercisesList!!)
        recyclerViewExerciseStatus.adapter = exerciseAdapter
    }

    /**
     * custom dialog to ask if the user is sure to cancel exercise
     */
    private fun dialogCustomForBackButton(){
        val dialogCustom = Dialog(this)
        dialogCustom.setContentView(R.layout.dialog_custom_back_confirmation)

        dialogCustom.textViewYes.setOnClickListener {
            finish()
            dialogCustom.dismiss()
        }
        dialogCustom.textViewNo.setOnClickListener {
            dialogCustom.dismiss()
        }
        dialogCustom.show()
    }
}