package com.erykhf.android.a3sctest.model.pokemonresponses

import com.erykhf.android.a3sctest.model.PokemonListEntry

data class PokemonList(
    val count: Int = 0,
    val next: String = "",
    val previous: Any = Any(),
    val results: List<Result> = listOf(),
    val pokemonListEntry: PokemonListEntry
)