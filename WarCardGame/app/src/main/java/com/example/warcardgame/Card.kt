package com.example.warcardgame

import androidx.annotation.DrawableRes

data class Card(
    val name: String,
    val rank: Int,
    @DrawableRes val cardId: Int
)