package com.example.mealreminder

import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.profile_pg.*
import java.util.*
import android.app.DatePickerDialog
import android.content.Intent
import androidx.appcompat.widget.Toolbar
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.android.material.bottomnavigation.BottomNavigationView


class profile_pg : AppCompatActivity() {

    private var backPressedTime: Long = 0
    private var nm: TextInputEditText? = null
    private var dob: TextInputEditText? = null
    private var tat: TextView? = null
    private var wht: TextInputEditText? = null
    private var htg: TextInputEditText? = null
    private var phn: TextInputEditText? = null
    private var m: RadioButton? = null
    private var f: RadioButton? = null
    private var gender: RadioGroup? = null

    private var firebaseAuth: FirebaseAuth? = null
    private var currentUser: FirebaseUser? = null
    private var firebaseDatabase: FirebaseDatabase? = null
    private var databaseReference: DatabaseReference? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.profile_pg)

        //setup toolbar
        var toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        firebaseAuth = FirebaseAuth.getInstance()
        currentUser = firebaseAuth!!.getCurrentUser()
        firebaseDatabase = FirebaseDatabase.getInstance()
        databaseReference = firebaseDatabase!!.getReference("users")

        nm = findViewById(R.id.usname) as TextInputEditText
        dob = findViewById(R.id.usdob) as TextInputEditText
        wht = findViewById(R.id.usweight) as TextInputEditText
        htg = findViewById(R.id.usheight) as TextInputEditText
        phn = findViewById(R.id.usphn) as TextInputEditText
        m = findViewById(R.id.man) as RadioButton
        f = findViewById(R.id.woman) as RadioButton
        gender = findViewById(R.id.rdg) as RadioGroup
        tat = findViewById(R.id.gen) as TextView


        // userInfo
        val query = databaseReference!!.orderByChild("email").equalTo(currentUser!!.getEmail())
        query.addValueEventListener(object : ValueEventListener {
            //
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                //check until req data get
                for (ds in dataSnapshot.children) {
                    val name = "" + ds.child("name").value!!
                    val age = "" + ds.child("age").value!!
                    val weight = "" + ds.child("weight").value!!
                    val gender = "" + ds.child("gender").value!!
                    val height = "" + ds.child("height").value!!
                    val phone_no = "" + ds.child("phone no").value!!

                    //set data
                    nm!!.setText(name)
                    dob!!.setText(age)
                    wht!!.setText(weight)
                    htg!!.setText(height)
                    tat!!.setText(gender)
                    phn!!.setText(phone_no)
                }
            }


            override fun onCancelled(databaseError: DatabaseError) {

            }
        })

        dobclk.setOnClickListener { v: View? ->
            // Get Current Date
            val c: Calendar? = Calendar.getInstance()
            val mYear = c!!.get(Calendar.YEAR)
            val mMonth = c.get(Calendar.MONTH)
            val mDay = c.get(Calendar.DAY_OF_MONTH)

            val datePickerDialog = DatePickerDialog(
                this,
                DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                    dob!!.setText(
                        dayOfMonth.toString() + "-" + monthOfYear + "-" + year
                    )
                }, mYear, mMonth, mDay
            )
            datePickerDialog.show()
        }

        proceedbtn.setOnClickListener { v: View? ->
            // fun post
            post()

        }
        ////////////////////////////////
        // Bottom Nav
        var btnv: BottomNavigationView?  = findViewById(R.id.btm)
        btnv!!.setOnNavigationItemSelectedListener(BottomNavigationView.OnNavigationItemSelectedListener { item ->
            val id = item.itemId
            when (id) {
                R.id.cal -> {
                    val intent1 = Intent(this@profile_pg, calculator_pg::class.java)
                    startActivity(intent1)
                    finish()
                }

                R.id.rmder -> {
                    val intent2 = Intent(this@profile_pg, remind_pg::class.java)
                    startActivity(intent2)
                    finish()
                }
                R.id.prf -> {
                    val intent3 = Intent(this@profile_pg, profile_pg::class.java)
                    startActivity(intent3)
                    finish()
                }
            }

            true
        })
        ////////////////////////////////

    }

    // created post()
    private fun post() {

        val selectedId = gender!!.getCheckedRadioButtonId()
        if (selectedId == m!!.getId()) {
            tat!!.setText("Male")
        }
        if (selectedId == f!!.getId()) {
            tat!!.setText("Female")
        }

        val name = nm!!.getText().toString()
        val age = dob!!.getText()!!.toString()
        val gender = tat!!.getText().toString()
        val height = htg!!.getText()!!.toString()
        val weight = wht!!.getText()!!.toString()
        val phone_no = phn!!.getText()!!.toString()

        if (nm!!.getText()!!.toString().length == 0)
            nm!!.setError("Name cannot be blank!")

        if (dob!!.getText()!!.toString().length == 0)
            dob!!.setError("Age cannot be blank!")

        if (htg!!.getText()!!.toString().length == 0)
            htg!!.setError("Height cannot be blank!")

        if (wht!!.getText()!!.toString().length == 0)
            wht!!.setError("Weight cannot be blank!")

        if (phn!!.getText()!!.toString().length == 0)
            phn!!.setError("Phone no cannot be blank!")
        else {

            val userData = HashMap<String, Any>()
            userData["name"] = name
            userData["age"] = age
            userData["gender"] = gender
            userData["weight"] = weight
            userData["height"] = height
            userData["phone no"] = phone_no
            databaseReference!!.child(currentUser!!.getUid()).updateChildren(userData)
                .addOnSuccessListener(OnSuccessListener<Void> {
                    Toast.makeText(
                        this@profile_pg,
                        "updated......",
                        Toast.LENGTH_SHORT
                    ).show()
                }).addOnFailureListener(
                    OnFailureListener { e ->
                        Toast.makeText(
                            this@profile_pg,
                            "" + e.message,
                            Toast.LENGTH_SHORT
                        ).show()
                    })
        }

    }


    /////////////////////////////////////////////
    //back press button code
    @Override
    override fun onBackPressed(){
        var t: Long =System.currentTimeMillis()
        if (t - backPressedTime > 2000){    // 2 sec
            backPressedTime = t
            Toast.makeText(this@profile_pg,"Press back again to exit", Toast.LENGTH_LONG).show()
        } else run {
            // clean up
            super.onBackPressed()       // Quit
        }
    }
    //////////////////////
}