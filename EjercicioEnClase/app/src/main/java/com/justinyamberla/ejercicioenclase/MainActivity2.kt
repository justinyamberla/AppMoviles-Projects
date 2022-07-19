package com.justinyamberla.ejercicioenclase

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity2 : AppCompatActivity() {
    private lateinit var fabFragmento: FloatingActionButton
    var modoDia = true
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
        /*fabFragmento = findViewById(R.id.fabFragmento)
        fabFragmento.setOnClickListener { view ->
            val fragmentoActivo : Fragment
            if (modoDia)
                fragmentoActivo = ModoNocheFragment()
            else
                fragmentoActivo = ModoDiaFragment()
            modoDia = !modoDia
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.fragmentContainerView,fragmentoActivo)
                .commit()
        }*/
    }
}