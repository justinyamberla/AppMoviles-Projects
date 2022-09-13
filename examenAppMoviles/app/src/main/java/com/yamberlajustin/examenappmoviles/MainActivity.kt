package com.yamberlajustin.examenappmoviles

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.Picasso
import kotlin.random.Random

//variables para conectar la parte lógica y parte visual
private lateinit var textViewPregunta: TextView
private lateinit var imgAnimal: ImageView
private lateinit var btnSiguiente: Button
private lateinit var btnAtras: Button

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Inicialziacion de variables
        textViewPregunta = findViewById(R.id.textoPregunta)
        imgAnimal = findViewById(R.id.imageViewAnimal)
        btnSiguiente = findViewById(R.id.btnSiguiente)
        btnAtras = findViewById(R.id.btnAtras)

        // Creación del Action bar
        var actionBar = getSupportActionBar()
        // showing the back button in action bar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
        }
        // this event will enable the back
        // function to the button on press
        fun onContextItemSelected(item: MenuItem): Boolean {
            when (item.itemId) {
                android.R.id.home -> {
                    finish()
                    return true
                }
            }
            return super.onContextItemSelected(item)
        }

        //llamada a funciones
        cargarImagenes(indice)
        btnSiguiente.setOnClickListener {
            mostrarSiguiente(indice)
        }
        btnAtras.setOnClickListener {
            mostrarAnterior(indice)
        }


    }
    //arreglos de preguntas y respuestas
    val arregloPreguntas = arrayOf(
        "1. ¿León, puma o gato doméstico?",
        "2. ¿Castor, chinchilla o capibara?",
        "3. ¿Alpaca, camello o burro?",
        "4. ¿León marino, nutria o hurón?",
        "5. ¿Oso hormiguero, elefante o tapir?",
        "6. ¿Zorrillo, mapache o tejón?",
        "7. ¿Oso, panda o koala?",
        "8. ¿Caballo, alce o cebra?"
    )
    val arregloRespuetsas = arrayOf(
        "Puma",
        "Capibara",
        "Alpaca",
        "Nutria",
        "Tapir",
        "Mapache",
        "Koala",
        "Cebra"
    )
    val arregloImagenesPregunta = arrayOf(
        "https://wl-genial.cf.tsp.li/resize/728x/jpg/154/25d/4f9115544aa0525caf38168eee.jpg",
        "https://wl-genial.cf.tsp.li/resize/728x/jpg/46a/01a/99c9b65644b460453af152b6c9.jpg",
        "https://wl-genial.cf.tsp.li/resize/728x/jpg/b63/7fb/0e267a5c68911b97a4c20d9d6d.jpg",
        "https://wl-genial.cf.tsp.li/resize/728x/jpg/700/91c/713a6f5ff6a20f608f9f685b98.jpg",
        "https://wl-genial.cf.tsp.li/resize/728x/jpg/165/bbf/c639515ba1b5d4b1929e623aa9.jpg",
        "https://wl-genial.cf.tsp.li/resize/728x/jpg/aa2/a4e/3cb39d5160a6a64505f7e0d0d6.jpg",
        "https://wl-genial.cf.tsp.li/resize/728x/jpg/8be/347/ba62055e679ad722385b2e07ee.jpg",
        "https://wl-genial.cf.tsp.li/resize/728x/jpg/729/3d5/9ecd0658719ccf6e8da26e4cb9.jpg"
    )
    val arregloImagenesRespuesta = arrayOf(
        "https://wl-genial.cf.tsp.li/resize/728x/jpg/b55/d22/b98a0553449a2e5549a3a2e379.jpg",
        "https://wl-genial.cf.tsp.li/resize/728x/jpg/9e3/bbc/7f38aa5f178c2641e4b13953bd.jpg",
        "https://wl-genial.cf.tsp.li/resize/728x/jpg/d42/299/88e5eb5373964e8e3f2ec7421c.jpg",
        "https://wl-genial.cf.tsp.li/resize/728x/jpg/24a/a78/b1bb475110a1f0ad11b72a836b.jpg",
        "https://wl-genial.cf.tsp.li/resize/728x/jpg/450/e30/098b2351c5b522db8bfffc9f27.jpg",
        "https://wl-genial.cf.tsp.li/resize/728x/jpg/99a/e21/2f568859d9a4a1ebe14e083d94.jpg",
        "https://wl-genial.cf.tsp.li/resize/728x/jpg/c15/000/eb6eea5f8f9cb374f7006a2292.jpg",
        "https://wl-genial.cf.tsp.li/resize/728x/jpg/617/a27/7120eb51ed86862429c423ba8f.jpg"
    )
    var indice = Random.nextInt(arregloPreguntas.size)

    fun cargarImagenes(indice: Int){
        var esPregunta = true
        val imagen = arregloImagenesPregunta[indice]
        val pregunta = arregloPreguntas[indice]
        Picasso.get().load(imagen).into(imgAnimal)
        textViewPregunta.setText(pregunta)
        imgAnimal.setOnClickListener {
            if (esPregunta){
                val imagen = arregloImagenesRespuesta[indice]
                Picasso.get().load(imagen).into(imgAnimal)
                esPregunta = false
            }else {
                val imagen = arregloImagenesPregunta[indice]
                Picasso.get().load(imagen).into(imgAnimal)
                esPregunta = true
            }
        }
    }
    fun mostrarSiguiente(indice: Int){
        if(indice >= 7){
            this.indice = 0
        }else{
            this.indice ++
        }
        cargarImagenes(this.indice)
    }
    fun mostrarAnterior(indice: Int){
        if(indice <= 0){
            this.indice = 7
        }else{
            this.indice --
        }
        cargarImagenes(this.indice)
    }
}