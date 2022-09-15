package com.erykhf.android.a3sctest.fragments.pokemonlist

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.erykhf.android.a3sctest.data.repository.Repository
import com.erykhf.android.a3sctest.model.Result
import com.erykhf.android.a3sctest.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PokemonViewModel @Inject constructor(private val repository: Repository) : ViewModel() {


    private val _pokemons = MutableLiveData<List<Result>?>()
    val pokemons: LiveData<List<Result>?> = _pokemons

    private val _spinner = MutableLiveData<Boolean>(false)
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
                    _pokemons.postValue(result.data?.results)
                    _spinner.value = false
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