package com.hakansarac.workoutapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_bmi.*
import kotlinx.android.synthetic.main.activity_history.*

class HistoryActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)

        setSupportActionBar(toolbarHistoryActivity)
        val actionBar = supportActionBar
        if(actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.title = "HISTORY"
        }
        toolbarHistoryActivity.setNavigationOnClickListener {
            onBackPressed()
        }

        getDatesList()
    }

    private fun getDatesList(){
        val dbHandler = SqliteOpenHelper(this,null)
        val allDates = dbHandler.getAllCompletedDates()

        if(allDates.size>0){
            textViewHistory.visibility = View.VISIBLE
            recyclerViewHistory.visibility = View.VISIBLE
            textViewNoDataAvailable.visibility = View.GONE

            recyclerViewHistory.layoutManager = LinearLayoutManager(this)
            val historyAdapter = HistoryAdapter(this,allDates)
            recyclerViewHistory.adapter = historyAdapter
        }else{
            textViewHistory.visibility = View.GONE
            recyclerViewHistory.visibility = View.GONE
            textViewNoDataAvailable.visibility = View.VISIBLE
        }



    }
}