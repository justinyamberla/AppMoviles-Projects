package com.grupo3.proyectoborrador

import android.content.Context
import android.content.Intent
import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.grupo3.proyectoborrador.adicionales.Libro

class FavoritosAdapter(private val mContext: Context, private val dataSet:ArrayList<Libro>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val TYPE_HEADER: Int = 0

    class ViewHolderHeader(view: View) : RecyclerView.ViewHolder(view) {
        val textViewTitulo: TextView = view.findViewById(R.id.textViewTituloF)
        val textViewAutor: TextView = view.findViewById(R.id.textViewAutorF)
        val textViewIsbn: TextView = view.findViewById(R.id.textViewIsbnF)
        val textViewAcciones: TextView = view.findViewById(R.id.textViewAccionesF)
        val btnVer: ImageButton = view.findViewById(R.id.btnVerF)
        val btnEliminar: ImageButton = view.findViewById(R.id.btnEliminarF)
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textViewTitulo: TextView
        val textViewAutor: TextView
        val textViewIsbn: TextView
        val textViewAcciones: TextView
        val btnVer: ImageButton
        val btnEliminar: ImageButton

        init {
            textViewTitulo = view.findViewById(R.id.textViewTituloF)
            textViewAutor = view.findViewById(R.id.textViewAutorF)
            textViewIsbn = view.findViewById(R.id.textViewIsbnF)
            textViewAcciones = view.findViewById(R.id.textViewAccionesF)
            btnVer = view.findViewById(R.id.btnVerF)
            btnEliminar= view.findViewById(R.id.btnEliminarF)
        }
    }

    override fun getItemViewType(position: Int): Int {
        if (position == 0) {
            return TYPE_HEADER
        }
        return 1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == TYPE_HEADER) {
            val header =
                LayoutInflater.from(parent.context).inflate(R.layout.favoritos_list, parent, false)
            return ViewHolderHeader(header)
        }
        val header =
            LayoutInflater.from(parent.context).inflate(R.layout.favoritos_list, parent, false)
        return ViewHolder(header)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ViewHolderHeader) {
            holder.btnVer.visibility = View.INVISIBLE
            holder.btnEliminar.visibility = View.INVISIBLE
            holder.textViewAcciones.visibility = View.VISIBLE
            holder.textViewTitulo.setTypeface(null, Typeface.BOLD)
            holder.textViewAutor.setTypeface(null, Typeface.BOLD)
            holder.textViewIsbn.setTypeface(null, Typeface.BOLD)
            holder.textViewAcciones.setTypeface(null, Typeface.BOLD)
            holder.textViewTitulo.setTextColor((R.color.propio).toInt())
            holder.textViewAutor.setTextColor((R.color.propio).toInt())
            holder.textViewIsbn.setTextColor((R.color.propio).toInt())
            holder.textViewAcciones.setTextColor((R.color.propio).toInt())

        } else if (holder is ViewHolder) {
            holder.btnVer.visibility = View.VISIBLE
            holder.btnEliminar.visibility = View.VISIBLE
            holder.textViewAcciones.visibility = View.INVISIBLE
            holder.textViewTitulo.text = dataSet[position - 1].titulo
            holder.textViewAutor.text = dataSet[position - 1].autor
            holder.textViewIsbn.text = dataSet[position - 1].isbn

            holder.btnVer.setOnClickListener {
                val intent = Intent(mContext, LibroActivity::class.java).apply {
                    putExtra("titulo", dataSet[position - 1].titulo)
                    putExtra("autor", dataSet[position - 1].autor)
                    putExtra("isbn", dataSet[position - 1].isbn)
                }
                mContext.startActivity(intent)
            }
        }
    }

    override fun getItemCount() = dataSet.size + 1
}