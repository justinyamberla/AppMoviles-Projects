package com.yamberlajustin.myapplistas

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView

class MainActivity : AppCompatActivity() {
    lateinit var listViewSitiosWeb: ListView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //inicializo al arreglo de sitios web
        val arregloWebsites = arrayOf(
            "https://www.epn.edu.ec",
            "https://www.fis.epn.edu.ec",
            "https://www.google.com"
        )

        listViewSitiosWeb = findViewById(R.id.listViewSitiosWeb)

        //lleno la lista con adaptadores del SDK de android de esta forma:
        //listViewSitiosWeb.adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, resources.getStringArray(R.array.arreglo_websites))

        //lleno la lista pero con un adoptador propio si quiero usar mi propio diseño:
        listViewSitiosWeb.adapter = MiAdaptador(resources.getStringArray(R.array.arreglo_websites))

        listViewSitiosWeb.setOnItemClickListener { parent, view, position, id ->
            val intencion = Intent(Intent.ACTION_VIEW, Uri.parse("http://www.google.com"))
            startActivity(intencion)
        }
    }
}