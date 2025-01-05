package com.example.appcifrado

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    lateinit var btn_par_impar: Button
    lateinit var btn_cesar: Button
    lateinit var btn_desc_cesar: Button
    lateinit var btn_bilateral: Button
    lateinit var btn_desc_bilateral: Button
    lateinit var btn_vegenere: Button
    lateinit var btn_desc_vegenere: Button

    lateinit var et_frase: EditText
    lateinit var tv_frase: TextView
    lateinit var btn_frase: Button
    lateinit var btn_seven_times: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        initComponent()
        initListeners()


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
    private fun initListeners() {

    }
    private fun initComponent() {
        btn_par_impar = findViewById(R.id.btn_par_impar)
        btn_cesar = findViewById(R.id.btn_cesar)
        btn_desc_cesar = findViewById(R.id.btn_desc_cesar)
        btn_bilateral = findViewById(R.id.btn_bilateral)
        btn_desc_bilateral = findViewById(R.id.btn_desc_bilateral)
        btn_vegenere = findViewById(R.id.btn_vegenere)
        btn_desc_vegenere = findViewById(R.id.btn_desc_vegenere)
        et_frase = findViewById(R.id.et_frase)
        tv_frase = findViewById(R.id.tv_frase)
        btn_frase = findViewById(R.id.btn_frase)
        btn_seven_times = findViewById(R.id.btn_seven_times)

    }
}