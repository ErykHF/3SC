package com.erykhf.android.a3sctest.fragments.pokemonlist

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import com.erykhf.android.a3sctest.databinding.FragmentPokemonBinding
import com.erykhf.android.a3sctest.model.Result


class PokemonRecyclerViewAdapter() : RecyclerView.Adapter<PokemonRecyclerViewAdapter.ViewHolder>() {

    private var values = arrayListOf<Result>()
    fun updateList(listOfPokemon: List<Result>) {
        values.clear()
        values.addAll(listOfPokemon)
        notifyDataSetChanged()

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(
            FragmentPokemonBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]
        holder.idView.text = item.name
//        holder.contentView.text = item.content

        holder.itemView.apply {
            setOnClickListener {
                Toast.makeText(holder.itemView.context, item.name, Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(binding: FragmentPokemonBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val idView: TextView = binding.itemNumber
        val contentView: TextView = binding.content

    }

}