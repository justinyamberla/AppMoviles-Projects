// ----------------------- EJERCICIO CICLO DE VIDA DE UNA ACTIVIDAD -------------------------------
package com.justinyamberla.ejercicioenclase

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class CicloVidaActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ciclo_vida)
        Toast.makeText(this, "onCreate", Toast.LENGTH_SHORT).show()
        //La actividad está creada
    }

    override fun onStart() {
        super.onStart()
        Toast.makeText(this, "onStart", Toast.LENGTH_SHORT).show()
        //La actividad está a punto de hacerse visible
    }

    override fun onResume() {
        super.onResume()
        Toast.makeText(this, "OnResume", Toast.LENGTH_SHORT).show()
        // La actividad se ha vuelto visible (ahora se "reanuda").
    }

    override fun onPause() {
        super.onPause()
        Toast.makeText(this, "OnPause", Toast.LENGTH_SHORT).show()
        // Enfocarse en otra actividad  (esta actividad esta a punto de ser "detenida").
    }

    override fun onStop() {
        super.onStop()
        Toast.makeText(this, "OnStop", Toast.LENGTH_SHORT).show()
        // La actividad ya no es visible (ahora esta "detenida")
    }

    override fun onDestroy() {
        super.onDestroy()
        Toast.makeText(this, "OnDestroy", Toast.LENGTH_SHORT).show()
        // La actividad esta a punto de ser destruida.
    }

}