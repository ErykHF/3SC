package com.erykhf.android.a3sctest.fragments.pokemondetail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.erykhf.android.a3sctest.R
import com.erykhf.android.a3sctest.databinding.FragmentPokemonDetailBinding
import com.erykhf.android.a3sctest.utils.Util.loadImage
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PokemonDetailFragment : Fragment() {


    private val viewModel: PokemonDetailViewModel by viewModels()
    private lateinit var binding: FragmentPokemonDetailBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentPokemonDetailBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observePokemonDetail()
        observeError()
    }

    private fun observePokemonDetail() {
        viewModel.pokemon.observe(viewLifecycleOwner) {
            it?.let {
                binding.mainImage.loadImage(
                    it.sprites.other.home.front_default,
                    CircularProgressDrawable(requireContext())
                )
                binding.pokemonName.text = it.name.capitalize()
                binding.type.text =
                    getString(R.string.type, it.types.first().type.name.capitalize())
                binding.baseStat.text =
                    getString(R.string.stat, it.stats.first().base_stat.toString())

                it.moves.forEach { move ->
                    binding.moveName.text = getString(R.string.move, move.move.name.capitalize())
                }
                binding.weight.text = getString(R.string.weight, it.weight.toString())

            }
        }
    }

    private fun observeError() {
        viewModel.error.observe(viewLifecycleOwner) {
            it?.let {
                Snackbar.make(requireView(), it, Snackbar.LENGTH_LONG).show()
            }
        }
    }

}