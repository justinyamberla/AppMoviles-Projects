package com.yamberlajustin.yamberlajustinex2

import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.yamberlajustin.yamberlajustinex2.R
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import java.util.*

private lateinit var imgImagen: ImageView
private lateinit var textViewPregunta: TextView
private lateinit var btnSiguiente: Button
private lateinit var btnAnterior: Button
private lateinit var arrayPreguntas: Array<String>
private lateinit var imagen_pr: Array<String>
private lateinit var imagen_res: Array<String>
private var anterior = 0
private var actual = 1


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportActionBar?.title = resources.getString(R.string.action_bar)

        imgImagen = findViewById(R.id.imgImagen)
        textViewPregunta = findViewById(R.id.textViewPregunta)
        arrayPreguntas = resources.getStringArray(R.array.preguntas)
        imagen_pr = resources.getStringArray(R.array.imagenes_preguntas)
        imagen_res = resources.getStringArray(R.array.imagenes_respuestas)
        btnSiguiente = findViewById(R.id.btnSiguiente)
        btnAnterior = findViewById(R.id.btnAnterior)

        actual = Random().nextInt(8)
        cargar(actual)

        btnAnterior.setOnClickListener {
            actual = anterior
        }

        btnSiguiente.setOnClickListener {
            anterior = actual
            while (actual == anterior) {
                actual = Random().nextInt(8)
            }
            cargar(actual)
        }

    }

    fun cargar(posicion: Int) {

        var estado = 0

        Picasso.get().load(imagen_pr[posicion]).fit()
            .placeholder(R.drawable.product_image_thumbnail_placeholder)
            .into(imgImagen, object : Callback {
                override fun onSuccess() {
                    //
                }
                override fun onError(e: Exception?) {
                    Toast.makeText(applicationContext, "Error cargando la imagen",
                        Toast.LENGTH_SHORT).show()
                }
            })

        textViewPregunta.text = arrayPreguntas[posicion]

        imgImagen.setOnClickListener {
            if (estado == 0) {
                Picasso.get().load(imagen_res[posicion]).fit()
                    .placeholder(R.drawable.product_image_thumbnail_placeholder)
                    .into(imgImagen, object : Callback {
                        override fun onSuccess() {
                            //
                        }
                        override fun onError(e: Exception?) {
                            Toast.makeText(applicationContext, "Error cargando la imagen",
                                Toast.LENGTH_SHORT).show()
                        }
                    })
                estado = 1
            } else {
                Picasso.get().load(imagen_pr[posicion]).fit()
                    .placeholder(R.drawable.product_image_thumbnail_placeholder)
                    .into(imgImagen, object : Callback {
                        override fun onSuccess() {
                            //
                        }

                        override fun onError(e: Exception?) {
                            Toast.makeText(applicationContext, "Error cargando la imagen",
                                Toast.LENGTH_SHORT).show()
                        }
                    })
                estado = 0
            }

        }

    }


}