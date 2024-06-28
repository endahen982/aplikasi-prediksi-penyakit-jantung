package com.example.heartforecast

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView

class DatasetActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dataset)

        val linkdataset: TextView = findViewById(R.id.linkdataset)
        linkdataset.setOnClickListener {
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.kaggle.com/datasets/uciml/pima-indians-diabetes-database"))
            startActivity(browserIntent)

            finish()

        }
    }
}