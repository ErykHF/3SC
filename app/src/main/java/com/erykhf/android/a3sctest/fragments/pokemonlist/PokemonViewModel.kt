package com.erykhf.android.a3sctest.fragments.pokemonlist

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.erykhf.android.a3sctest.data.repository.Repository
import com.erykhf.android.a3sctest.model.PokemonListEntry
import com.erykhf.android.a3sctest.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class PokemonViewModel @Inject constructor(private val repository: Repository) : ViewModel() {


    private val _pokemons = MutableLiveData<List<PokemonListEntry>>()
    val pokemons: LiveData<List<PokemonListEntry>> = _pokemons
    private val emptyPokemonList = mutableListOf<PokemonListEntry>()

    private val _spinner = MutableLiveData(false)
    val spinner: LiveData<Boolean> = _spinner

    private val _errorText = MutableLiveData<String?>()
    val errorText: LiveData<String?> = _errorText


    init {
        getAllPokemon()
    }


    fun getAllPokemon() {
        viewModelScope.launch {

            _spinner.value = true
            val result = repository.loadPokemonList()

            when (result) {
                is Resource.Success -> {
                    val pokedexEntries = result.data!!.results.mapIndexed { index, entry ->
                        val number = if (entry.url.endsWith("/")) {
                            entry.url.dropLast(1).takeLastWhile { it.isDigit() }
                        } else {
                            entry.url.takeLastWhile { it.isDigit() }
                        }

                        val url =
                            "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/$number.png"
                        PokemonListEntry(entry.name.capitalize(Locale.ROOT), url, number.toInt())
                    }

                    _spinner.value = false
                    emptyPokemonList += pokedexEntries
                    _pokemons.value = emptyPokemonList
                    Log.d("TAG", "getAllPokemon: ${result.data}")
                }
                is Resource.Error -> {
                    _errorText.value = result.message
                    _spinner.value = false
                }
            }

        }
    }

    fun onErrorTextShown() {
        _errorText.value = null
    }
}