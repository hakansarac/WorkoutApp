package com.hakansarac.workoutapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_bmi.*
import java.math.BigDecimal
import java.math.RoundingMode

class BmiActivity : AppCompatActivity() {

    val METRIC_UNITS_VIEW = "METRIC_UNIT_VIEW"
    val US_UNITS_VIEW = "US_UNIT_VIEW"

    var currentVisibleView : String = METRIC_UNITS_VIEW     // A variable to hold a value to make visible a selected view

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bmi)

        setSupportActionBar(toolbarBmiActivity)
        val actionBar = supportActionBar
        if(actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true)       // set back button
            actionBar.title = "CALCULATE BMI"               // Setting a title in the action bar.
        }
        toolbarBmiActivity.setNavigationOnClickListener {
            onBackPressed()
        }


        buttonCalculator.setOnClickListener {
            if(currentVisibleView == METRIC_UNITS_VIEW) {
                if (validateMetricUnits()) {
                    val weightMetricInput: Float = editTextMetricWeight.text.toString().toFloat()
                    val heightMetricInput: Float = editTextMetricHeight.text.toString().toFloat() / 100      //from cm to m
                    val bmi = weightMetricInput / (heightMetricInput * heightMetricInput)
                    displayBmiResult(bmi)
                }
            }else{
                if(validateUsUnits()){
                    val weightUsInput : Float = editTextUsWeight.text.toString().toFloat()
                    val feetUsInput : String = editTextUsHeightFeet.text.toString()
                    val inchUsInput : String = editTextUsHeightInch.text.toString()

                    val heightValue = inchUsInput.toFloat() + feetUsInput.toFloat() * 12
                    val bmi = 703 * (weightUsInput / (heightValue * heightValue))
                    displayBmiResult(bmi)
                }
            }
        }

        makeVisibleMetric()     //select the unit as metric by default
        //then check the changes of selected element of radio group
        radioGroupUnits.setOnCheckedChangeListener{group,checkedId ->
            // Here is the checkId is METRIC UNITS view then make the view visible else US UNITS view.
            when(checkedId){
                R.id.radioButtonMetric -> makeVisibleMetric()
                else -> makeVisibleUs()
            }
        }
    }

    /**
     * check if all of the blanks are filled
     */
    private fun validateMetricUnits():Boolean{
        var isValid = true

        if(editTextMetricWeight.text.toString().isEmpty() || editTextMetricHeight.text.toString().isEmpty()){
            isValid = false
            Toast.makeText(this,"Please enter both of values correctly.",Toast.LENGTH_SHORT).show()
        }
        return isValid
    }

    /**
     * check if all of the blanks are filled
     */
    private fun validateUsUnits():Boolean{
        var isValid = true

        if(editTextUsHeightFeet.text.toString().isEmpty() || editTextUsHeightInch.text.toString().isEmpty() || editTextUsWeight.text.toString().isEmpty()) {
            isValid = false
            Toast.makeText(this,"Please enter all of values correctly.",Toast.LENGTH_SHORT).show()
        }
        return isValid
    }

    /**
     * check the weight and height;
     * give a message according to calculated values
     */
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

        linearLayoutBmiResult.visibility = View.VISIBLE

        val bmiResult = BigDecimal(bmi.toDouble()).setScale(2,RoundingMode.HALF_EVEN).toString()

        textViewBmiResult.text = bmiResult
        textViewBmiType.text = bmiLabel
        textViewBmiDescription.text = bmiDescription
    }

    /**
     * set the values as Metric Unit type
     */
    private fun makeVisibleMetric(){
        currentVisibleView = METRIC_UNITS_VIEW

        editTextMetricHeight.text?.clear()
        editTextMetricWeight.text?.clear()
        textInputLayoutMetricHeight.visibility = View.VISIBLE
        textInputLayoutMetricWeight.visibility = View.VISIBLE

        linearLayoutUsUnitHeight.visibility = View.GONE
        textInputLayoutUsWeight.visibility = View.GONE

        linearLayoutBmiResult.visibility = View.INVISIBLE
    }

    /**
     * set the values as Us Unit Type
     */
    private fun makeVisibleUs(){
        currentVisibleView = US_UNITS_VIEW

        textInputLayoutMetricHeight.visibility = View.GONE
        textInputLayoutMetricWeight.visibility = View.GONE

        editTextUsHeightFeet.text?.clear()
        editTextUsHeightInch.text?.clear()
        editTextUsWeight.text?.clear()
        linearLayoutUsUnitHeight.visibility = View.VISIBLE
        textInputLayoutUsWeight.visibility = View.VISIBLE

        linearLayoutBmiResult.visibility = View.INVISIBLE
    }
}