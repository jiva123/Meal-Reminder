package com.example.mealreminder

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.tasks.*
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.reg_pg.*
import java.util.HashMap

class reg_pg : AppCompatActivity() {

    private var mAuth:FirebaseAuth?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.reg_pg)

        mAuth = FirebaseAuth.getInstance()

        btnsnup_rg.setOnClickListener(View.OnClickListener { v: View? ->
            val Email = emltxt_rg.getText().toString()
            val Pass = passtxt_rg.getText().toString()
            val confpass = cnf_rg.getText().toString()

            if(Email.isEmpty()){
                emltxt_rg.requestFocus()
                emltxt_rg.setError("Email Address is required")
                return@OnClickListener
            }

            if(!Patterns.EMAIL_ADDRESS.matcher(Email).matches())
            {
                emltxt_rg.requestFocus()
                emltxt_rg.setError("Enter a valid Email Address")
                return@OnClickListener
            }

            if(Pass.isEmpty())
            {
                passtxt_rg.requestFocus()
                passtxt_rg.setError("Password is required")
                return@OnClickListener
            }

            if(Pass.length<8){
                passtxt_rg.requestFocus()
                passtxt_rg.setError("Password minimum 8 characters!")
                return@OnClickListener
            }

            else if (Pass != confpass){
                cnf_rg.requestFocus()
                cnf_rg.setError("Password not matching!")
                return@OnClickListener
            }

            progressBar1.setVisibility(View.VISIBLE)

            mAuth!!.createUserWithEmailAndPassword(Email!!, Pass!!)
                .addOnCompleteListener(this@reg_pg,
                    OnCompleteListener<AuthResult> { task ->
//                        Toast.makeText(this@reg_pg, "createUserWithEmail", Toast.LENGTH_SHORT)
//                            .show()
                        progressBar1.setVisibility(View.GONE)
                        if (!task.isSuccessful) {

                            Toast.makeText(
                                this@reg_pg,
                                "Authentication failed.",
                                Toast.LENGTH_SHORT
                            ).show()

                        } else {

                            val user = mAuth!!.currentUser!!
                            //get user email&uid frm auth
                            val email = user?.email
                            val uid = user?.uid
                            //whn usr is reg str usr info in fb realtime db too
                            //usng hashmap
                            val hashMap = HashMap<Any, String?>()
                            //put info in hashmap
                            hashMap["email"] = email
                            hashMap["uid"] = uid
                            hashMap["name"] = ""
                            hashMap["age"] = ""
                            hashMap["gender"] = ""
                            hashMap["weight"] = ""
                            hashMap["height"] = ""
                            hashMap["phone no"] = ""
                            //fb db instance
                            val db = FirebaseDatabase.getInstance()
                            //path yo store user data named  "users"
                            val rfn = db.getReference("users")
                            //put data within hashmap in db
                            rfn.child(uid).setValue(hashMap)

                            updateUserInfoAndUI()
                        }
                    })
        })
    }

    @Override
    override fun onResume(){
        super.onResume()
        progressBar1.setVisibility(View.GONE)
    }

    private fun updateUserInfoAndUI() {

        //start next activity
        val intent = Intent(this@reg_pg, remind_pg::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
        finish()
    }


}