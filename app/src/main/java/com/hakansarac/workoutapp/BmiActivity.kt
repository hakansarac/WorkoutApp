package com.hakansarac.workoutapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_bmi.*
import java.math.BigDecimal
import java.math.RoundingMode

class BmiActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bmi)

        setSupportActionBar(toolbarBmiActivity)
        val actionBar = supportActionBar
        if(actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.title = "CALCULATE BMI"
        }
        toolbarBmiActivity.setNavigationOnClickListener {
            onBackPressed()
        }


        buttonCalculateBmi.setOnClickListener {
            if (validateMetricUnits()){
                val weightInput : Float = editTextMetricWeight.text.toString().toFloat()
                val heightInput : Float = editTextMetricHeight.text.toString().toFloat()/100      //from cm to m
                val bmi = weightInput/(heightInput*heightInput)
                displayBmiResult(bmi)
            }
        }
    }

    private fun validateMetricUnits():Boolean{
        var isValid = true

        if(editTextMetricWeight.text.toString().isEmpty() || editTextMetricHeight.text.toString().isEmpty()){
            isValid = false
            Toast.makeText(this,"Please enter both of values correctly.",Toast.LENGTH_SHORT).show()
        }
        return isValid
    }

    private fun displayBmiResult(bmi:Float){
        val bmiLabel : String
        val bmiDescription : String
        when{
            bmi.compareTo(15f) <= 0 -> {
                bmiLabel = "Very severely underweight"
                bmiDescription = "Oops! You really need to take better care of yourself! Eat more!"
            }
            bmi.compareTo(15f) > 0 && bmi.compareTo(16f) <= 0 -> {
                bmiLabel = "Severely underweight"
                bmiDescription = "Oops! You really need to take better care of yourself! Eat more!"
            }
            bmi.compareTo(16f) > 0 && bmi.compareTo(18.5f) <= 0 -> {
                bmiLabel = "Underweight"
                bmiDescription = "Oops! You really need to take better care of yourself! Eat more!"
            }
            bmi.compareTo(18.5f) > 0 && bmi.compareTo(25f) <= 0 -> {
                bmiLabel = "Normal"
                bmiDescription = "Congratulations! You are in a good shape!"
            }
            bmi.compareTo(25f) > 0 && bmi.compareTo(30f) <= 0 -> {
                bmiLabel = "Overweight"
                bmiDescription = "Oops! You really need to take care of your yourself! Workout maybe!"
            }
            bmi.compareTo(30f) > 0 && bmi.compareTo(35f) <= 0 -> {
                bmiLabel = "Obese Class | (Moderately obese)"
                bmiDescription = "Oops! You really need to take care of your yourself! Workout maybe!"
            }
            bmi.compareTo(35f) > 0 && bmi.compareTo(40f) <= 0 -> {
                bmiLabel = "Obese Class || (Severely obese)"
                bmiDescription = "OMG! You are in a very dangerous condition! Act now!"
            }
            else -> {
                bmiLabel = "Obese Class ||| (Very Severely obese)"
                bmiDescription = "OMG! You are in a very dangerous condition! Act now!"
            }
        }
        textViewYourResult.visibility = View.VISIBLE
        textViewBmiResult.visibility = View.VISIBLE
        textViewBmiType.visibility = View.VISIBLE
        textViewBmiDescription.visibility = View.VISIBLE

        val bmiResult = BigDecimal(bmi.toDouble()).setScale(2,RoundingMode.HALF_EVEN).toString()

        textViewBmiResult.text = bmiResult
        textViewBmiType.text = bmiLabel
        textViewBmiDescription.text = bmiDescription
    }
}