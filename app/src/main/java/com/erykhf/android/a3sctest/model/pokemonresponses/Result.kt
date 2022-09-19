package com.erykhf.android.a3sctest.model.pokemonresponses

import com.erykhf.android.a3sctest.model.PokemonListEntry

data class Result(
    val name: String = "",
    val url: String = "",
    var pokemon: Pokemon?,
    val pokemonListEntry: PokemonListEntry
)