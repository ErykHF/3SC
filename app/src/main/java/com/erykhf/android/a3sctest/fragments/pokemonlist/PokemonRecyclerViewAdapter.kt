package com.erykhf.android.a3sctest.fragments.pokemonlist

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import com.erykhf.android.a3sctest.databinding.FragmentPokemonBinding
import com.erykhf.android.a3sctest.model.PokemonListEntry
import com.erykhf.android.a3sctest.model.pokemonresponses.Pokemon
import com.erykhf.android.a3sctest.utils.Util
import com.erykhf.android.a3sctest.utils.Util.loadImage


class PokemonRecyclerViewAdapter() : RecyclerView.Adapter<PokemonRecyclerViewAdapter.ViewHolder>(),
    Filterable {

    private var values = mutableListOf<PokemonListEntry>()
    private var filteredValues = mutableListOf<PokemonListEntry>()
    fun updateList(listOfPokemon: List<PokemonListEntry>) {
        values = listOfPokemon as MutableList<PokemonListEntry>
        filteredValues = values
        notifyItemInserted(values.size)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val inflater = LayoutInflater.from(parent.context)
        val binding = FragmentPokemonBinding.inflate(inflater, parent, false)

        return ViewHolder(binding)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = filteredValues[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int = filteredValues.size


    inner class ViewHolder(val binding: FragmentPokemonBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(values: PokemonListEntry) {
            val progressDrawable = Util.getProgressDrawable(binding.root.context)

            binding.pokemonImage.loadImage(values.imgUrl, progressDrawable)
            binding.pokemonName.text = values.pokemonName

            binding.root.setOnClickListener {
                onItemClickListener?.let {
                    it(values)
                }
            }
        }
    }

    private var onItemClickListener: ((PokemonListEntry) -> Unit)? = null

    fun setOnItemClickListener(listener: (PokemonListEntry) -> Unit) {
        onItemClickListener = listener
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charString = constraint?.toString() ?: ""
                if (charString.isEmpty()) filteredValues = values else {
                    val filteredList = arrayListOf<PokemonListEntry>()
                    val filteredPattern = constraint.toString().toLowerCase().trim { it <= ' ' }
                    values.filter {
                        (it.pokemonName.toLowerCase().contains(filteredPattern))
                    }.forEach { filteredList.add(it) }
                    filteredValues = filteredList
                }
                return FilterResults().apply { values = filteredValues }
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                filteredValues = if (results?.values == null)
                    values
                else
                    results.values as MutableList<PokemonListEntry>
                notifyDataSetChanged()
            }

        }
    }

}