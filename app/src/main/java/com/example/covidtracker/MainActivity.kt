package com.example.covidtracker

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import android.util.Log
import android.view.LayoutInflater
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit
import javax.microedition.khronos.opengles.GL10

class MainActivity : AppCompatActivity() {
    lateinit var stateAdapter: StateAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        listview.addHeaderView(LayoutInflater.from(this).inflate(R.layout.item_header,listview,false))
        fetchResult()
    }

    private fun fetchResult() {
        GlobalScope.launch {
            val response = withContext(Dispatchers.IO){
                Client.api.execute()

            }
            if(response.isSuccessful){
              val data = Gson().fromJson(response.body?.string(),Response::class.java)
                launch(Dispatchers.Main) {
                    bindCombinedData(data.statewise[0])
                    bindstatewise(data.statewise.subList(1,data.statewise.size))
                }
            }
        }

    }

    private fun bindstatewise(subList: List<StatewiseItem>) {
        stateAdapter = StateAdapter(subList)
        listview.adapter = stateAdapter
    }


    private fun bindCombinedData(data: StatewiseItem?) {
        val lastUpdatedTime = data?.lastupdatedtime
        val simpleDateFormat =SimpleDateFormat("dd/MM/yy HH:mm:ss")
        lastupdatedTV.text = "Last Updated\n ${getTimeAgo(simpleDateFormat
            .parse(lastUpdatedTime))}"

        confirmedTv.text = data?.confirmed
        acitveTv.text = data?.active
        recovredTv.text = data?.recovered
        DeceasedTV.text = data?.deaths
    }
    fun getTimeAgo(past:Date):String{
        val now = Date()
        val second = TimeUnit.MILLISECONDS.toSeconds(now.time-past.time)
        val minute = TimeUnit.MILLISECONDS.toMinutes(now.time-past.time)
        val hours = TimeUnit.MILLISECONDS.toHours((now.time-past.time))
        return when{
            second<60->{
                "Few Second ago"
            }
            minute<60->{
                "$minute Minutes ago"
            }
            hours<24->{
                "$hours hours ${minute%60} min ago"
            }
            else -> SimpleDateFormat("dd/MM/yy,hh:mm a").format(past).toString()
        }
    }
}