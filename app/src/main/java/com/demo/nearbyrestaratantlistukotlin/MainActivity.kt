package com.demo.nearbyrestaratantlistukotlin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import com.demo.nearbyrestaratantlistukotlin.ui.HomeActivity
import com.demo.nearbyrestaratantlistukotlin.utils.AppPreferences
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    val email: String = "test@gmail.com"
    val pass: String = "test"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if(AppPreferences.isLogin){
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)

        }
        setContentView(R.layout.activity_main)

        ButtonReset.setOnClickListener {
            textInputEditTextEmail.setText("")
            textInputEditTextPassword.setText("")
        }

        ButtonLogin.setOnClickListener {

            val user_name = textInputEditTextEmail.text;
            val password = textInputEditTextPassword.text;

            if (user_name.isNullOrEmpty()) {
                textInputEditTextEmail.setError("Please Enter Email")
            } else if (!user_name.toString().isEmailValid()) {
                textInputEditTextEmail.setError("Email is invalid")

            } else if (password.isNullOrEmpty()) {
                textInputEditTextPassword.setError("Please Enter Password")
            } else if (user_name.toString()!!.equals(email) && password.toString()!!.equals(pass)) {
                AppPreferences.isLogin = true
                AppPreferences.username = user_name.toString()
                AppPreferences.password = password.toString()
                Toast.makeText(this@MainActivity, "Login Successfully", Toast.LENGTH_LONG).show()
                val intent = Intent(this, HomeActivity::class.java)
                startActivity(intent)
            } else {
                Toast.makeText(this@MainActivity, "Invalid details", Toast.LENGTH_LONG).show()

            }

        }

    }

    fun String.isEmailValid(): Boolean {
        return !TextUtils.isEmpty(this) && android.util.Patterns.EMAIL_ADDRESS.matcher(this).matches()
    }
}