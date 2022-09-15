package com.erykhf.android.a3sctest.data.repository

import com.erykhf.android.a3sctest.data.remote.PokeApi
import com.erykhf.android.a3sctest.model.PokemonList
import com.erykhf.android.a3sctest.model.Result
import com.erykhf.android.a3sctest.utils.Resource
import java.lang.Exception
import javax.inject.Inject

class Repository @Inject constructor(private val api: PokeApi) {

    suspend fun loadPokemonList(): Resource<PokemonList> {
        val result = try {

            api.getPokemon()
        } catch (e: Exception) {
            return Resource.Error("An Error Occured")
        }

        return Resource.Success(result)
    }
}