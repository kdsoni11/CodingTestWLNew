package com.codingtestwl.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.codingtestwl.R
import com.codingtestwl.list.view.MainActivity
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashActivity : AppCompatActivity() {
    val scope = MainScope() // could also use an other scope such as viewModelScope if available
    var job: Job? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        init()
    }


    private fun init() {
        stopUpdates()
        job = scope.launch {
            // app delay for five second
            delay(5000)
            val intent = Intent(this@SplashActivity, MainActivity::class.java)
            startActivity(intent)

            finish()
        }
    }

    private fun stopUpdates() {
        job?.cancel()
        job = null
    }
}