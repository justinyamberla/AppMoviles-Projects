package com.yamberlajustin.myappws

import android.content.Context
import android.net.ConnectivityManager
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract.CommonDataKinds.Website.URL
import android.text.method.ScrollingMovementMethod
import android.util.Log
import android.widget.Button
import android.widget.TextView
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL

class MainActivity : AppCompatActivity() {
    lateinit var textViewMensaje : TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        textViewMensaje = findViewById(R.id.textViewMensaje)
        textViewMensaje.movementMethod = ScrollingMovementMethod()
        setOnClickListeners()
    }
    /*private fun setOnClickListenersAntigua() {
        findViewById<Button>(R.id.buttonConsultarString).setOnClickListener {
            if(Network.isConnected(this)){
                val url = "https://www.google.com"
                val contentType = "text/plain; charset=utf-8"
                val task = HttpPostAsyncTask()
                task.execute(url, contentType)
            }
        }
        findViewById<Button>(R.id.buttonConsultarJson).setOnClickListener {
            val url = "https://dummy.restapiexample.com/api/v1/employee/1"
            val contentType = "application/json"
            val task = HttpPostAsyncTask()
            task.execute(url, contentType)
        }
    }*/

    private fun setOnClickListeners() {
        findViewById<Button>(R.id.buttonConsultarString).setOnClickListener {
            val queue = Volley.newRequestQueue(this)
            val url = "https://www.google.com"
            val stringRequest = StringRequest(
                Request.Method.GET, url,
                { response -> textViewMensaje.text = response }
            ) { textViewMensaje.setText( getString(R.string.textViewMensaje_text_error) ) }
            queue.add(stringRequest)
        }
        findViewById<Button>(R.id.buttonConsultarJson).setOnClickListener {
            val queue = Volley.newRequestQueue(this)
            val url = "https://dummy.restapiexample.com/api/v1/employee/1"
            val jsObjRequest = JsonObjectRequest(
                Request.Method.GET, url, null,
                { response -> textViewMensaje.text = response.toString() }
            ) { textViewMensaje.text = getString(R.string.textViewMensaje_text_error) }
            queue.add(jsObjRequest)
        }
    }

    class Network {
        companion object{
            fun isConnected(activity: AppCompatActivity):Boolean{
                val connectivityManager = activity.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
                val networkInfo = connectivityManager.activeNetworkInfo
                return  networkInfo != null && networkInfo.isConnected
            }
        }
    }
    inner class HttpPostAsyncTask() :
        AsyncTask<String?, Void?, Void?>() {
        protected override fun doInBackground(vararg params: String?): Void? {
            var inputStream: InputStream? = null
            var urlConnection: HttpURLConnection? = null
            try {
                val url = URL(params[0])
                val contentType = params[1]
                urlConnection = url.openConnection() as HttpURLConnection
                //urlConnection.doInput = true
                //urlConnection.doOutput = true
                urlConnection.setRequestProperty("Content-Type", contentType)
                urlConnection.requestMethod = "GET"
                urlConnection.setReadTimeout(1000);
                urlConnection.setConnectTimeout(1000);
                // OPTIONAL - Sets an authorization header
                //urlConnection.setRequestProperty("Authorization", "someAuthString")
                urlConnection.connect()
                val responseCode = urlConnection.responseCode
                Log.d("TAG", urlConnection.responseCode.toString())
                if (responseCode == HttpURLConnection.HTTP_OK)
                {
                    inputStream = urlConnection.inputStream
                    textViewMensaje.text = urlConnection.inputStream.bufferedReader().readText()
                    // From here you can convert the string to JSON with whatever JSON parser you like to use
                }
            } catch (e: Exception) {
                Log.d("TAG", e.localizedMessage)
            }
            finally {
                if(inputStream != null){
                    inputStream.close()
                }
                if(urlConnection != null) {
                    urlConnection.disconnect()
                }
            }
            return null
        }
    }
}
