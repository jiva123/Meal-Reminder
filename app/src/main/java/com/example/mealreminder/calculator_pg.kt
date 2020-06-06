package com.example.mealreminder

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.calculator_pg.*

class calculator_pg : AppCompatActivity() {

    private var backPressedTime: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.calculator_pg)

        ////////////////////

        // intent to bmi
        bmibtn.setOnClickListener { v: View? ->
            val bmi_intent= Intent(this@calculator_pg,bmi_pg::class.java)
            startActivity(bmi_intent)
        }

        ////////////////////

        ////////////////////

        // intent to step counter
        stepsbtn.setOnClickListener { v: View? ->
            val StepCounter_Intent= Intent(this@calculator_pg,step_counter_pg::class.java)
            startActivity(StepCounter_Intent)
        }

        ////////////////////

        ////////////////////////////////
        //Bottom Nav
        var btnv: BottomNavigationView?  = findViewById(R.id.btm)
        btnv!!.setOnNavigationItemSelectedListener(BottomNavigationView.OnNavigationItemSelectedListener { item ->
            val id = item.itemId
            when (id) {
                R.id.cal -> {
                    val intent1 = Intent(this@calculator_pg, calculator_pg::class.java)
                    startActivity(intent1)
                    finish()
                }

                R.id.rmder -> {
                    val intent2 = Intent(this@calculator_pg, remind_pg::class.java)
                    startActivity(intent2)
                    finish()
                }
                R.id.prf -> {
                    val intent3 = Intent(this@calculator_pg, profile_pg::class.java)
                    startActivity(intent3)
                    finish()
                }
            }

            true
        })
        ////////////////////////////////
    }

    /////////////////////////////////////////////
    //back press button code
    @Override
    override fun onBackPressed(){
        var t: Long =System.currentTimeMillis()
        if (t - backPressedTime > 2000){    // 2 sec
            backPressedTime = t
            Toast.makeText(this@calculator_pg,"Press back again to exit", Toast.LENGTH_LONG).show()
        } else run {
            // clean up
            super.onBackPressed()       // Quit
        }
    }
    //////////////////////

}