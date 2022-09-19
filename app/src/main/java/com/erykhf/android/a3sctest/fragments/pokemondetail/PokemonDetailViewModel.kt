package com.erykhf.android.a3sctest.fragments.pokemondetail

import androidx.lifecycle.*
import com.erykhf.android.a3sctest.data.repository.Repository
import com.erykhf.android.a3sctest.model.PokemonListEntry
import com.erykhf.android.a3sctest.model.pokemonresponses.Pokemon
import com.erykhf.android.a3sctest.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PokemonDetailViewModel @Inject constructor(
    val repository: Repository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val pokemonNumber = savedStateHandle.get<Int>("id")

    private val _pokemon = MutableLiveData<Pokemon?>()
    val pokemon: LiveData<Pokemon?> = _pokemon

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> = _error

    init {
        getPokemonByID(pokemonNumber)
    }

    private fun getPokemonByID(id: Int?) = viewModelScope.launch {

        val result = repository.getIndividualPokemonInfo(id)

            when(result){
                is Resource.Success -> {

                    _pokemon.value = result.data

                }
                is Resource.Error -> {
                    _error.value = result.message

                }
            }


    }


}