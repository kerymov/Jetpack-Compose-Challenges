package com.example.warcardgame

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

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
    Row(
        modifier = Modifier.fillMaxSize(),
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically
    ) {
        CardsPanel(firstCard, secondCard)
        Spacer(modifier = Modifier.width(24.dp))
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.SpaceAround,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Logo()
            DealButtonView(deal)
            ScorePanel(firstPlayerScore, secondPlayerScore)
        }
    }
}