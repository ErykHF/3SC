package com.erykhf.android.a3sctest.fragments.pokemonlist

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import com.erykhf.android.a3sctest.R
import com.erykhf.android.a3sctest.databinding.FragmentPokemonBinding
import com.erykhf.android.a3sctest.databinding.FragmentPokemonListBinding

import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class PokemonFragment : Fragment() {

    private lateinit var binding: FragmentPokemonListBinding
    private val viewModel: PokemonViewModel by viewModels()
    private val recyclerViewAdapter = PokemonRecyclerViewAdapter()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentPokemonListBinding.inflate(inflater, container, false)
        val menuHost: MenuHost = requireActivity()


        // Search the pokemon
        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.pokemon_list_fragment_menu, menu)

                val searchItem: MenuItem = menu.findItem(R.id.menu_item_search)
                val searchView = searchItem.actionView as SearchView

                searchView.apply {

                    setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                        override fun onQueryTextSubmit(queryText: String): Boolean {
                            Log.d("MainActivity", "QueryTextSubmit: $queryText")
                            return false
                        }

                        override fun onQueryTextChange(queryText: String): Boolean {
                            Log.d("MainActivity", "QueryTextChange: $queryText")
                            recyclerViewAdapter.filter.filter(queryText)
                            return true
                        }
                    })

                }
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return false
            }

        }, viewLifecycleOwner, Lifecycle.State.RESUMED)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        observeLoadingAndError()
        navigateToDetails()
        observePokemons()
    }


    private fun observePokemons() {
        viewModel.pokemons.observe(viewLifecycleOwner) { pokemons ->
            recyclerViewAdapter.updateList(pokemons)
        }
    }

    private fun setupRecyclerView() {
        binding.list.apply {
            adapter = recyclerViewAdapter
        }
    }

    private fun navigateToDetails() {

        recyclerViewAdapter.setOnItemClickListener {

            val args = Bundle().apply {
                putInt("id", it.number)
            }

            findNavController().navigate(R.id.goto_pokemon_detail, args)
            Toast.makeText(requireContext(), it.pokemonName, Toast.LENGTH_SHORT)
                .show()
        }
    }

    private fun observeLoadingAndError() {
        viewModel.errorText.observe(viewLifecycleOwner) { text ->
            text?.let {
                binding.errorTextView.text = it
                binding.errorTextView.visibility = View.VISIBLE
                binding.errorButton.visibility = View.VISIBLE
                binding.errorButton.setOnClickListener {
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
                    binding.errorButton.visibility = View.GONE
                }
            }
        }
    }
}