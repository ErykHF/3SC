package com.erykhf.android.a3sctest.data.remote

import com.erykhf.android.a3sctest.model.pokemonresponses.Pokemon
import com.erykhf.android.a3sctest.model.pokemonresponses.PokemonList
import retrofit2.http.GET
import retrofit2.http.Path

interface PokeApi {

    @GET("pokemon")
    suspend fun getPokemon(): PokemonList

    @GET("pokemon/{name}")
    suspend fun getPokemonInfo(@Path("name") name: Int?): Pokemon
}