package com.erykhf.android.a3sctest.fragments.pokemonlist

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.erykhf.android.a3sctest.R
import com.erykhf.android.a3sctest.databinding.FragmentPokemonBinding
import com.erykhf.android.a3sctest.databinding.FragmentPokemonListBinding

import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class PokemonFragment : Fragment(R.layout.fragment_pokemon_list) {

    private lateinit var binding: FragmentPokemonListBinding
    private val viewModel: PokemonViewModel by viewModels()
    private val recyclerViewAdapter = PokemonRecyclerViewAdapter()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentPokemonListBinding.inflate(inflater, container, false)


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        observeLoadingAndError()
        viewModel.pokemons.observe(viewLifecycleOwner) { pokemons ->
            recyclerViewAdapter.updateList(pokemons!!)
        }
    }

    private fun setupRecyclerView() {
        binding.list.apply {
            adapter = recyclerViewAdapter
        }
    }

    private fun observeLoadingAndError() {
        viewModel.errorText.observe(viewLifecycleOwner) { text ->
            text?.let {
                binding.errorTextView.text = it
                binding.errorTextView.visibility = View.VISIBLE
                binding.errorButton?.visibility = View.VISIBLE
                binding.errorButton?.setOnClickListener {
                    viewModel.getAllPokemon()
                }
                viewModel.onErrorTextShown()
            }
        }
        viewModel.spinner.observe(viewLifecycleOwner) { value ->
            value.let {
                binding.spinner.visibility = if (it) View.VISIBLE else View.GONE
                if (it) {
                    binding.errorTextView.visibility = View.GONE
                    binding.errorButton?.visibility = View.GONE
                }
            }
        }
    }
}