package com.example.covidtracker

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.android.synthetic.main.item_list.*


class RegisterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        tv_login.setOnClickListener{
            val intent = Intent(this,LoginWithFirebase::class.java)
            startActivity(intent)
            onBackPressed()
        }
        btn_register.setOnClickListener{
            when{
                TextUtils.isEmpty(et_register_mail.text.toString().trim{it <= ' '})->{
                    Toast.makeText(this,"Please Enter Email",Toast.LENGTH_LONG).show()
                }
                TextUtils.isEmpty(et_register_password.text.toString().trim{it <= ' '})->{
                    Toast.makeText(this,"Please Enter Password",Toast.LENGTH_LONG).show()
                }
                else->{
                    val email:String = et_register_mail.text.toString().trim{it <= ' '}
                    val password:String = et_register_password.toString().trim{it<=' '}

                    //create an instance and create a register a user with email ans password .
                    FirebaseAuth.getInstance().createUserWithEmailAndPassword(email,password)
                        .addOnCompleteListener({ task->
                                // if registration is successfully done
                                if (task.isSuccessful){
                                    val firebaseUser:FirebaseUser = task.result!!.user!!
                                    Toast.makeText(this, "you were register successfully", Toast.LENGTH_SHORT).show()

                                    val intent = Intent(this,MainActivity::class.java)
                                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK

                                    intent.putExtra("user_id",firebaseUser.uid)
                                    intent.putExtra("email_id",email)
                                    startActivity(intent)
                                    finish()

                                }
                            else{
                                    Toast.makeText(this, task.exception!!.message.toString(), Toast.LENGTH_SHORT).show()
                                }
                            }
                        )
                }

            }

        }
    }
}