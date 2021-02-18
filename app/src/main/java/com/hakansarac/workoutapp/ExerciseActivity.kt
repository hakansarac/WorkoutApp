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
    private var currentExercise = -1                    // Current Position of Exercise.

    private var restTimer: CountDownTimer? = null       // Variable for Rest Timer and later on we will initialize it.
    private var restProgress = 10                       // Variable for timer progress. As initial the rest progress is set to 10. As we are about to start

    private var exerciseTimer: CountDownTimer? = null   // Variable for Exercise Timer and later on it will initialized
    private var exerciseProgress = 30                   // Variable for exercise timer progress. As initial the exercise progress is set to 30. As we are about to start.

    private var player : MediaPlayer? = null            // Created a variable for Media Player to use later.
    private var tts: TextToSpeech? = null               // Variable for Text to Speech

    private var exerciseAdapter : ExerciseStatusAdapter? = null     // Declaring a exerciseAdapter object which will be initialized later.

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_exercise)

        setSupportActionBar(toolbarExerciseActivity)
        val actionbar = supportActionBar
        if(actionbar!=null)
            actionbar.setDisplayHomeAsUpEnabled(true)     //back button symbol
        // Navigate the activity on click on back button of action bar.
        toolbarExerciseActivity.setNavigationOnClickListener {
            dialogCustomForBackButton()
        }

        tts = TextToSpeech(this,this)
        exercisesList = Constants.defaultExercisesList()

        setupRestView()
        setupExerciseStatusRecyclerView()
    }

    /**
     * Function is used to set the progress of timer using the progress
     */
    private fun setRestProgressBar(){
        progressBarRest.progress = restProgress     // Sets the current progress to the specified value.
        /**
         * @param millisInFuture The number of millis in the future from the call
         *   to {#start()} until the countdown is done and {#onFinish()}
         *   is called.
         * @param countDownInterval The interval along the way to receive
         *   {#onTick(long)} callbacks.
         */
        // Here we have started a timer of 10 seconds so the 10000 is milliseconds is 10 seconds and the countdown interval is 1 second so it 1000.
        restTimer = object : CountDownTimer(10000,1000){
            override fun onTick(p0: Long) {
                progressBarRest.progress = restProgress
                textViewTimer.text = restProgress.toString()
                restProgress--
            }

            override fun onFinish() {
                currentExercise++
                exercisesList!![currentExercise].setIsSelected(true)    // Current Item is selected
                exerciseAdapter!!.notifyDataSetChanged()        // Notified the current item to adapter class to reflect it into UI.
                setupExerciseView()
            }
        }.start()
    }

    /**
     * Function is used to set the progress of timer using the progress for Exercise View for 30 Seconds
     */
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
                    exercisesList!![currentExercise].setIsSelected(false)   // exercise is completed so selection is set to false
                    exercisesList!![currentExercise].setIsCompleted(true)   // updating in the list that this exercise is completed
                    exerciseAdapter!!.notifyDataSetChanged()                // Notifying to adapter class.
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

    /**
     * Function is used to set the timer for REST.
     */
    private fun setupRestView(){
        /**
         * Here the sound file is added in to "raw" folder in resources.
         * And played using MediaPlayer. MediaPlayer class can be used to control playback
         * of audio/video files and streams.
         */
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

        // Here according to the view make it visible as this is Rest View so rest view is visible and exercise view is not.
        linearLayoutRestView.visibility = View.VISIBLE
        linearLayoutExerciseView.visibility = View.GONE
        /**
         * Here firstly we will check if the timer is running the and it is not null then cancel the running timer and start the new one.
         * And set the progress to initial which is 0.
         */
        if(restTimer!= null){
            restTimer!!.cancel()
            restProgress = 10
        }
        // Here we have set the upcoming exercise name to the text view
        // Here as the current position is -1 by default so to selected from the list it should be 0 so we have increased it by +1.
        textViewUpcomingExercise.text = exercisesList!![currentExercise+1].getName()
        setRestProgressBar()    // This function is used to set the progress details.
    }

    /**
     * Function is used to set the progress of timer using the progress for Exercise View.
     */
    private fun setupExerciseView(){
        // Here according to the view make it visible as this is Exercise View so exercise view is visible and rest view is not.
        linearLayoutRestView.visibility = View.GONE
        linearLayoutExerciseView.visibility = View.VISIBLE
        /**
         * Here firstly we will check if the timer is running the and it is not null then cancel the running timer and start the new one.
         * And set the progress to initial which is 30.
         */
        if(exerciseTimer!= null){
            exerciseTimer!!.cancel()
            exerciseProgress = 30
        }
        /**
         * Here current exercise name and image is set to exercise view.
         */
        speakOut(exercisesList!![currentExercise].getName())
        setExerciseProgressBar()
        imageViewExercise.setImageResource(exercisesList!![currentExercise].getImage())
        textViewExerciseName.text = exercisesList!![currentExercise].getName()
    }

    /**
     * Here is Destroy function we will reset the program to initial if it is running.
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
     * This the TextToSpeech override function
     *
     * Called to signal the completion of the TextToSpeech engine initialization.
     */
    override fun onInit(p0: Int) {
        if(p0 == TextToSpeech.SUCCESS){
            // set English as language for tts
            val result = tts!!.setLanguage(Locale.ENGLISH)
            if(result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED)
                Log.e("TTS","The language specified is not supported")
        }else
            Log.e("TTS","Initialization is failed.")
    }

    /**
     * Function is used to speak the text what we pass to it.
     */
    private fun speakOut(text : String){
        tts!!.speak(text,TextToSpeech.QUEUE_FLUSH,null,"")
    }

    /**
     * Function is used to set up the recycler view to UI and assign the Layout Manager and Adapter Class is attached to it.
     */
    private fun setupExerciseStatusRecyclerView(){
        // Defining a layout manager to recycle view
        // Here we have used Linear Layout Manager with horizontal scroll.
        recyclerViewExerciseStatus.layoutManager = LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false)
        // As the adapter expect the exercises list and context so initialize it passing it.
        exerciseAdapter = ExerciseStatusAdapter(this,exercisesList!!)
        // Adapter class is attached to recycler view
        recyclerViewExerciseStatus.adapter = exerciseAdapter
    }

    /**
     * Function is used to launch the custom confirmation dialog.
     * custom dialog to ask if the user is sure to cancel exercise
     */
    private fun dialogCustomForBackButton(){
        val dialogCustom = Dialog(this)
        /*Set the screen content from a layout resource.
         The resource will be inflated, adding all top-level views to the screen.*/
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