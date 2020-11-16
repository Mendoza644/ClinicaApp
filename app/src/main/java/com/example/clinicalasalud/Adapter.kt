package com.example.clinicalasalud

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.clinicalasalud.models.Dossiers
import com.google.android.material.textview.MaterialTextView
import java.util.*

class Adapter(private val dossiersArrayList: ArrayList<Dossiers>, private val context: Context) : RecyclerView.Adapter<Adapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_layout, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val dossiers = dossiersArrayList[position]
        holder.itemName.text = dossiers.name
        holder.itemAge.text = dossiers.age.toString()

        holder.itemView.setOnClickListener {
            val intent = Intent(context, UpdateLog::class.java)
            intent.putExtra("dossier", dossiers)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return dossiersArrayList.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var itemName: MaterialTextView = itemView.findViewById(R.id.item_name_rv)
        var itemAge: MaterialTextView = itemView.findViewById(R.id.item_age_rv)
    }
}