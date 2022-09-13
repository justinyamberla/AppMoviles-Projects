package com.yamberlajustin.cazarpatos

import android.content.Intent
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import java.util.regex.Pattern

class LoginActivity : AppCompatActivity() {
    lateinit var manejadorArchivo: FileHandler //Lab04
    lateinit var checkBoxRecordarme: CheckBox //Lab04
    lateinit var editTextEmail: EditText
    lateinit var editTextPassword:EditText
    lateinit var buttonLogin: Button
    lateinit var buttonNewUser:Button
    lateinit var mediaPlayer: MediaPlayer

    private lateinit var auth: FirebaseAuth // Lab 07

    //----------- Examen: Expresion regular para validar correo
    val EMAIL_ADDRESS_PATTERN = Pattern.compile(
        "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
                "\\@" +
                "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                "(" +
                "\\." +
                "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
                ")+"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        /*Inicialización de variables*/

        //manejadorArchivo = EncryptedSharedPreferencesManager(this) //Lab 04 - Encriptación
        //manejadorArchivo = SharedPreferencesManager(this) //Lab 04 - Normal
        manejadorArchivo = FileExternalManager(this) //Lab 04 - Almacenamiento Externo

        editTextEmail = findViewById(R.id.editTextEmail)
        editTextPassword = findViewById(R.id.editTextPassword)
        buttonLogin = findViewById(R.id.buttonLogin)
        buttonNewUser = findViewById(R.id.buttonNewUser)
        checkBoxRecordarme = findViewById(R.id.checkBoxRecordarme) //lab 04

        // Initialize Firebase Auth - Lab 07
        auth = Firebase.auth

        LeerDatosDePreferencias() //Lab 04

        //Eventos clic
        buttonLogin.setOnClickListener {
            val email = editTextEmail.text.toString()
            val clave = editTextPassword.text.toString()
            //Validaciones de datos requeridos y formatos
            if(!ValidarDatosRequeridos())
                return@setOnClickListener

            //Guardar datos en preferencias.
            GuardarDatosEnPreferencias() //Lab 04

            //Si pasa validación de datos requeridos, ir a pantalla principal
            //val intencion = Intent(this, MainActivity::class.java)
            //intencion.putExtra(EXTRA_LOGIN, email)
            //startActivity(intencion)

            AutenticarUsuario(email, clave) // Lab 07
        }
        buttonNewUser.setOnClickListener{
            val intent = Intent(this, RegistroActivity::class.java)
            startActivity(intent)
        }

        mediaPlayer=MediaPlayer.create(this, R.raw.title_screen)
        mediaPlayer.start()
    }

    override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        if (currentUser!= null)
        {
            var intent = Intent(this, MainActivity::class.java)
            intent.putExtra(EXTRA_LOGIN, auth.currentUser!!.email)
            startActivity(intent)
        }
    }

    // Lab 07
    fun AutenticarUsuario(email:String, password:String){
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Log.d(EXTRA_LOGIN, "signInWithEmail:success")
                    //Si pasa validación de datos requeridos, ir a pantalla principal
                    val intencion = Intent(this, MainActivity::class.java)
                    intencion.putExtra(EXTRA_LOGIN, auth.currentUser!!.email)
                    startActivity(intencion)
                    //finish()
                } else {
                    Log.w(EXTRA_LOGIN, "signInWithEmail:failure", task.exception)
                    Toast.makeText(baseContext, task.exception!!.message,
                        Toast.LENGTH_SHORT).show()
                }
            }
    }
    fun SignUpNewUser(email:String, password:String){
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(EXTRA_LOGIN, "createUserWithEmail:success")
                    val user = auth.currentUser
                    Toast.makeText(baseContext, "New user saved.",
                        Toast.LENGTH_SHORT).show()
                    //updateUI(user)
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(EXTRA_LOGIN, "createUserWithEmail:failure", task.exception)
                    Toast.makeText(baseContext, "Authentication failed.",
                        Toast.LENGTH_SHORT).show()
                    //updateUI(null)
                }
            }
    }

    //----------- Examen: Funcion para validar Correo
    private fun validarEmail(str: String): Boolean {
        return EMAIL_ADDRESS_PATTERN.matcher(str).matches()
    }

    private fun ValidarDatosRequeridos():Boolean{
        val email = editTextEmail.text.toString()
        val clave = editTextPassword.text.toString()

        //----------- Examen: Condiciones para validar Email
        if (email.isEmpty()) {
            editTextEmail.setError("El email es obligatorio")
            editTextEmail.requestFocus()
            return false
        }else if(!validarEmail(email)){
            editTextEmail.setError("¡El email no es válido! Intente de nuevo")
            editTextEmail.requestFocus()
            return false
        }

        if (clave.isEmpty()) {
            editTextPassword.setError("La clave es obligatoria")
            editTextPassword.requestFocus()
            return false
        } else if (clave.length < 8) {
            editTextPassword.setError("La clave debe tener al menos 3 caracteres")
            editTextPassword.requestFocus()
            return false
        }
        return true
    }
    private fun GuardarDatosEnPreferencias(){ //Lab 04
        val email = editTextEmail.text.toString()
        val clave = editTextPassword.text.toString()
        val listadoAGrabar:Pair<String,String>
        if(checkBoxRecordarme.isChecked){
            listadoAGrabar = email to clave
        }
        else{
            listadoAGrabar ="" to ""
        }
        manejadorArchivo.SaveInformation(listadoAGrabar)
    }

    private fun LeerDatosDePreferencias(){ //Lab 04
        val listadoLeido = manejadorArchivo.ReadInformation()
        if(listadoLeido.first != null){
            checkBoxRecordarme.isChecked = true
        }
        editTextEmail.setText ( listadoLeido.first )
        editTextPassword.setText ( listadoLeido.second )
    }

    override fun onDestroy() {
        mediaPlayer.release()
        super.onDestroy()
    }
}
