package com.erykhf.android.a3sctest.data.remote

import com.erykhf.android.a3sctest.model.PokemonList
import retrofit2.http.GET

interface PokeApi {

    @GET("pokemon")
    suspend fun getPokemon(): PokemonList
}