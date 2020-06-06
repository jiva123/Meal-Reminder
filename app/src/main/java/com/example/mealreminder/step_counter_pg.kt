package com.example.mealreminder

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.step_counter_pg.*

class step_counter_pg: AppCompatActivity(),SensorEventListener {

    private var sensorManager: SensorManager? = null
    private var running =false
    private var totalSteps = 0f
    private var previousToatalSteps =0f

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.step_counter_pg)

        loadData()
        resetSteps()

        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager

    }

    override fun onResume() {
        super.onResume()
        running = true
        val stepSensor = sensorManager?.getDefaultSensor(Sensor.TYPE_STEP_COUNTER)

        if (stepSensor == null){
            Toast.makeText(this,"No sensor detected to this device", Toast.LENGTH_SHORT).show()
        }
        else{
            sensorManager?.registerListener(this, stepSensor, SensorManager.SENSOR_DELAY_UI)

        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
    }

    override fun onSensorChanged(event: SensorEvent?) {
        if (running){
            totalSteps = event!!.values[0]
            val currensteps = totalSteps.toInt() - previousToatalSteps.toInt()
            stpcount.text = ("$currensteps")

            circularProgressBar.apply {
                setProgressWithAnimation(currensteps.toFloat())
            }

        }
    }

    private fun resetSteps(){

        stpcount.setOnClickListener {
            previousToatalSteps = totalSteps
            stpcount.text = 0.toString()
            saveData()

        }
    }

    private fun saveData() {
        val sharedPreferences = getSharedPreferences("myPref", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putFloat("key1", previousToatalSteps)
        editor.apply()
    }

    private fun loadData(){
        val sharedPreferences = getSharedPreferences("myPref", Context.MODE_PRIVATE)
        val savedNumber = sharedPreferences.getFloat("key1", 0f)
        Log.d("step_counter_pg","$savedNumber")
        previousToatalSteps = savedNumber
    }

}