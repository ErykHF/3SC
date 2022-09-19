package com.erykhf.android.a3sctest.data.repository


import com.erykhf.android.a3sctest.data.remote.PokeApi
import com.erykhf.android.a3sctest.utils.Resource
import com.erykhf.android.a3sctest.model.pokemonresponses.Pokemon
import com.erykhf.android.a3sctest.model.pokemonresponses.PokemonList
import javax.inject.Inject
import kotlin.Exception

class Repository @Inject constructor(private val api: PokeApi) {

    suspend fun loadPokemonList(): Resource<PokemonList> {
        val result = try {

            api.getPokemon()
        } catch (e: Exception) {
            return Resource.Error("An Error Occured")
        }

        return Resource.Success(result)
    }


    suspend fun getIndividualPokemonInfo(id: Int?): Resource<Pokemon> {
        val result = try {

            api.getPokemonInfo(id)
        } catch (e: Exception) {
            return Resource.Error("An Error Occured")
        }

        return Resource.Success(result)
    }

}