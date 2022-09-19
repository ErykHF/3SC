package com.erykhf.android.a3sctest.model.pokemonresponses

data class VersionGroupDetail(
    val level_learned_at: Int = 0,
    val move_learn_method: MoveLearnMethod = MoveLearnMethod(),
    val version_group: VersionGroup = VersionGroup()
)