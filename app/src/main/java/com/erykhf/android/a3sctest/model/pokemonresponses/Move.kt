package com.erykhf.android.a3sctest.model.pokemonresponses

data class Move(
    val move: MoveX = MoveX(),
    val version_group_details: List<VersionGroupDetail> = listOf()
)