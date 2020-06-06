package com.example.mealreminder

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.bmi_pg.*

class bmi_pg : AppCompatActivity() {

    private var firebaseAuth: FirebaseAuth? = null
    private var currentUser: FirebaseUser? = null
    private var firebaseDatabase: FirebaseDatabase? = null
    private var databaseReference: DatabaseReference? = null

    private var wht: TextInputEditText? = null
    private var htg: TextInputEditText? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.bmi_pg)

        firebaseAuth = FirebaseAuth.getInstance()
        currentUser = firebaseAuth!!.getCurrentUser()
        firebaseDatabase = FirebaseDatabase.getInstance()
        databaseReference = firebaseDatabase!!.getReference("users")

        wht = findViewById(R.id.wghtkg) as TextInputEditText
        htg = findViewById(R.id.hgtcm) as TextInputEditText

        // userInfo weight nd height
        val query = databaseReference!!.orderByChild("email").equalTo(currentUser!!.getEmail())
        query.addValueEventListener(object : ValueEventListener {
            //
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                //check until req data get
                for (ds in dataSnapshot.children) {

                    val weight = "" + ds.child("weight").value!!
                    val height = "" + ds.child("height").value!!

                    //set data
                    wht!!.setText(weight)
                    htg!!.setText(height)
                }
            }


            override fun onCancelled(databaseError: DatabaseError) {

            }
        })

        ///////////////////////////////

        // bmi calculation
        bmical.setOnClickListener { v: View? ->
            val height = hgtcm.text.toString().toFloat() / 100
            val weight = wghtkg.text.toString().toFloat()
            val bmi_result = weight/(height*height)
            bmirslt.text = "%.2f".format(bmi_result)
            bmirslt_ll.visibility = View.VISIBLE
        }

        //////////////////////////////
    }
}