package com.example.heartforecast

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnAbout = findViewById<CardView>(R.id.btnAbout)
        btnAbout.setOnClickListener {
            // Handle button click
            val intent = Intent(this, AboutActivity::class.java)
            startActivity(intent)
        }

        val btnDataset = findViewById<CardView>(R.id.btnDataset)
        btnDataset.setOnClickListener {
            // Handle button click
            val intent = Intent(this, DatasetActivity::class.java)
            startActivity(intent)
        }

        val btnFitur = findViewById<CardView>(R.id.btnFitur)
        btnFitur.setOnClickListener {
            // Handle button click
            val intent = Intent(this, FiturActivity::class.java)
            startActivity(intent)
        }

        val btnModel = findViewById<CardView>(R.id.btnModel)
        btnModel.setOnClickListener {
            // Handle button click
            val intent = Intent(this, ModelActivity::class.java)
            startActivity(intent)
        }

        val btnSimulasi = findViewById<CardView>(R.id.btnSimulasi)
        btnSimulasi.setOnClickListener {
            // Handle button click
            val intent = Intent(this, SimulasiActivity::class.java)
            startActivity(intent)
        }
    }
}
