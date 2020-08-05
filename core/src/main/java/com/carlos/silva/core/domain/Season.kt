package com.carlos.silva.core.domain

import com.google.gson.annotations.SerializedName
data class Season(
    @SerializedName("anime")
    val anime: List<Anime>
)