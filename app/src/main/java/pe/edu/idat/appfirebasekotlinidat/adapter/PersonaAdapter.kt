package pe.edu.idat.appfirebasekotlinidat.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import pe.edu.idat.appfirebasekotlinidat.R
import pe.edu.idat.appfirebasekotlinidat.databinding.PersonaItemBinding
import pe.edu.idat.appfirebasekotlinidat.model.Persona

class PersonaAdapter (private var lstpersona: List<Persona>)
    : RecyclerView.Adapter<PersonaAdapter.ViewHolder>()
{

    inner class ViewHolder(val binding: PersonaItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PersonaAdapter.ViewHolder {
        val binding = PersonaItemBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }
    override fun onBindViewHolder(holder: PersonaAdapter.ViewHolder, position: Int) {
        with(holder){
            with(lstpersona[position]){
                binding.tvnompersona.text = nombre
                binding.tvapepersona.text = apellido
                binding.tvedadpersona.text = edad.toString()
            }
        }


    }
    override fun getItemCount(): Int {
        return lstpersona.size
    }
}