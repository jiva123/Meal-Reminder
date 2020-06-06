package com.example.mealreminder

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.util.Patterns
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.login_pg.*
import java.util.HashMap

class login_pg : AppCompatActivity() {

    private val TAG = "LoginActivity"
    private var backPressedTime: Long = 0
    private var mAuth:FirebaseAuth?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.login_pg)

        mAuth =FirebaseAuth.getInstance()

        btn_lg.setOnClickListener(View.OnClickListener { v: View? ->
            val loginEmail = emltxt?.text.toString()
            val loginPass = passtxt?.text.toString()

            if(loginEmail.isEmpty()){
                emltxt.requestFocus()
                emltxt.setError("Email Address is required")
                return@OnClickListener
            }

            if(!Patterns.EMAIL_ADDRESS.matcher(loginEmail).matches())
            {
                emltxt.requestFocus()
                emltxt.setError("Enter a valid Email Address")
                return@OnClickListener
            }

            if(loginPass.isEmpty())
            {
                passtxt.requestFocus()
                passtxt.setError("Password is required")
                return@OnClickListener
            }

            if(loginPass.length<8){
                passtxt.requestFocus()
                passtxt.setError("Password minimum 8 characters!")
                return@OnClickListener
            }

            if (!TextUtils.isEmpty(loginEmail) && !TextUtils.isEmpty(loginPass)) {
                progressBar2.setVisibility(View.VISIBLE)
                mAuth!!.signInWithEmailAndPassword(loginEmail!!, loginPass!!)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            // Sign in success, update UI with signed-in user's information
                            Log.d(TAG, "signInWithEmail:success")
                            val user = mAuth!!.getCurrentUser()

                            if (task.result!!.additionalUserInfo.isNewUser) {
                                //get user email&uid frm auth
                                val email = user!!.email
                                val uid = user!!.uid
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
                            }

                            updateUI()
                        } else {
                            Toast.makeText(this@login_pg, "Error", Toast.LENGTH_SHORT).show()

                        }
                        progressBar2.setVisibility(View.INVISIBLE)
                    }
            }

        })

        btn_signup.setOnClickListener(View.OnClickListener { v: View? -> val intent = Intent(this@login_pg,reg_pg::class.java)
        startActivity(intent)

        })
    }


    /////////////////////////////////////////////
    private fun updateUI(){
        val intent = Intent(this@login_pg, remind_pg::class.java)
        startActivity(intent)
        finish()
        }


    ////////////////////////////////
    //back press button code
    @Override
    override fun onBackPressed(){
        var t: Long =System.currentTimeMillis()
        if (t - backPressedTime > 2000){    // 2 sec
            backPressedTime = t
            Toast.makeText(this@login_pg,"Press back again to exit",Toast.LENGTH_LONG).show()
        } else run {
            // clean up
            super.onBackPressed()       // Quit
        }
    }
    //////////////////////////////////////
    override fun onStart(){
        super.onStart()
        if (mAuth!!.currentUser !=null){
            startActivity(Intent(this@login_pg,remind_pg::class.java))
            finish()
        }
    }

}