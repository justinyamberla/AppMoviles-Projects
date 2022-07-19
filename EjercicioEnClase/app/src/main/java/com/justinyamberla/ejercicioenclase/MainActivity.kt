package com.justinyamberla.ejercicioenclase

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val matematicas = 5
        val quimica = 5
        val fisica = 5
        var resultado = 0

        resultado = (matematicas + quimica + fisica) / 3

        if (resultado >= 6) {
            Toast.makeText(this, "Aprobado", Toast.LENGTH_LONG).show()
        } else if (resultado <= 5) {
            Toast.makeText(this, "Reprobado", Toast.LENGTH_LONG).show()
        }
    }
}