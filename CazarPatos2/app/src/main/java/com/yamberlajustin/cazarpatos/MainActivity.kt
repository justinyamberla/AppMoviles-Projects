package com.yamberlajustin.cazarpatos

import android.media.MediaPlayer
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import java.util.*

class MainActivity : AppCompatActivity() {
    lateinit var textViewUsuario: TextView
    lateinit var textViewContador: TextView
    lateinit var textViewTiempo: TextView
    lateinit var imageViewPato: ImageView
    var contador = 0
    var anchoPantalla = 0
    var alturaPantalla = 0
    var gameOver = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        ////----------- Examen: Botón Atrás para regresar a Login
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.title = "Login"

        textViewUsuario = findViewById(R.id.textViewUsuario)
        textViewContador = findViewById(R.id.textViewContador)
        textViewTiempo = findViewById(R.id.textViewTiempo)
        imageViewPato = findViewById(R.id.imageViewPato)

        //Obtener el usuario de pantalla login
        val extras = intent.extras ?: return
        val usuario = extras.getString(EXTRA_LOGIN) ?:"Unknown"

        //----------- Examen: Capturar sólo nombre del correo
        textViewUsuario.setText(usuario.substringBefore('@'))

        //Determina el ancho y largo de pantalla
        inicializarPantalla()
        //Cuenta regresiva del juego
        inicializarCuentaRegresiva()
        //Evento clic sobre la imagen del pato
        imageViewPato.setOnClickListener {
            if (gameOver) return@setOnClickListener
            contador++
            MediaPlayer.create(this, R.raw.gunshot).start()
            textViewContador.setText(contador.toString())
            imageViewPato.setImageResource(R.drawable.duck_clicked)
            //Evento que se ejecuta luego de 500 milisegundos
            Handler().postDelayed(Runnable {
                imageViewPato.setImageResource(R.drawable.duck)
                moverPato()
            }, 500)
        }
    }
    private fun inicializarPantalla() {
        // 1. Obtenemos el tamaño de la pantalla del dispositivo
        val display = this.resources.displayMetrics
        anchoPantalla = display.widthPixels
        alturaPantalla = display.heightPixels
    }

    private fun moverPato() {
        val min = imageViewPato.getWidth()/2
        val maximoX = anchoPantalla - imageViewPato.getWidth()
        val maximoY = alturaPantalla - imageViewPato.getHeight()
        // Generamos 2 números aleatorios, para la coordenadas x , y
        val randomX = Random().nextInt(maximoX - min + 1)
        val randomY = Random().nextInt(maximoY - min + 1)

        // Utilizamos los números aleatorios para mover el pato a esa nueva posición
        imageViewPato.setX(randomX.toFloat())
        imageViewPato.setY(randomY.toFloat())
    }
    var contadorTiempo = object : CountDownTimer(10000, 1000) {
        override fun onTick(millisUntilFinished: Long) {
            val segundosRestantes = millisUntilFinished / 1000
            textViewTiempo.setText("${segundosRestantes}s")
        }
        override fun onFinish() {
            textViewTiempo.setText("0s")
            gameOver = true
            mostrarDialogoGameOver()
        }
    }
    private fun inicializarCuentaRegresiva() {
        contadorTiempo.start()
    }
    private fun mostrarDialogoGameOver() {
        val builder = AlertDialog.Builder(this)
        builder
            .setIcon(getResources().getDrawable(android.R.drawable.ic_dialog_info)) //Examen: Icono mensaje de alerta
            .setMessage("Felicidades!!\nHas conseguido cazar $contador patos")
            .setTitle("Fin del juego")
            .setPositiveButton("Reiniciar",
                { _, _ ->
                    reiniciarJuego()
                })
            .setNegativeButton("Cerrar",
                { _, _ ->
                    //dialog.dismiss()
                })
        builder.create().show()
    }
    fun reiniciarJuego(){
        contador = 0
        gameOver = false
        contadorTiempo.cancel()
        textViewContador.setText(contador.toString())
        moverPato()
        inicializarCuentaRegresiva()
    }

}
