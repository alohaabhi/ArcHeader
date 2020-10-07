package com.example.archeaderdemo

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<Button>(R.id.btn_open_arc_header).setOnClickListener {
            startActivity(Intent(this, ArcHeaderActivity::class.java))
        }

        findViewById<Button>(R.id.btn_open_arc_header_gradient).setOnClickListener {
            startActivity(Intent(this, ArcHeaderGradientActivity::class.java))
        }
    }
}