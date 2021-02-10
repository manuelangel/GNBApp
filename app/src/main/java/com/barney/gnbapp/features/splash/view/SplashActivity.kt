package com.barney.gnbapp.features.splash.view

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import androidx.appcompat.app.AppCompatActivity
import com.barney.gnbapp.R
import com.barney.gnbapp.features.catalogue.view.ProductsCatalogueActivity


class SplashActivity : AppCompatActivity()  {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        startCountDown()
    }

    private fun startCountDown() {
        object : CountDownTimer(2000, 1000) {
            override fun onTick(millisUntilFinished: Long) {}

            override fun onFinish() {
                goToCatalogue()
            }
        }.start()
    }

    private fun goToCatalogue() {
        startActivity(Intent(this,ProductsCatalogueActivity::class.java))
        finish()
    }
}