package com.example.warcardgame

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun PortraitOrientation(
    firstCard: Card,
    secondCard: Card,
    firstPlayerScore: Int,
    secondPlayerScore: Int,
    deal: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceAround,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Logo()
        CardsPanel(firstCard, secondCard)
        DealButtonView(deal)
        ScorePanel(firstPlayerScore, secondPlayerScore)
    }
}

@Composable
fun LandscapeOrientation(
    firstCard: Card,
    secondCard: Card,
    firstPlayerScore: Int,
    secondPlayerScore: Int,
    deal: () -> Unit
) {

}