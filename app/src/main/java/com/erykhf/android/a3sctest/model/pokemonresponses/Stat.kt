package com.erykhf.android.a3sctest.model.pokemonresponses

data class Stat(
    val base_stat: Int = 0,
    val effort: Int = 0,
    val stat: StatX = StatX()
)